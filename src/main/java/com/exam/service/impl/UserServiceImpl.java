package com.exam.service.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.repository.RoleRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User createUser(User user, Set<UserRole> userRole) throws Exception {

		User local = this.userRepository.findByUsername(user.getUsername());
		if (Objects.nonNull(local)) {
			log.info("User already present with Id: {}", user.getUsername());
			throw new Exception("User already present !!");
		} else {
			for (UserRole ur : userRole) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRole);
			local = this.userRepository.save(user);
		}

		return local;
	}

	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public void deteleUser(Long userId) {
		this.userRepository.deleteById(userId);		
	}

}
