package com.datn2021.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.datn2021.model.Notification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmitterService {
	List<SseEmitter> emitters = new ArrayList<>();
	
	public void addEmitter(SseEmitter emitter) {
		emitter.onCompletion(()-> emitters.remove(emitter));
		emitter.onTimeout(()-> emitters.remove(emitter));
		emitters.add(emitter);
	}
	
	public void pushNotification(String username, String name, String message, String tableId) {
		log.info("pushing {} notification for user {} with table id {}", message, username, tableId);
		List<SseEmitter> deadEmitters = new ArrayList<>();

        Notification payload = Notification
                .builder()
                .from(name)
                .message(message)
                .tableId(tableId)
                .date(new Date())
                .build();

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter
                        .event()
                        .name(username)
                        .data(payload));

            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        emitters.removeAll(deadEmitters);
	}
}
