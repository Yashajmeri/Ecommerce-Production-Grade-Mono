package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.payload.AddressDTO;

import java.util.List;

/**
 * Defines the contract for address service operations.
 */
public interface AddressService {
    /**
     * Creates address.
     * @param addressDTO the addressDTO value.
     * @param user the user value.
     * @return the result of create address.
     */
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    /**
     * Returns the all addresses.
     * @return the all addresses.
     */
    List<AddressDTO> getAllAddresses();

    /**
     * Returns the addresses by id.
     * @param addressId the addressId value.
     * @return the addresses by id.
     */
    AddressDTO getAddressesById(Long addressId);

    /**
     * Returns the addresses by user.
     * @param user the user value.
     * @return the addresses by user.
     */
    List<AddressDTO> getAddressesByUser(User user);

    /**
     * Updates addresses by id.
     * @param addressId the addressId value.
     * @param addressDTO the addressDTO value.
     * @return the result of update addresses by id.
     */
    AddressDTO updateAddressesById(Long addressId, AddressDTO addressDTO);

    /**
     * Deletes addresses by id.
     * @param addressId the addressId value.
     * @return the result of delete addresses by id.
     */
    String deleteAddressesById(Long addressId);
}
