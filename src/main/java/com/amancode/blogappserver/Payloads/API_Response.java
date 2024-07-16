package com.amancode.blogappserver.Payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class API_Response {
	
	private String message;
	private Boolean success;
	
	

}
