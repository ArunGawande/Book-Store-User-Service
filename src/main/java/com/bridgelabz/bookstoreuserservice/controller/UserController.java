package com.bridgelabz.bookstoreuserservice.controller;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.service.IUserService;
import com.bridgelabz.bookstoreuserservice.util.UserResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
 * Purpose : BookStoreUser Data API's
 *
 */

@RestController
@RequestMapping("/bookstoreuser")
public class UserController {

	@Autowired
    IUserService userService;
	
	/*
	 *@Purpose:create User
	 */

	@PostMapping("adduser")
	public ResponseEntity<UserResponse> addBookStoreUser(@Valid @RequestBody UserDTO userDTO){
		UserModel userModel = userService.addBookStoreUser(userDTO);
		UserResponse userResponse = new UserResponse(200, "user added successfully", userModel);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	/*
	 *@Purpose:Update User
	 *@Param :userDTO, token
	 */

	@PutMapping("/updateuser")
	public ResponseEntity<UserResponse> updateBookStoreUser(@Valid @RequestBody UserDTO userDTO, @RequestHeader String token) {
		UserModel userModel = userService.updateBookStoreUser(userDTO, token);
		UserResponse userResponse = new UserResponse(200, "user added successfully", userModel);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	/*
	 *@Purpose :get-all Users
	 */

	@GetMapping("fetchallusers")
	public ResponseEntity<UserResponse> getAllBookStoreUsers(@RequestHeader String token) {
		List<UserModel> userModel = userService.getAllBookStoreUsers(token);
		UserResponse userResponse = new UserResponse(200, "BookStore Users Fetched uccessfully", userModel);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	/*
	 *@Purpose :get User by id
	 */

	@GetMapping("/getuserbyid")
	public ResponseEntity<UserResponse> fetchUserById(@RequestHeader String token) {
		UserModel userModel = userService.fetchUserById(token);
		UserResponse userResponse = new UserResponse(200, "BookStore User Fetched uccessfully", userModel);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	/*
	 *@Purpose :delete user by id

	 */

	@DeleteMapping("removeuser")
	public ResponseEntity<UserResponse> deleteUserById(@RequestHeader String token) {
		UserModel userModel = userService.deleteUserById(token);
		UserResponse userResponse = new UserResponse(200, "BookStore User Deleted uccessfully", userModel);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	/*
	 *@Purpose :Login User
	 * @Param : emailId, password
	 */

	@PostMapping("login")
	public UserResponse loginUser(@RequestParam String emailId,@RequestParam String password) {
		return userService.loginUser(emailId, password);
	}
	
	/*
	 *@Purpose :change password
	 */

	@PutMapping("changepassword")
	public ResponseEntity<UserResponse> changePassword(@RequestHeader String token,@Valid @RequestParam String password,@Valid @RequestParam String newPassword) {
		UserModel userModel = userService.changePassword(token, password, newPassword);
		UserResponse userResponse = new UserResponse(200,"BookStore User Password Changed successfully",userModel);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	/*
	 *@Purpose :reset password
	 */

	@PostMapping("/forgotpassword")
	public ResponseEntity<UserResponse> forgotpassword(@RequestParam String emailId) {
		UserModel userModel = userService.forgotpassword(emailId);
		UserResponse userResponse = new UserResponse(200,"Reset Password Link Sent successfully");
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	/*
	 *@Purpose : reset password
	 * @Param : password, confirmPassword, token
	 */

	@PostMapping("/resetpassword")
	public ResponseEntity<UserResponse> resetPassword(@Valid @RequestParam String newPassword,@Valid @RequestParam String confirmPassword, @RequestHeader String token) {
		UserModel userModel = userService.resetPassword(newPassword,confirmPassword, token);
		UserResponse userResponse = new UserResponse(200,"Reset Password successfully",userModel);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	/*
	 *@Purpose :verify token
	 *@Param : token
	 */
	
	@GetMapping("/verify/{token}")
	public Boolean validateUser(@PathVariable String token) {
		return userService.validateUser(token);
	}
	
	/*
	 *@Purpose :send otp to user
	 *@Param : token
	 */
	
	@PutMapping("/sendotp")
	public ResponseEntity<UserResponse> sendOTP(@RequestHeader String token) {
		UserModel userModel = userService.sendOTP(token);
		UserResponse userResponse = new UserResponse(200,"Otp Sent successfully",userModel);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	/*
	 *@Purpose :verify user otp
	 *@Param : otp, token
	 */
	
	@PutMapping("/verifyotp/{otp}")
	public ResponseEntity<UserResponse> validateOTP(@PathVariable Long otp, @RequestHeader String token) {
		UserModel userModel = userService.validateOTP(otp, token);
		UserResponse userResponse = new UserResponse(200,"Otp Validate successfully",userModel);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	/*
	 *@Purpose : validate user otp
	 *
	 */
	
	@GetMapping("/validateuser/{userId}")
	public UserResponse validateUserId(@PathVariable Long userId) {
		return userService.validateUserId(userId);
	}
	
	/*
	 *@Purpose : purchasesubscription
	 *
	 */
	
	@PutMapping("/purchasesubscription")
	public ResponseEntity<UserResponse> purchaseSubscription(@RequestHeader String token) {
		UserModel userModel = userService.purchaseSubscription(token);
		UserResponse userResponse = new UserResponse(200,"Success",userModel);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
}
