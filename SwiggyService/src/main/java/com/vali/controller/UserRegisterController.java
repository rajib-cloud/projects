package com.vali.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vali.dto.LoginRequestDto;
import com.vali.dto.ReqByIdDto;
import com.vali.dto.ResponseMessage;
import com.vali.dto.UserRequestDto;
import com.vali.entity.FileData;
import com.vali.entity.UserRegister;
import com.vali.repository.UserRegisterRepository;
import com.vali.service.IUserRegisterService;
import com.vali.utility.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User Operations", description = "Swiggy User CRUD and Login APIs")
@RestController
public class UserRegisterController {

    @Autowired
	private IUserRegisterService service;

    UserRegisterController(UserRegisterRepository userRegisterRepository) {
    }

	@Operation(summary = "Register a new Swiggy user", description = "Creates a new Swiggy user if email and password are valid", responses = {
			@ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseMessage.class))) })
	@PostMapping("/swiggyRegister")
	public ResponseEntity<ResponseMessage> createUserRegister(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration details", required = true, content = @Content(schema = @Schema(implementation = UserRequestDto.class))) @Valid @RequestBody UserRequestDto userRequestDto) {

		try {
			if (userRequestDto.getEmail() == null || userRequestDto.getEmail().isEmpty()
					|| userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,
						"Email and password cannot be empty..!"));
			}
			UserRegister register = service.createSwiggyUserRegister(userRequestDto);
			if (register != null) {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS,
						"Swiggy user registered.", register));
			} else {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,
						"Swiggy user registration failed.", register));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED,
					"Internal Server Error"));
		}
	}

	@Operation(summary = "User login", description = "Authenticates user if email and password are valid", responses = {
			@ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseMessage.class))) })
	
	@PostMapping("/swiggyLogin")
	public ResponseEntity<ResponseMessage> loginUser(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login details", required = true, content = @Content(schema = @Schema(implementation = LoginRequestDto.class))) @Valid @RequestBody LoginRequestDto loginDto) {

		try {
			if (loginDto.getEmail() == null || loginDto.getEmail().isBlank() || loginDto.getPassword() == null
					|| loginDto.getPassword().isBlank()) {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,
						"Email and password cannot be empty..!"));
			}
			UserRegister loginUser = service.login(loginDto);
			if (loginUser != null) {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
						"Login successful", loginUser));
			} else {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,
						"Login failed. Incorrect email or password."));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED,
					"Internal Server Error"));
		}
	}

	@Operation(summary = "Update user by email", description = "Updates a user using the given email and new details", responses = {
			@ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ResponseMessage.class))) })
	
	@PutMapping("/swiggyUpdate/{email}")
	public ResponseEntity<ResponseMessage> updateUser(
			@Parameter(description = "User email to update") @PathVariable String email,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New user details", required = true, content = @Content(schema = @Schema(implementation = UserRequestDto.class))) @Valid @RequestBody UserRequestDto dto) {

		UserRegister updatedUser = service.updateUserByEmail(email, dto);
		if (updatedUser != null) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
					"User updated successfully", updatedUser));
		} else {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_NOT_FOUND, Constants.FAILURE,
					"User not found with email: " + email));
		}
	}

	@Operation(summary = "Delete user by email", description = "Deletes a Swiggy user using the provided email", responses = {
			@ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(schema = @Schema(implementation = ResponseMessage.class))) })
	
	@DeleteMapping("/swiggyDelete/{email}")
	public ResponseEntity<ResponseMessage> deleteUser(
			@Parameter(description = "User email to delete") @PathVariable String email) {

		service.deleteByEmail(email);
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
				"User deleted successfully with " + email));
	}

	@Operation(summary = "Retrive swiggy user by id", description = "Retrieves a user if the id is valid", responses = {
			@ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseMessage.class))) })
	
	@GetMapping("/getByid/{userId}")
	public ResponseEntity<ResponseMessage> getUserById(@PathVariable Long id) {
		ReqByIdDto swiggyUserById = service.findSwiggyUserById(id);
		if (swiggyUserById != null) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
					"User retrieved successfully with ID : " + id));
		} else {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_NOT_FOUND, Constants.FAILURE,
					"User not found with ID : " + id));
		}
	}

	@Operation(summary = "Find all swiggy  user", description = "Retrieves all the users", responses = {
			@ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseMessage.class))) })
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseMessage> getAllUser() {
		List<UserRegister> allSwiggyUser = service.findAllSwiggyUser();
		if (allSwiggyUser != null) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
					"All user retrieved successfully.", allSwiggyUser));
		} else {
			return ResponseEntity
					.ok(new ResponseMessage(HttpURLConnection.HTTP_NOT_FOUND, Constants.FAILURE, "User not found."));
		}
	}
	
	@PostMapping("/uploadOne")
	public ResponseEntity<ResponseMessage> saveOneFile(@RequestParam MultipartFile file)throws Exception{
		FileData uploadFile = service.uploadFile(file);
		if(uploadFile != null) {
			return ResponseEntity.ok(new ResponseMessage(
					HttpURLConnection.HTTP_OK,
					Constants.SUCCESS,
					"File saved successfully with Name::"+file.getOriginalFilename()
					));
		}else {
			return ResponseEntity.ok(new ResponseMessage(
					HttpURLConnection.HTTP_NOT_FOUND,
					Constants.FAILURE,
					"File not found"
					));
		}
	}
	
	@PostMapping("/uploadMulti")
	public ResponseEntity<ResponseMessage> saveMultipleFile(@RequestParam MultipartFile[] files)throws Exception{
		List<FileData> uploadMultipleFile = service.uploadMultipleFile(files);
		if(uploadMultipleFile != null) {
			return ResponseEntity.ok(new ResponseMessage(
					HttpURLConnection.HTTP_OK,
					Constants.SUCCESS,
					"File saved successfully with Name::"+files.length
					));
		}else {
			return ResponseEntity.ok(new ResponseMessage(
					HttpURLConnection.HTTP_NOT_FOUND,
					Constants.FAILURE,
					"File not found"
					));
		}
	}
	
