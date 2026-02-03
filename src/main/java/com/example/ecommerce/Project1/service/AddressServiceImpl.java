package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.Address;
import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.payload.AddressDTO;
import com.example.ecommerce.Project1.repositories.AddressRepository;
import com.example.ecommerce.Project1.repositories.UserRepository;
import com.example.ecommerce.Project1.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AuthUtil authUtil;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);


        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
         List<Address> addressList = addressRepository.findAll();
           return addressList.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressesById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        return  modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressesByUser(User user) {
        List<Address> addressList = user.getAddresses();

        return addressList.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO updateAddressesById(Long addressId, AddressDTO addressDTO) {
         Address addressFromDatabase = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
          addressFromDatabase.setCity(addressDTO.getCity());
          addressFromDatabase.setState(addressDTO.getState());
          addressFromDatabase.setStreet(addressDTO.getStreet());
          addressFromDatabase.setPincode(addressDTO.getPincode());
          addressFromDatabase.setCountry(addressDTO.getCountry());
          addressFromDatabase.setBuildingName(addressDTO.getBuildingName());
          Address updatedAddress = addressRepository.save(addressFromDatabase);
          User user = addressFromDatabase.getUser();
          user.getAddresses().removeIf(address -> Objects.equals(address.getAddressId(), addressId));
          user.getAddresses().add(updatedAddress);
          userRepository.save(user);


        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String  deleteAddressesById(Long addressId) {
         Address addressFromDB = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
         User user = addressFromDB.getUser();
         user.getAddresses().removeIf(address -> Objects.equals(address.getAddressId(), addressId));
         userRepository.save(user);
         addressRepository.delete(addressFromDB);
        return "Address successfully deleted with address_ID: " + addressId;
    }

}
