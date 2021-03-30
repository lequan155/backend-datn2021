package com.datn2021.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn2021.dto.UserDTO;
import com.datn2021.model.User;
import com.datn2021.repo.UserRepository;


@Service
public class UserService {
	@Autowired  ModelMapper mapper;
	@Autowired UserRepository repo;
	public UserDTO getUserDTO (String username) {
		User user = repo.findByUserName(username);
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		
		return userDTO;
	}
}
