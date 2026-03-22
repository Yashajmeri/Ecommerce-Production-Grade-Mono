package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.Address;
import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.payload.AddressDTO;
import com.example.ecommerce.Project1.repositories.AddressRepository;
import com.example.ecommerce.Project1.repositories.UserRepository;
import com.example.ecommerce.Project1.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Represents the address service impl component.
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AuthUtil authUtil;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    /**
     * Creates address.
     * @param addressDTO the addressDTO value.
     * @param user the user value.
     * @return the result of create address.
     */
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

    /**
     * Returns the all addresses.
     * @return the all addresses.
     */
    @Override
    public List<AddressDTO> getAllAddresses() {
         List<Address> addressList = addressRepository.findAll();
           return addressList.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    /**
     * Returns the addresses by id.
     * @param addressId the addressId value.
     * @return the addresses by id.
     */
    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        return  modelMapper.map(address, AddressDTO.class);
    }

    /**
     * Returns the addresses by user.
     * @param user the user value.
     * @return the addresses by user.
     */
    @Override
    public List<AddressDTO> getAddressesByUser(User user) {
        List<Address> addressList = user.getAddresses();

        return addressList.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    /**
     * Updates addresses by id.
     * @param addressId the addressId value.
     * @param addressDTO the addressDTO value.
     * @return the result of update addresses by id.
     */
    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {
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

    /**
     * Deletes addresses by id.
     * @param addressId the addressId value.
     * @return the result of delete addresses by id.
     */
    @Override
    public String deleteAddressById(Long addressId) {
         Address addressFromDB = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
         User user = addressFromDB.getUser();
         user.getAddresses().removeIf(address -> Objects.equals(address.getAddressId(), addressId));
         userRepository.save(user);
         addressRepository.delete(addressFromDB);
        return "Address successfully deleted with address_ID: " + addressId;
    }

}
