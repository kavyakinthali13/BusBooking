package com.learn.busBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.busBooking.Dto.UserDto;
import com.learn.busBooking.exception.InvalidCredentials;
import com.learn.busBooking.exception.UserNotFound;
import com.learn.busBooking.model.User;
import com.learn.busBooking.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public User getUser(UserDto userDto) {
		User user = new User();
		user = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
		if (user!=null) {
			return user;
		} else {
			throw new InvalidCredentials("incorrect email or password");
		}

	}
	public User getUserById(long id) {
		User user = new User();
		user = userRepository.findById(id).orElseThrow(()->new UserNotFound("invalid details"));
			return user;
	}

	public User UpdateUser(User user) throws UserNotFound {
		if (user != null) {
			return userRepository.save(user);
		} else {
			throw new UserNotFound("user object should not be empty");
		}
	}
	
}
