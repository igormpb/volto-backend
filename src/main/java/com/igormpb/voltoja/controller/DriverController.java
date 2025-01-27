package com.igormpb.voltoja.controller;

import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.request.PostDriverRegisterRequest;
import com.igormpb.voltoja.response.ResponseErr;
import com.igormpb.voltoja.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity Register (@RequestBody PostDriverRegisterRequest body) {
        if (body.Validate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErr(body.Validate(), HttpStatus.BAD_REQUEST));
        }
        try {
            driverService.Register(body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }

    }

    @GetMapping("/list")
    public ResponseEntity All(){
        try{
            var data = driverService.All();
            return ResponseEntity.ok(data);
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
    @PostMapping("/edit/{plate}")
    public ResponseEntity EditByPlate(@PathVariable(value = "plate") String plate , @RequestBody PostDriverRegisterRequest body){
        try {
            driverService.EditByPlate(plate,body);
            return ResponseEntity.noContent().build();
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @GetMapping("/detail/{plate}")
    public ResponseEntity DetailByPlate(@PathVariable(value = "id") String plate){
        try {
            var data = driverService.DetailByPlate(plate);
            return ResponseEntity.ok(data);
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @DeleteMapping("/delete/{plate}")
    public ResponseEntity<Void> deleteByPlate(@PathVariable(value = "id") String plate) {
        try {
            driverService.DeleteByPlate(plate);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (HandleErros e) {
            return ResponseEntity.status(e.GetResponseError().status()).body(null);
        }
    }
}
