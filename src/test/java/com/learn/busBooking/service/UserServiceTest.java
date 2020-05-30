package com.learn.busBooking.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.learn.busBooking.Dto.UserDto;
import com.learn.busBooking.exception.UserNotFound;
import com.learn.busBooking.model.User;
import com.learn.busBooking.repository.UserRepository;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
	
		@InjectMocks
		UserService userService;

		@Mock
		UserRepository userRepository;
		
		@Test
		public void testUpdateUserForNegitive() throws UserNotFound   {
			User user=new User();
			user.setId(1l);;
			user.setPassword("kumar");
			user.setEmail("sai@gmail.com");
			Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn((user));
			User resUser = userService.UpdateUser(user);
			Assert.assertNotNull(resUser);
			Assert.assertEquals(user.getEmail(), resUser.getEmail());
		}
		
		@Test
		public void testUpdateUserForPositive() throws UserNotFound  {
			User user=new User();
			user.setId(1l);
			user.setPassword("kumar");
			user.setEmail("sai@gmail.com");
			Mockito.when(userRepository.save(user)).thenReturn((user));
			User resUser = userService.UpdateUser(user);
			Assert.assertNotNull(resUser);
			Assert.assertEquals(user.getPassword(), resUser.getPassword());
		}

		@Test(expected = NullPointerException.class)
		public void testSaveForNegitive() throws UserNotFound {
			User user=new User();
			Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(NullPointerException.class);
			User resUser = userService.UpdateUser(user);
		}
		@Test
		public void testGetUserForPositive() throws UserNotFound  {
			UserDto userDto = new UserDto();
			userDto.setEmail("sai@gmail.com");
			userDto.setPassword("sai");
			User user=new User();
			user.setId(1l);
			user.setPassword("kumar");
			user.setFirstname("sai");
			user.setEmail("sai@gmail.com");
			Mockito.when(userRepository.findByEmailAndPassword(userDto.getEmail(),userDto.getPassword())).thenReturn(user);
			user = userService.getUser(userDto);
			Assert.assertNotNull(user);
		}
		@Test
		public void testgetUserForNegitive() throws UserNotFound  {
			UserDto userDto = new UserDto();
			userDto.setEmail("sai@gmail.com");
			userDto.setPassword("sai");
			User user=new User();
			user.setId(1l);
			user.setPassword("kumar");
			user.setFirstname("saikumar");
			user.setEmail("sai@gmail.com");
			Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(user);

			user= userService.getUser(userDto);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getEmail(),userDto.getEmail());
		}
		
		@Test(expected = Exception.class)
		public void testgetUserForExce(){
			UserDto userDto = new UserDto();
			User user=new User();
			Mockito.when(userRepository.findByEmailAndPassword(userDto.getEmail(),userDto.getPassword())).thenThrow(Exception.class);
		}
		
		@SuppressWarnings("deprecation")
		@Test(expected=UserNotFound.class)
		public void testGetUserById() {
			User user=new User();
			user.setId(1L);
			user.setPassword("kumar");
			user.setFirstname("sai");
			user.setEmail("sai@gmail.com");
			user.setActivated(true);
			user.setAdmin(true);
			Mockito.when(userRepository.findById(2L)).thenThrow(UserNotFound.class);
			user = userService.getUserById(user.getId());
			Assert.assertNotNull(user);
			
		}
}

