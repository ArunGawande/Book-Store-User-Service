package com.bridgelabz.bookstoreuserservice.model;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "bookstore_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	private String firstName;
	private String lastName;
	private String kyc;
	private String dob;
	private String emailId;
	private String password;
	private String phoneno;
	private LocalDateTime registerDate;
	private LocalDateTime updatedDate;
	private boolean verify;
	private long otp;
	private Date purchaseDate;
	private Date expiryDate;
	@Lob
	private byte[] profilepic;
	
	public UserModel(UserDTO userDto) {
		this.firstName = userDto.getFirstName();
		this.lastName = userDto.getLastName();
		this.kyc = userDto.getKyc();
		this.dob = userDto.getDob();
		this.emailId = userDto.getEmailId();
		this.password = userDto.getPassword();
		this.phoneno = userDto.getPhoneno();
	}
}
