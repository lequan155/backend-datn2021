package com.datn2021.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.datn2021.model.TableNotification;
import com.datn2021.payload.request.NotificationRequest;
import com.datn2021.repo.NotificationRepository;
import com.datn2021.services.EmitterService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private EmitterService emitterService;
    @Autowired private NotificationRepository repo;
    @GetMapping("/subscription")
    public SseEmitter subsribe() {
        log.info("subscribing...");

        SseEmitter sseEmitter = new SseEmitter(24 * 60 * 60 * 1000l);
        emitterService.addEmitter(sseEmitter);

        log.info("subscribed");
        return sseEmitter;
    }

    @PostMapping("/send/{username}")
    public ResponseEntity<?> send(@PathVariable String username, @RequestBody NotificationRequest request) {
    	TableNotification newNoti = new TableNotification();
    	newNoti.setDate(new Date());
    	newNoti.setFromTable(request.getFrom());
    	newNoti.setMessage(request.getMessage());
    	newNoti.setTableid(request.getTableId());
    	repo.save(newNoti);
        emitterService.pushNotification(username, request.getFrom(), request.getMessage(), request.getTableId());
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
    
    @GetMapping("/listnoti")
    public ResponseEntity<List<TableNotification>> getListNotification(){
    	List<TableNotification> list = repo.getTopNotification();
    	return new ResponseEntity<List<TableNotification>> (list,HttpStatus.OK);
    }
}
