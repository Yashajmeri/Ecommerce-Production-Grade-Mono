package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.payload.AddressDTO;
import com.example.ecommerce.Project1.service.AddressService;
import com.example.ecommerce.Project1.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class AddressController {
    private  final AddressService addressService;
    private  final AuthUtil authUtil;

    public AddressController(AddressService addressService, AuthUtil authUtil) {
        this.addressService = addressService;
        this.authUtil = authUtil;
    }

    @PostMapping("/addresses")
 public ResponseEntity<AddressDTO>   createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
       AddressDTO newAddressDTO = addressService.createAddress(addressDTO,user);
       return new ResponseEntity<>(newAddressDTO, HttpStatusCode.valueOf(201));
 }
 @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){

      List<AddressDTO> addressDTOList = addressService.getAllAddresses();
      return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
 }
 @GetMapping("/addresses/{addressId}")
 public ResponseEntity<AddressDTO> getAddressesById(@PathVariable Long addressId){

     AddressDTO addressDTO = addressService.getAddressesById(addressId);
     return new ResponseEntity<>(addressDTO, HttpStatus.OK);
 }
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressesByUser(){
           User user = authUtil.loggedInUser();
        List<AddressDTO> addressDTOList = addressService.getAddressesByUser(user);
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressesById(@PathVariable Long addressId , @RequestBody AddressDTO addressDTO){

        AddressDTO updatedAddressDTO = addressService.updateAddressesById(addressId ,addressDTO);
        return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
    }
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressesById(@PathVariable Long addressId){

        String status = addressService.deleteAddressesById(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
