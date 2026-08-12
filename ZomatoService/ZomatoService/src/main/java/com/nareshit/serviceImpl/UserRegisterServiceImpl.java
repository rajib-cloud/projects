package com.nareshit.serviceImpl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nareshit.dto.UserRequestDto;
import com.nareshit.enity.UserRegister;
import com.nareshit.repository.UserRegisterRepo;
import com.nareshit.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private UserRegisterRepo userRegisterRepo;

	@Override
	public UserRegister createdZomatoRegister(UserRequestDto userRequestDto) {

		UserRegister user = null;

		try {
			user = new UserRegister();
			user.setFirstName(userRequestDto.getFirstName());
			user.setLastName(userRequestDto.getLastName());
			user.setEmail(userRequestDto.getEmail());
			user.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
			user.setContactId(userRequestDto.getContactId());
			userRegisterRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
