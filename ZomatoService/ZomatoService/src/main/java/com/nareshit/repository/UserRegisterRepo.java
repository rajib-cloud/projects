package com.nareshit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nareshit.enity.UserRegister;
@Repository // it is a optinal
public interface UserRegisterRepo extends JpaRepository<UserRegister, Long>{

}
