package com.mall.service;

import com.mall.dataobject.UserActiveDTO;

import java.util.List;

public interface ActiveService {
    List<UserActiveDTO> listAllUserActive();

    boolean saveUserActive(UserActiveDTO userActiveDTO);
}
