package com.igormpb.voltoja.app.controller;
import com.igormpb.voltoja.app.service.DriverConfigService;
import com.igormpb.voltoja.domain.errors.HandleErros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver-config")
public class DriverConfigController {
    @Autowired
    DriverConfigService driverConfigService;
    @GetMapping("/races")
    public ResponseEntity GetRaces(@Param("status") String status, @Param("id") String id){
        try{
            var boardings = driverConfigService.getList(status, id);
            return ResponseEntity.ok(boardings);
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }

    @GetMapping("/race/{id}")
    public ResponseEntity GetRaceById(String id){
        try{
            var boarding = driverConfigService.GetById(id);
            return ResponseEntity.ok(boarding);
        }catch (HandleErros e){
            return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
        }
    }
}
