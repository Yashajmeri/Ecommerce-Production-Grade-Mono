package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.payload.AddressDTO;
import com.example.ecommerce.Project1.service.AddressService;
import com.example.ecommerce.Project1.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Represents the address controller component.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {
    private  final AddressService addressService;
    private  final AuthUtil authUtil;

    /**
     * Creates address.
     * @param addressDTO the addressDTO value.
     * @return the result of create address.
     */
    @PostMapping("/addresses")
 public ResponseEntity<AddressDTO>   createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
       AddressDTO newAddressDTO = addressService.createAddress(addressDTO,user);
       return new ResponseEntity<>(newAddressDTO, HttpStatusCode.valueOf(201));
 }
 /**
  * Returns the all addresses.
  * @return the all addresses.
  */
 @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){

      List<AddressDTO> addressDTOList = addressService.getAllAddresses();
      return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
 }
 /**
  * Returns the addresses by id.
  * @param addressId the addressId value.
  * @return the addresses by id.
  */
 @GetMapping("/addresses/{addressId}")
 public ResponseEntity<AddressDTO> getAddressesById(@PathVariable Long addressId){

     AddressDTO addressDTO = addressService.getAddressesById(addressId);
     return new ResponseEntity<>(addressDTO, HttpStatus.OK);
 }
    /**
     * Returns the addresses by user.
     * @return the addresses by user.
     */
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressesByUser(){
           User user = authUtil.loggedInUser();
        List<AddressDTO> addressDTOList = addressService.getAddressesByUser(user);
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }
    /**
     * Updates addresses by id.
     * @param addressId the addressId value.
     * @param addressDTO the addressDTO value.
     * @return the result of update addresses by id.
     */
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressesById(@PathVariable Long addressId , @RequestBody AddressDTO addressDTO){

        AddressDTO updatedAddressDTO = addressService.updateAddressesById(addressId ,addressDTO);
        return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
    }
    /**
     * Deletes addresses by id.
     * @param addressId the addressId value.
     * @return the result of delete addresses by id.
     */
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressesById(@PathVariable Long addressId){

        String status = addressService.deleteAddressesById(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
