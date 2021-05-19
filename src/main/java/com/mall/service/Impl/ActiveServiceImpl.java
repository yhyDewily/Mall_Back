package com.mall.service.Impl;

import com.mall.dataobject.UserActiveDTO;
import com.mall.repository.UserActiveRepository;
import com.mall.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    UserActiveRepository repository;

    @Override
    public List<UserActiveDTO> listAllUserActive() {
        return repository.findAll();
    }

    @Override
    public boolean saveUserActive(UserActiveDTO userActiveDTO) {
        boolean flag = false;

        int rows = this.repository.countUserActiveDTOByUserIdAndCategory2Id(userActiveDTO.getUserId(), userActiveDTO.getCategory2Id());
        int saveRows = 0;
        int updateRows = 0;
        if(rows < 1) { //不存在
            userActiveDTO.setHits(1L);
            try {
                repository.save(userActiveDTO);
                saveRows = 1;
            } catch (Exception e){
                System.out.println(e);
            }

        } else { //已经存在
            Long hits = repository.findHitsByUserIdAndCategory2Id(userActiveDTO.getUserId(), userActiveDTO.getCategory2Id());
            hits++;
            userActiveDTO.setHits(hits);
            try {
                repository.save(userActiveDTO);
                updateRows = 1;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (saveRows > 0 || updateRows >0) {
            flag = true;
        }
        return flag;
    }


}
