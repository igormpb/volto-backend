package com.igormpb.voltoja.service;

import com.igormpb.voltoja.entity.DriverEntity;
import com.igormpb.voltoja.errors.HandleErros;
import com.igormpb.voltoja.repository.DriverRepository;
import com.igormpb.voltoja.request.PostDriverRegisterRequest;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;

    public void Register(PostDriverRegisterRequest body){
        try{
            var driver = DriverEntity.builder()
                    .name(body.getName())
                    .type(body.getType())
                    .quantity(body.getQuantity())
                    .plate(body.getPlate())
                    .documents(body.getDocuments())
                    .music(body.getMusic())
                    .snow(body.getSnow());
            var result = driverRepository.findByPlate(body.getPlate());
            System.out.println("Resultado do findByPlate: " + result);
            if (result != null){
                throw new HandleErros("veículo já existe, por favor tente registrar outro", HttpStatus.BAD_REQUEST);
            };

            driverRepository.save(driver.build());
        }catch (MongoException e) {
            throw new HandleErros("não foi possível cadastrar seu veiculo, por favor tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }

    public List<DriverEntity> All(){
        try {
            return driverRepository.findAll();
        }catch (Exception e){
            throw new HandleErros("não foi possível listar os motoristas, tente novamente mais tarde", HttpStatus.BAD_REQUEST);
        }
    }
    public DriverEntity DetailByPlate(String plate){
        try {
            System.out.println("Buscando motorista com plate: " + plate);
            var driver = driverRepository.findByPlate(plate);
            if (driver == null) {
                throw new HandleErros("Motorista não encontrado", HttpStatus.NOT_FOUND);
            }
            System.out.println("Motorista encontrado: " + driver);
            return driver;

        } catch (Exception e) {
            throw new HandleErros("Error fetching driver", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void DeleteByPlate(String plate){
        try {
            var driver = driverRepository.findByPlate(plate);
            driverRepository.delete(driver);
        }catch (Exception e) {
            throw new HandleErros("Motorista não encontrado, Tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void EditByPlate(String plate , PostDriverRegisterRequest updatedDriver){
        try {
            var driver = driverRepository.findByPlate(plate);
            System.out.println(driver.getName());

            // Atualizar os campos necessários
            if (updatedDriver.getName() != null) {
                driver.setName(updatedDriver.getName());
            }
            if (updatedDriver.getType() != null) {
                driver.setType(updatedDriver.getType());
            }
            if (updatedDriver.getQuantity() != null) {
                driver.setQuantity(updatedDriver.getQuantity());
            }
            if (updatedDriver.getDocuments() != null) {
                driver.setDocuments(updatedDriver.getDocuments());
            }

            // Salvar o documento atualizado
            driverRepository.save(driver);
        }catch (Exception e){
            throw new HandleErros("Não foi possivel editar o motorista, tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
