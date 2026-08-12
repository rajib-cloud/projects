package com.vali.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vali.entity.UserRegister;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Long>{

	public Optional<UserRegister> findByEmail(String email);
	
	public void deleteByEmail(String email);
}
