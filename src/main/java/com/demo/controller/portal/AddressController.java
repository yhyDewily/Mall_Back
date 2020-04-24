package com.demo.controller.portal;

import com.demo.common.ServerResponse;
import com.demo.dataobject.User;
import com.demo.dataobject.UserAddress;
import com.demo.service.Impl.AddressServiceImpl;
import com.demo.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address/")
public class AddressController {

    @Autowired
    AddressServiceImpl addressService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "getAddressList.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<List<UserAddress>> getUserAddress(Integer userId) {
        User user = userService.getUserInfo(userId);
        if(user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        return addressService.getUserAddressList(userId);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse delAddress(Integer id){
        return addressService.deleteAddress(id);
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse add(int userId, String recipient, String address, String postcode, String mobile){
        User user = userService.getUserInfo(userId);
        if(user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        return addressService.addAddress(userId,recipient,address,postcode,mobile);
    }

    @RequestMapping(value = "modify.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse modify(Integer id, int userId, String recipient, String address, String postcode, String mobile){
        User user = userService.getUserInfo(userId);
        if(user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        return addressService.modifyAddress(id,userId,recipient,address,postcode,mobile);
    }
}
