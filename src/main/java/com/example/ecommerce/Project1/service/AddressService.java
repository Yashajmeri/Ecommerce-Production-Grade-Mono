package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressesById(Long addressId);

    List<AddressDTO> getAddressesByUser(User user);

    AddressDTO updateAddressesById(Long addressId, AddressDTO addressDTO);

    String deleteAddressesById(Long addressId);
}
