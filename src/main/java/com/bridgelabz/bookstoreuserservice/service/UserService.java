package com.bridgelabz.bookstoreuserservice.service;

import com.bridgelabz.bookstoreuserservice.util.TokenUtil;
import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.exception.UserNotFoundException;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.repository.UserRepository;
import com.bridgelabz.bookstoreuserservice.util.Email;
import com.bridgelabz.bookstoreuserservice.util.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
 * Purpose :implementation of BookStoreUser
 */

@Service
@Slf4j
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	MailService mailService;


	@Autowired
	Email email;



	@Override
	public UserModel addBookStoreUser(UserDTO userDTO) {
		Optional<UserModel> userCheck = userRepository.findByemailId(userDTO.getEmailId());
		if (userCheck.isPresent()) {
			log.error("Email already exists");
			throw new UserNotFoundException(200, "Email already exists");
		}
		else {
			System.out.println("Welcome to BookStore");
			UserModel userModel = new UserModel(userDTO);
			userModel.setRegisterDate(LocalDateTime.now());
			userRepository.save(userModel);
			String body = "User is Added successfully with id " + userModel.getUserId();
			String subject = "User Added BookStore Successfully";
			mailService.send(userModel.getEmailId(), subject, body);
			return userModel;
		}
	}

	@Override
	public UserModel updateBookStoreUser(UserDTO userDTO, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			isUserPresent.get().setFirstName(userDTO.getFirstName());
			isUserPresent.get().setLastName(userDTO.getLastName());
			isUserPresent.get().setKyc(userDTO.getKyc());
			isUserPresent.get().setDob(userDTO.getDob());
			isUserPresent.get().setEmailId(userDTO.getEmailId());
			isUserPresent.get().setPhoneno(userDTO.getPhoneno());
			isUserPresent.get().setUpdatedDate(LocalDateTime.now());
			userRepository.save(isUserPresent.get());
			String body = "User is Updated successfully with id " + isUserPresent.get().getUserId();
			String subject = "User Updated in BookStore Successfully";
			mailService.send(isUserPresent.get().getEmailId(), subject, body);
			return isUserPresent.get();
		}
		throw new UserNotFoundException(500, "User Not Found");
	}

	@Override
	public List<UserModel> getAllBookStoreUsers(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			List<UserModel> getAllUsers = userRepository.findAll();
			if (getAllUsers.size()>0) {
				return getAllUsers;
			}
			throw new UserNotFoundException(500, "No Users Found");
		}
		throw new UserNotFoundException(500, "User Id Not Found");
	}


	@Override
	public UserModel fetchUserById(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			return isUserPresent.get();
		}
		throw new UserNotFoundException(500, "User Id Not Found");
	}

	@Override
	public UserModel deleteUserById(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			userRepository.delete(isUserPresent.get());
			return isUserPresent.get();
		}
		throw new UserNotFoundException(500, "User Id Not Found");
	}

	@Override
	public UserResponse loginUser(String emailId, String password) {
		Optional<UserModel> isEmailPresent = userRepository.findByemailId(emailId);
		if (isEmailPresent.isPresent()) {
			if (isEmailPresent.get().getPassword().equals(password)) {
				String token = tokenUtil.createToken(isEmailPresent.get().getUserId());
				return new UserResponse(200, "Login Successfully", token);
			}
			throw new UserNotFoundException(500,"PasswordNotMatched");
		}
		throw new UserNotFoundException(500,"EmailId Not Found");
	}

	@Override
	public UserModel changePassword(String token, String password, String newPassword) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(decode);
		if (isUserPresent.isPresent()) {
			log.info("abc");
			if (isUserPresent.get().getPassword().equals(password) ) {
				log.info("abc");
				isUserPresent.get().setPassword(newPassword);
				userRepository.save(isUserPresent.get());
				String body = "User Password changed successfully with new Password is" + isUserPresent.get().getPassword();
				String subject = "User Password changed successfully in BookStore";
				mailService.send(isUserPresent.get().getEmailId(), subject, body);
				return isUserPresent.get();
			}
			throw new UserNotFoundException(500, "User Password Not Matched");
		}
		throw new UserNotFoundException(500, "User Id Not Found");
	}

	/*
	 *  reset password link sent to BookStore User
	 */

	@Override
	public UserModel forgotpassword(String emailId) {
		Optional<UserModel> isEmailPresent = userRepository.findByemailId(emailId);
		if (isEmailPresent.isPresent()) {
			String token = tokenUtil.createToken(isEmailPresent.get().getUserId());
			String url = "Click on below link to Reset your Password \n"+ "http://localhost:8081/bookstoreuser/resetpassword" + token;
			String subject = "User reset password Link From BookStore";
			mailService.send(isEmailPresent.get().getEmailId(), subject, url);
			return isEmailPresent.get();
		}
		throw new UserNotFoundException(500, "Invalid EmailId");
	}

	@Override
	public UserModel resetPassword(String newPassword, String confirmPassword, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			if(newPassword.equals(confirmPassword) ) {
				isUserPresent.get().setUpdatedDate(LocalDateTime.now());
				userRepository.save(isUserPresent.get());
				String body = "User Password Reset successfully with new Password is : " + isUserPresent.get().getPassword();
				String subject = "User Password Reset successfully in BookStore";
				mailService.send(isUserPresent.get().getEmailId(), subject, body);
				return isUserPresent.get();
			}
			throw new UserNotFoundException(500, "newpassword and confirmed password mismatch");
		}
		throw new UserNotFoundException(500, "Invalid UserId");
	}
	
	/*
	 *  @Purpose:verify  User
	 */

	@Override
	public Boolean validateUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent())
			return true;
		throw new UserNotFoundException(500, "Token Id Not Found");
	}
	
	/*
	 *  @Purpose :Send OTP to User
	 */

	@Override
	public UserModel sendOTP(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			long min = 100000, max = 999999;
			long random_long = (long)(Math.random() * (max - min + 1) + min);
			isUserPresent.get().setOtp(random_long);
			isUserPresent.get().setUpdatedDate(LocalDateTime.now());
			userRepository.save(isUserPresent.get());
			String body = isUserPresent.get().getOtp() + " is your OTP. Please do not share it with anybody";
			String subject = "BOOKSTORE-USEROTP";
			mailService.send(isUserPresent.get().getEmailId(), subject, body);
			return isUserPresent.get();
		}
		throw new UserNotFoundException(500, "Invalid UserId");
	}

	/*
	 *  @Purpose: Validate  User OTP
	 */

	@Override
	public UserModel validateOTP(Long otp, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			if (isUserPresent.get().getOtp() == otp) {
				isUserPresent.get().setVerify(true);
				return isUserPresent.get();
			}
			else {
				throw new UserNotFoundException(500, "Invalid OTP");
			}
		}
		throw new UserNotFoundException(500, "Invalid UserId");
	}

	/*
	 *  @Purpose : purchase Subscription date and set expire date
	 */

	@Override
	public UserModel purchaseSubscription(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			isUserPresent.get().setPurchaseDate(new Date());
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 12);
			Date expireDate = calendar.getTime();
			isUserPresent.get().setExpiryDate(expireDate);
			userRepository.save(isUserPresent.get());
			return isUserPresent.get();
		}
		throw new UserNotFoundException(500, "User Not Found");
	}
	
	/*
	 *  @Purpose:Validate  User
	 */

	@Override
	public UserResponse validateUserId(Long userId) {
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			return new UserResponse(200,"User Validate Successfully",isUserPresent.get());
		}
		throw new UserNotFoundException(500, "User Not Found");
	}



}
