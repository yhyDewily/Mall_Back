package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.dataobject.UserAddress;

import java.util.List;

public interface AddressService {
    ServerResponse<List<UserAddress>> getUserAddressList(Integer userId);

    ServerResponse deleteAddress(int id);

    ServerResponse addAddress(int userId, String recipient, String address, String postcode, String mobile);

    ServerResponse modifyAddress(Integer id, int userId, String recipient, String address, String postcode, String mobile);
}
