package com.igormpb.voltoja.app.controller;

import com.igormpb.voltoja.app.service.BoardingService;
import com.igormpb.voltoja.app.service.EventService;
import com.igormpb.voltoja.domain.entity.BoardingEntity;
import com.igormpb.voltoja.domain.entity.EventEntity;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostEventRegisterRequest;
import com.igormpb.voltoja.domain.response.ResponseErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;
    @Autowired
    BoardingService boardingService;


    @PostMapping("/register")
    public ResponseEntity Register(@RequestBody PostEventRegisterRequest body){
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            eventService.Register(body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity Edit(@PathVariable String id , @RequestBody PostEventRegisterRequest body){
        try {
            eventService.EditById(id,body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
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
    public ResponseEntity DetailById(@PathVariable String id) {
        try {
            var data = eventService.DetailById(id);
            return ResponseEntity.ok(data);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @GetMapping("/producers/{id}")
    public ResponseEntity EventByProducerId(@PathVariable String id){
        try {
            List<EventEntity> events = eventService.AllByProducer(id);
            return ResponseEntity.ok(events);
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
    @GetMapping("/{id}/boarding")
    public ResponseEntity BoardingByEventId(@PathVariable String id) {
        try {
            List<BoardingEntity> boardings = boardingService.findAllByEventId(id);
            return ResponseEntity.ok(boardings);
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity DeleteById(@PathVariable String id){
        try {
            eventService.Delete(id);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
}
