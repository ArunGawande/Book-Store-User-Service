package com.bridgelabz.bookstoreuserservice.exception.exceptionhandler;

import com.bridgelabz.bookstoreuserservice.exception.UserNotFoundException;
import com.bridgelabz.bookstoreuserservice.util.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UserExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	 public ResponseEntity<UserResponse> response(UserNotFoundException userNotFoundException) {
        UserResponse userResponse = new UserResponse();
        userResponse.setErrorCode(400);
        userResponse.setMessage(userNotFoundException.getMessage());
        return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<UserResponse>
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        UserResponse userResponce = new UserResponse();
        userResponce.setErrorCode(500);
        userResponce.setMessage(e.getMessage());
        return new ResponseEntity<UserResponse>(userResponce, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
