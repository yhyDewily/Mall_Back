package com.mall.repository;

import com.mall.dataobject.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<UserAddress, Integer> {

    List<UserAddress> findAllByUserId(Integer userId);

    void deleteById(Integer id);

    Optional<UserAddress> findById(Integer id);
}
