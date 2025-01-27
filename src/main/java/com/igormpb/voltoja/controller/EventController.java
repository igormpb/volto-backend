package com.igormpb.voltoja.controller;

import com.igormpb.voltoja.entity.BoardingEntity;
import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.request.PostEventRegisterRequest;
import com.igormpb.voltoja.response.ResponseErr;
import com.igormpb.voltoja.service.BoardingService;
import com.igormpb.voltoja.service.EventService;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity DetailById(@PathVariable(value = "id") String id) {
        try {
            var data = eventService.DetailById(id);
            return ResponseEntity.ok(data);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }


    @GetMapping("/{id}/boarding")
    public ResponseEntity BoardingByEventId(@PathParam(value = "id") String id) {
        List<BoardingEntity> boardings = boardingService.findAllByEventId(id);
        return ResponseEntity.ok(boardings);
    }

}
