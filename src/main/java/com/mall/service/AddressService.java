package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.dataobject.UserAddress;

import java.util.List;

public interface AddressService {
    ServerResponse<List<UserAddress>> getUserAddressList(Integer userId);

    ServerResponse deleteAddress(int id);

    ServerResponse addAddress(int userId, String recipient, String address, String postcode, String mobile);

    ServerResponse modifyAddress(Integer id, int userId, String recipient, String address, String postcode, String mobile);
}
