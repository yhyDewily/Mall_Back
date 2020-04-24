package com.demo.repository;

import com.demo.dataobject.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<UserAddress, Integer> {

    List<UserAddress> findAllByUserId(Integer userId);

    void deleteById(Integer id);
}
