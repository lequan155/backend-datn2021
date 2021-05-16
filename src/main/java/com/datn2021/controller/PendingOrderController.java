package com.datn2021.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn2021.dto.OrderItemsDTO;
import com.datn2021.model.Customer;
import com.datn2021.model.Menu;
import com.datn2021.model.OrderFinal;
import com.datn2021.model.OrderItems;
import com.datn2021.model.StoreTable;
import com.datn2021.repo.CustomerRepository;
import com.datn2021.repo.MenuRepository;
import com.datn2021.repo.OrderFinalRepository;
import com.datn2021.repo.OrderItemsRepository;
import com.datn2021.repo.StoreTableRepository;
import com.datn2021.services.OrderItemsService;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushReceipt;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/table/{id}/pendingorder")
public class PendingOrderController {
@Autowired private OrderItemsRepository repo;
@Autowired private MenuRepository itemRepo;
@Autowired private StoreTableRepository tableRepo;
@Autowired private OrderFinalRepository finalRepo ;
@Autowired private CustomerRepository customerRepo;
@Autowired private OrderItemsService service;

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<List<OrderItemsDTO>> getListPendingOrder(@PathVariable Long id){
		StoreTable table = tableRepo.findById(id).get();
		List<OrderItemsDTO> list = service.findByTableId(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
}
	
	@GetMapping("/listcancel")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<List<OrderItemsDTO>> getListCancelItems(@PathVariable Long id){
		OrderFinal newOrderFinal = finalRepo.findByTableId(id);
		if(newOrderFinal != null) {
			List<OrderItemsDTO> list = service.listCancelItemsByOrderFinalId(newOrderFinal.getId());
			return new ResponseEntity<List<OrderItemsDTO>>(list,HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping("/listok")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<List<OrderItemsDTO>> getListOkItems(@PathVariable Long id){
		OrderFinal newOrderFinal = finalRepo.findByTableId(id);
		if(newOrderFinal != null) {
			List<OrderItemsDTO> list = service.findActiveByOrderFinalId(newOrderFinal.getId());
			return new ResponseEntity<List<OrderItemsDTO>>(list,HttpStatus.OK);
		}
		return null;
	}
	
	@PostMapping("/addcustomer")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Customer addCustomer(@RequestBody Customer customer, @PathVariable Long id) {
		OrderFinal of = finalRepo.findByTableId(id);
		Customer cus = new Customer();
		Customer newCus = new Customer();
		newCus.setName(customer.getName());
		newCus.setPhoneNo(customer.getPhoneNo());
		
		cus = customerRepo.findByPhoneNo(customer.getPhoneNo());
		if(of==null) {
			of = new OrderFinal();
			of.setStoreTable(tableRepo.findById(id).get());
			finalRepo.save(of);
		}
		if(cus == null) {
				customerRepo.save(newCus);
				of.setCustomer(newCus);
				finalRepo.save(of);
				return newCus;
		}
		of.setCustomer(cus);
		finalRepo.save(of);
		return customerRepo.findByPhoneNo(customer.getPhoneNo());
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public OrderItems createPendingOrder(@RequestBody OrderItems newPendingOrder){
		if(newPendingOrder.getId()==null) {
			newPendingOrder.setId(new Long(0));
		}
		return repo.save(newPendingOrder);
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public OrderItems updatePendingOrder(@PathVariable Long id){
		Optional<OrderItems> opt = repo.findById(id);
		OrderItems newPendingOrder = new OrderItems();
		if(opt.isPresent()) {
			newPendingOrder = opt.get();
//			newPendingOrder.setStatus(true);
		}
		return repo.save(newPendingOrder);
	}
	
	@PostMapping("/{id}/addNote")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public OrderItems updateNotePendingOrder(@RequestBody OrderItems newPendingOrder,@PathVariable Long id) {
		return repo.findById(id).map(
				pendingorder -> {
					pendingorder.setNote(newPendingOrder.getNote());
					return repo.save(pendingorder);
				}).orElseGet(()->{
					newPendingOrder.setId(id);
					return repo.save(newPendingOrder);
				});
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public void deletePendingOrder(@PathVariable Long id){
		Optional<OrderItems> opt = repo.findById(id);
		OrderItems newPendingOrder = new OrderItems();
		if(opt.isPresent()) {
			newPendingOrder = opt.get();
			newPendingOrder.setDelete(true);
		}
		repo.save(newPendingOrder);
	}

	@PostMapping("/additem")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<List<OrderItemsDTO>> addMenuItem (@RequestBody List<Map> map,@PathVariable Long id){
		try {
			String itemId = null;
			String itemQty = null;
			OrderFinal newOrderFinal = finalRepo.findByTableId(id);
			for (int i = 0; !map.isEmpty() && i < map.size(); i++) {
				Map item = map.get(i);
				itemId = item.get("id").toString();
				itemQty = item.get("qty").toString();
				if(repo.findByMenuId(Long.parseLong(itemId),newOrderFinal.getId()) == null){
					OrderItems newOrderItems = new OrderItems();
					newOrderItems.setItem(itemRepo.findMenuItemById(Long.parseLong(itemId)));
					newOrderItems.setOrderFinal(newOrderFinal);
					newOrderItems.setDelete(false);
					newOrderItems.setQty(Integer.parseInt(itemQty));
					newOrderItems.setActive(true);
					repo.save(newOrderItems);
				}
				else {
					OrderItems newOrderItems = repo.findByMenuId(Long.parseLong(itemId),newOrderFinal.getId());
					int newQty = newOrderItems.getQty() + Integer.parseInt(itemQty);
					newOrderItems.setQty(newQty);
					repo.save(newOrderItems);
				}	
			}
			return new ResponseEntity<>(service.findActiveByOrderFinalId(newOrderFinal.getId()),HttpStatus.OK);
		}
		catch (Exception e) {
			
		}
		return new ResponseEntity<>(service.findByTableId(id),HttpStatus.OK); 
	}
	
	@PostMapping("/cancelitem")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<OrderItemsDTO> cancelMenuItem (@RequestBody Map<String,Long> citem,@PathVariable Long id){
		OrderFinal newOrderFinal = finalRepo.findByTableId(id);
		Long fid = newOrderFinal.getId();
		try {
			if(citem!= null) {
				Long cid = citem.get("id");
				int cqty = citem.get("qtyDestroy").intValue();
				OrderItems newOrderItem = repo.findByMenuId(cid,fid);
				OrderItems cancelOrderItem = repo.findCancelItemByMenuId(cid,fid);
				int oldQty = newOrderItem.getQty();
				newOrderItem.setQty(oldQty - cqty);
				if(cancelOrderItem == null) {
					OrderItems newCancelOrderItem = new OrderItems();
					newCancelOrderItem.setDelete(false);
					newCancelOrderItem.setOrderFinal(newOrderFinal);
					newCancelOrderItem.setItem(itemRepo.findMenuItemById(cid));
					newCancelOrderItem.setQty(cqty);
					newCancelOrderItem.setActive(false);
					repo.save(newCancelOrderItem);
				}
				else if(cancelOrderItem != null) {
					int cancelQty = cancelOrderItem.getQty();
					cancelOrderItem.setQty(cancelQty + cqty);
					repo.save(cancelOrderItem);
				}
				if(newOrderItem.getQty() <= 0) {
					newOrderItem.setDelete(true);
					repo.save(newOrderItem);
				}
				return service.findActiveByOrderFinalId(newOrderFinal.getId());
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return service.findActiveByOrderFinalId(newOrderFinal.getId());
	}
	
	@PostMapping("/mergetable")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<String> mergeTable(@RequestBody Map<String,Long> data, @PathVariable Long id){
		if(data != null && id != null) {
			Long newTableId = data.get("newTableId");
			OrderFinal oldFinal = finalRepo.findByTableId(id);
			OrderFinal newFinal = finalRepo.findByTableId(newTableId);
			List<OrderItems> oldItems = repo.findAllByOrderFinalId(oldFinal.getId());
			for (int i = 0;oldItems != null && i < oldItems.size(); i++) {
				OrderItems mergeItem = oldItems.get(i);
				OrderItems newItem = repo.findByMenuId(mergeItem.getItem().getId(), newFinal.getId());
				OrderItems newCitem = repo.findCancelItemByMenuId(mergeItem.getItem().getId(), newFinal.getId());
				if(newItem != null && mergeItem.isActive() == true) {
					newItem.setQty(newItem.getQty() + mergeItem.getQty());
					newItem.setNote(new StringBuilder(newItem.getNote()== null ? new String() : newItem.getNote()).append(" / Chuyển bàn từ bàn "+ id + ", Số lượng: "+ mergeItem.getQty()).toString());
					mergeItem.setDelete(true);
					repo.save(mergeItem);
					repo.save(newItem);
				}
				else if(newCitem != null && mergeItem.isActive() == false) {
					newCitem.setQty(newCitem.getQty() + mergeItem.getQty());
					newCitem.setNote(new StringBuilder(newCitem.getNote()== null ? new String() : newCitem.getNote()).append(" / Chuyển bàn từ bàn "+ id + ", Số lượng: "+ mergeItem.getQty()).toString());
					mergeItem.setDelete(true);
					repo.save(mergeItem);
					repo.save(newCitem);
				}
				else {
					mergeItem.setOrderFinal(newFinal);
					mergeItem.setNote(new StringBuilder(mergeItem.getNote()== null ? new String() : mergeItem.getNote()).append(" / Chuyển bàn từ bàn "+ id + ", Số lượng: "+ mergeItem.getQty()).toString());
					repo.save(mergeItem);
				}
			}
			oldItems = repo.findAllByOrderFinalId(oldFinal.getId());
			if(oldItems.isEmpty()) {
				oldFinal.setDelete(true);
				StoreTable table = tableRepo.findById(id).get();
				table.setStatus("Pending");
				finalRepo.save(oldFinal);
				tableRepo.save(table);
			}
		}
		return new ResponseEntity<String>("Merge Done",HttpStatus.ACCEPTED);
	}
	@GetMapping("/send")
	public String sendNotification() throws Exception {
		String recipient = "ExponentPushToken[xl0PEwEceZ97AwVqm-ajAz]"; // To test, you must replace the recipient with a valid token!
        String title = "Hủy món thành công!";
        String message = "Bạn đã hủy món thành công";

        if (!PushClient.isExponentPushToken(recipient))
            throw new Error("Token:" + recipient + " is not a valid token.");

        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(recipient);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(message);

        List<ExpoPushMessage> expoPushMessages = new ArrayList<ExpoPushMessage>();
        expoPushMessages.add(expoPushMessage);

        PushClient client = new PushClient();
        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);

        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<CompletableFuture<List<ExpoPushTicket>>>();

        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(client.sendPushNotificationsAsync(chunk));
        }

        // Wait for each completable future to finish
        List<ExpoPushTicket> allTickets = new ArrayList<ExpoPushTicket>();
        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
            try {
                for (ExpoPushTicket ticket : messageReplyFuture.get()) {
                    allTickets.add(ticket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = client.zipMessagesTickets(expoPushMessages, allTickets);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = client.filterAllSuccessfulMessages(zippedMessagesTickets);
        String okTicketMessagesString = okTicketMessages.stream().map(
                p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()
        ).collect(Collectors.joining(","));
        System.out.println(
                "Recieved OK ticket for " +
                        okTicketMessages.size() +
                        " messages: " + okTicketMessagesString
        );

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = client.filterAllMessagesWithError(zippedMessagesTickets);
        String errorTicketMessagesString = errorTicketMessages.stream().map(
                p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError()
        ).collect(Collectors.joining(","));
        System.out.println(
                "Recieved ERROR ticket for " +
                        errorTicketMessages.size() +
                        " messages: " +
                        errorTicketMessagesString
        );


        // Countdown 30s
//        int wait = 30;
//        for (int i = wait; i >= 0; i--) {
//            System.out.print("Waiting for " + wait + " seconds. " + i + "s\r");
//            Thread.sleep(1000);
//        }
//        System.out.println("Fetching reciepts...");

        List<String> ticketIds = (client.getTicketIdsFromPairs(okTicketMessages));
        CompletableFuture<List<ExpoPushReceipt>> receiptFutures = client.getPushNotificationReceiptsAsync(ticketIds);

        List<ExpoPushReceipt> receipts = new ArrayList<ExpoPushReceipt>();
        try {
            receipts = receiptFutures.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "Recieved " + receipts.size() + " receipts:");

        for (ExpoPushReceipt reciept : receipts) {
            System.out.println(
                    "Receipt for id: " +
                            reciept.getId() +
                            " had status: " +
                            reciept.getStatus());

        }
        return "done";
	}
}
