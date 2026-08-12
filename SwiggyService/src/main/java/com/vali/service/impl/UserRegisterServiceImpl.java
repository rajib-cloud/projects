package com.vali.service.impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vali.dto.LoginRequestDto;
import com.vali.dto.ReqByIdDto;
import com.vali.dto.UserRequestDto;
import com.vali.entity.FileData;
import com.vali.entity.UserRegister;
import com.vali.repository.FileRepository;
import com.vali.repository.UserRegisterRepository;
import com.vali.service.IUserRegisterService;

@Service
public class UserRegisterServiceImpl implements IUserRegisterService{

	@Autowired
	private UserRegisterRepository repo;
	
	@Autowired
	private FileRepository fileRepo;
	
	
	
	@Override
	@CachePut(value = "User", key = "#dto.email")
	public UserRegister createSwiggyUserRegister(UserRequestDto dto) {
		UserRegister user = null;
		try {
			user = new UserRegister();
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setEmail(dto.getEmail());
			user.setPassword(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()));
			user.setContactId(dto.getContactId());
			repo.save(user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	@Cacheable(value = "User", key = "#dto.email")
	public UserRegister login(LoginRequestDto dto) {
		try {
			Optional<UserRegister> optional = repo.findByEmail(dto.getEmail());
			if(optional.isPresent()) {
				UserRegister user = optional.get();
				String pwd = new String(Base64.getDecoder().decode(user.getPassword()));
				if(dto.getPassword().equals(pwd)) {
					return user;
				}
			}
		}catch(Exception e) {
			
		}
		return null;
	}

	@Override
	@CachePut(value = "user", key = "#dto.email")
	public UserRegister updateUserByEmail(String email, UserRequestDto dto) {
		Optional<UserRegister> optional = repo.findByEmail(email);
		if(optional.isPresent()) {
			UserRegister user = optional.get();
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setContactId(dto.getContactId());
			if(dto.getPassword() != null && !dto.getPassword().isBlank()) {
				user.setPassword(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()));
			}
			repo.save(user);
		}
		return null;
		
	}

	@Override
	@CacheEvict(value = "user", key = "#email")
	public void deleteByEmail(String email) {
		
		repo.deleteByEmail(email);
		
	}

	@Override
	@Cacheable(value = "User", key = "#list")
	public List<UserRegister> findAllSwiggyUser() {
		
		return repo.findAll();
	}

	@Override
	@Cacheable(value = "User", key = "#dto.id")
	public ReqByIdDto findSwiggyUserById(Long id) {

		UserRegister user = repo.findById(id).orElse(null);
		
		return new ReqByIdDto(user.getFirstName(),user.getEmail());
	}
	
	
	@Override
	@CachePut(value = "File", key = "#Multipart")
	public FileData uploadFile(MultipartFile file) throws Exception{
		if(file == null || file.isEmpty()) {
			throw new IllegalArgumentException("Upload file is empty");
		}
		
		FileData fis = new FileData();
		fis.setFileName(file.getOriginalFilename());
		fis.setFileType(file.getContentType());
		fis.setFileData(file.getBytes());
		
		return fileRepo.save(fis);
		
	}
	
//	public List<FileData> uploadMultipleFile(MultipartFile[] files)throws Exception{
//		 List<FileData> savedFiles = new ArrayList<>();
//		 
//		 if(files != null && files.length>0) {
//			 for(MultipartFile mf : files) {
//				 FileData fis = new FileData();
//				 fis.setFileName(mf.getName());
//				 fis.setFileType(mf.getContentType());
//				 fis.setFileData(mf.getBytes());
//				 savedFiles.add(fileRepo.save(fis));
//			 }
//		 }
//		 return savedFiles;	
//	}
	
	@Override
	@CachePut(value = "Files", key = "#Multipart")
	public List<FileData> uploadMultipleFile(MultipartFile[] files)throws Exception{
		if(files == null || files.length == 0) {
			return Collections.emptyList();
		}
		
		return Arrays.stream(files)
	            .map(file -> {
	                try {
	                    FileData data = new FileData();
	                    data.setFileName(file.getOriginalFilename());
	                    data.setFileType(file.getContentType());
	                    data.setFileData(file.getBytes());
	                    return fileRepo.save(data);
	                } catch (Exception e) {
	                    throw new RuntimeException("Failed to process file: " + file.getOriginalFilename(), e);
	                }
	            })
	            .collect(Collectors.toList());	
				
	}

@Override
public UserRegister createSwiggyUserRegisterWithImages(UserRequestDto userRequestDto, MultipartFile[] files) {
	UserRegister user  = null;
	try {
		user = new UserRegister();
		user.setFirstName(userRequestDto.getFirstName());
		user.setLastName(userRequestDto.getLastName());
		user.setEmail(userRequestDto.getEmail());
		user.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
		user.setContactId(userRequestDto.getContactId());
		repo.save(user);
		
		if(files != null && files.length>0) {
			for(MultipartFile file : files) {
				FileData fd = new FileData();
				fd.setFileName(file.getOriginalFilename());
				fd.setFileType(file.getContentType());
				fd.setFileData(file.getBytes());
				fileRepo.save(fd);
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	return user;
}

	
	

 }