//	@PostMapping("/swiggyregisterwithimage")
//	public ResponseEntity<ResponseMessage> createSwiggyRegisterImage
//	(@RequestParam String jsonData, @RequestParam MultipartFile[] files){
//		
//		try {
//			UserRequestDto userRequestDto = new ObjectMapper().readValue(jsonData, UserRequestDto.class);
//			UserRegister userRegister = service.createSwiggyUserRegisterWithImages(userRequestDto, files);
//			if(userRegister != null) {
//				return ResponseEntity.ok(new ResponseMessage(
//						HttpURLConnection.HTTP_CREATED,
//						Constants.SUCCESS,
//						"Swiggy user registered successfully",
//						userRegister
//						));
//			}else {
//				return ResponseEntity.ok(new ResponseMessage(
//						HttpURLConnection.HTTP_BAD_REQUEST,
//						Constants.FAILURE,
//						"Swiggy user register failed",
//						userRegister
//						));
//			}
//			
//		}catch(Exception e) {
//			return ResponseEntity.ok(new ResponseMessage(
//					HttpURLConnection.HTTP_BAD_GATEWAY,
//					Constants.FAILED,
//					"Internal server error"
//					));
//		}
//	}
	
	@Operation(summary = "Register swiggy user with images",description ="Register a new swiggy user with images(multipart request)" )
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",description = "User registered successfully"),
			@ApiResponse(responseCode = "400",description = "Invalid input",content = @Content),
			@ApiResponse(responseCode = "502", description = "Server error",content = @Content)
	})
	@PostMapping(value = "/swiggyregisterwithimage", consumes = "multipart/form-data")
	public ResponseEntity<ResponseMessage> createSwiggyRegisterImage(
	    @RequestPart("user")@Parameter(
	    		description = "swiggy user registration data",
	    		required = true,
	    		content = @Content(schema = @Schema(implementation = UserRequestDto.class))
	    		) UserRequestDto userRequestDto,
	    
	    @RequestPart("files")@Parameter(
	    		description = "image files",
	    		required = true,
	    		content = @Content(array = @ArraySchema(schema = @Schema(type = "string", format = "binary")))
	    		) MultipartFile[] files) {

	    try {
	        UserRegister userRegister = service.createSwiggyUserRegisterWithImages(userRequestDto, files);
	        if (userRegister != null) {
	            return ResponseEntity.ok(new ResponseMessage(
	                HttpURLConnection.HTTP_CREATED,
	                Constants.SUCCESS,
	                "Swiggy user registered successfully",
	                userRegister));
	        } else {
	            return ResponseEntity.ok(new ResponseMessage(
	                HttpURLConnection.HTTP_BAD_REQUEST,
	                Constants.FAILURE,
	                "Swiggy user register failed",
	                userRegister));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.ok(new ResponseMessage(
	            HttpURLConnection.HTTP_BAD_GATEWAY,
	            Constants.FAILED,
	            "Internal server error"));
	    }
	}

}
