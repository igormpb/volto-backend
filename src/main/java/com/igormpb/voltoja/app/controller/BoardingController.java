package com.igormpb.voltoja.app.controller;

import com.igormpb.voltoja.app.service.BoardingService;
import com.igormpb.voltoja.domain.errors.HandleErros;
import com.igormpb.voltoja.domain.request.PostBoardingRegisterRequest;
import com.igormpb.voltoja.domain.response.ResponseErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boarding")
public class BoardingController {

    @Autowired
    BoardingService boardingService;

    @PostMapping("/register")
    public ResponseEntity Register(@RequestBody PostBoardingRegisterRequest body){
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            boardingService.Register(body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity FindByAccountId(@PathVariable String id) {
        try {
            var data = boardingService.findByAccountId(id);
            return ResponseEntity.ok(data);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
    @PostMapping("edit/{id}")
    public ResponseEntity Edit(@PathVariable String id, @RequestBody PostBoardingRegisterRequest body){
        try{
            boardingService.EditById(id,body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity DeleteById(@PathVariable String id){
        try{
            boardingService.DeleteById(id);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
}
