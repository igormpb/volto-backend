package com.igormpb.voltoja.app.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver-config")
public class DriverConfigController {

    @GetMapping("/races")
    public ResponseEntity GetRaces(){
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/race/{id}")
    public ResponseEntity GetRaceById(String id){
        return ResponseEntity.noContent().build();
    }
}
