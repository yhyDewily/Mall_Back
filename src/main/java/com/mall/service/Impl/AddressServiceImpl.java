package com.mall.service.Impl;

import com.mall.common.ServerResponse;
import com.mall.dataobject.UserAddress;
import com.mall.repository.AddressRepository;
import com.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository repository;

    @Override
    public ServerResponse<List<UserAddress>> getUserAddressList(Integer userId) {
        return ServerResponse.createBySuccess(repository.findAllByUserId(userId));
    }

    @Override
    public ServerResponse deleteAddress(int id){
        try {
            repository.deleteById(id);
            return ServerResponse.createBySuccessMessage("删除成功");
        } catch (Exception e){
            System.out.println(e);
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @Override
    public ServerResponse addAddress(int userId, String recipient, String address, String postcode, String mobile) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setRecipient(recipient);
        userAddress.setAddress(address);
        userAddress.setPostcode(postcode);
        userAddress.setMobile(mobile);
        repository.save(userAddress);
        return ServerResponse.createBySuccessMessage("添加成功");
    }

    @Override
    public ServerResponse modifyAddress(Integer id, int userId, String recipient, String address, String postcode, String mobile) {
        UserAddress userAddress = repository.findById(1).get();
        userAddress.setRecipient(recipient);
        userAddress.setAddress(address);
        userAddress.setPostcode(postcode);
        userAddress.setMobile(mobile);
        repository.save(userAddress);
        return ServerResponse.createBySuccessMessage("修改成功");
    }
}
