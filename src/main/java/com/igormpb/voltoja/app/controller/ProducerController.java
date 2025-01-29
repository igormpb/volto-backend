package com.igormpb.voltoja.app.controller;

import com.igormpb.voltoja.app.service.ProducerService;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostProducerRegisterRequest;
import com.igormpb.voltoja.domain.response.ResponseErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @PostMapping("/register")
    public ResponseEntity Register(@RequestBody PostProducerRegisterRequest body){
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            producerService.Register(body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }

    }

    @GetMapping("/list")
    public ResponseEntity All(){
        try{
            var producers = producerService.All();
            return ResponseEntity.ok(producers);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity findById(@PathVariable String id){
        try{
            var producer = producerService.findById(id);
            return ResponseEntity.ok(producer);
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        try{
            producerService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

}
