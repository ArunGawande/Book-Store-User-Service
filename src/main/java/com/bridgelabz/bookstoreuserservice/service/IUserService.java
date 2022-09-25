package com.bridgelabz.bookstoreuserservice.service;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.UserResponse;

import java.util.List;

public interface IUserService {

	UserModel addBookStoreUser(UserDTO userDTO);
	
	UserModel updateBookStoreUser(UserDTO userDTO, String token);

	List<UserModel> getAllBookStoreUsers(String token);

	UserModel fetchUserById(String token);

	UserModel deleteUserById(String token);
	
	UserResponse loginUser(String emailId, String password);

	UserModel changePassword(String token, String password, String newPassword);

	UserModel forgotpassword(String emailId);

	UserModel resetPassword(String newPassword, String confirmPassword, String token);

	Boolean validateUser(String token);

	UserModel sendOTP(String token);

	UserModel validateOTP(Long otp, String token);

	UserResponse validateUserId(Long userId);

	UserModel purchaseSubscription(String token);
}
