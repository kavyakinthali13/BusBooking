package com.learn.busBooking.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import com.learn.busBooking.Dto.UserDto;
import com.learn.busBooking.Dto.UserResponseDto;
import com.learn.busBooking.exception.UserNotFound;
import com.learn.busBooking.model.User;
import com.learn.busBooking.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;
	
	
	@Test
	public void testGetUserForPositive() throws UserNotFound {
		UserDto userDto = new UserDto();
		userDto.setEmail("sai@gmail.com");
		userDto.setPassword("sai");
		User user=new User();
		user.setId(1l);
		user.setPassword("sai");
		user.setFirstname("saikumar");
		user.setEmail("sai@gmail.com");
		Mockito.when(userService.getUser(userDto)).thenReturn(user);
		@SuppressWarnings("unused")
		ResponseEntity<UserResponseDto> user1 = userController.userLogin(userDto);
	}
	@Test
	public void testgetUserForNegitive() throws UserNotFound {
		UserDto userDto = new UserDto();
		userDto.setEmail("sai@gmail.com");
		userDto.setPassword("sai");
		User user=new User();
		user.setId(1l);
		user.setPassword("sai");
		user.setFirstname("saikumar");
		user.setEmail("sai@gmail.com");
		Mockito.when(userService.getUser(Mockito.any(UserDto.class))).thenReturn(user);

		@SuppressWarnings("unused")
		ResponseEntity<UserResponseDto> user1 = userController.userLogin(userDto);
		
	}
	
	@Test(expected = Exception.class)
	public void testgetUserForExce() throws UserNotFound{
		UserDto userDto = new UserDto();
		@SuppressWarnings("unused")
		User user=new User();
		Mockito.when(userService.getUser(userDto)).thenThrow(Exception.class);
	}

	

}
