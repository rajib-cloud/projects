package com.nareshit.service;

import com.nareshit.dto.UserRequestDto;
import com.nareshit.enity.UserRegister;

public interface UserRegisterService {

	public UserRegister createdZomatoRegister(UserRequestDto userRequestDto);

}
