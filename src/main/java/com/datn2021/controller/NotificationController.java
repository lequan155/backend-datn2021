package com.datn2021.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.datn2021.payload.request.NotificationRequest;
import com.datn2021.services.EmitterService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private EmitterService emitterService;

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
        emitterService.pushNotification(username, request.getFrom(), request.getMessage());
        return ResponseEntity.ok().body("message pushed to user " + username);
    }
}
