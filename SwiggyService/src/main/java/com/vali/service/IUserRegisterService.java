package com.vali.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vali.dto.LoginRequestDto;
import com.vali.dto.ReqByIdDto;
import com.vali.dto.UserRequestDto;
import com.vali.entity.FileData;
import com.vali.entity.UserRegister;

public interface IUserRegisterService {

	public UserRegister createSwiggyUserRegister(UserRequestDto userRequestDto);
	
	public UserRegister createSwiggyUserRegisterWithImages(UserRequestDto userRequestDto, MultipartFile[] files);
	
	public UserRegister login(LoginRequestDto loginRequestDto);
	
	public  List<UserRegister> findAllSwiggyUser();
	
	public ReqByIdDto findSwiggyUserById(Long id);
	
	public UserRegister updateUserByEmail(String email, UserRequestDto userRequestDto);
	
	public void deleteByEmail(String email);
	
	public FileData uploadFile(MultipartFile file) throws Exception;
	
	public List<FileData> uploadMultipleFile(MultipartFile[] files) throws Exception;
}
