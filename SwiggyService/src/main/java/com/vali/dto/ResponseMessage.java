package com.vali.dto;

import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMessage {

	private Integer statusCode;
	private String status;
	private String message;
	private Object data;
	private List<?> list;
	
	public ResponseMessage(Integer statusCode, String status, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.data = data;
		
	}

	public ResponseMessage(Integer statusCode, String status, String message) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
	}
	
	
	
	
}
