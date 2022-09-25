package com.bridgelabz.bookstoreuserservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
	@Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "User Name Invalid")
	private String firstName;
	@Pattern(regexp = "^[A-Z][a-z]{2,}$", message = "User Name Invalid")
	private String lastName;
	private String kyc;
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email Id")
	private String emailId;
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%!]).{8,}$", message = "Invalid Password")
	private String password;
	@NotNull
	private String dob;
	@Pattern(regexp = "^[1-9]{1}[0-9]{9}$", message = "Invalid Mobile Number")
	private String phoneno;
}
