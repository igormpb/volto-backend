package com.igormpb.voltoja.controller;

import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.service.EventService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/list")
    public ResponseEntity All() {
        try {
            var data = eventService.All();
            return ResponseEntity.ok(data);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity DetailById(@PathParam(value = "id") String id) {
        try {
            var data = eventService.DetailById(id);
            return ResponseEntity.ok(data);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }


    @GetMapping("/{id}/boarding")
    public ResponseEntity BoardingByEventId(@PathParam(value = "id") String id) {
        return ResponseEntity.ok(null);
    }

}
