package br.com.marketplace.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;
import br.com.marketplace.exception.EmptyUserListException;
import br.com.marketplace.exception.UserEmptyRequiredFieldsException;
import br.com.marketplace.exception.UserNotFoundException;
import br.com.marketplace.repository.UserRepository;
import br.com.marketplace.service.UserService;


@Service("userService")
@Transactional
public class UserServiceImpl extends GenericServiceImpl implements UserService {

	private static final long serialVersionUID = -5785263643244129259L;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		List<User> users = Lists.newArrayList(this.userRepository.findAll());
		return users;
	}

	@Override
	public User findOne(long id) {
		User user = this.userRepository.findOne(id);
		
		if (user == null || !(user.getId() != 0)) 
			throw new UserNotFoundException("User not found!");
		
		return user;
	}

	@Override
	public void add(User u) {
		if (!this.validateFields(u)) 
			throw new UserEmptyRequiredFieldsException("One or more required fields is empty.");
		
		
		this.userRepository.save(u);
		
	}

	private boolean validateFields(User u) {
		boolean nameIsNotEmpty = u.getName() != null && !u.getName().isEmpty();
		boolean emailIsNotEmpty = u.getEmail() != null && !u.getEmail().isEmpty();
		boolean usernameIsNotEmpty = u.getUsername() != null && !u.getUsername().isEmpty();
		boolean passwordIsNotEmpty = u.getPassword() != null && !u.getPassword().isEmpty();
		
		return nameIsNotEmpty && emailIsNotEmpty && usernameIsNotEmpty && passwordIsNotEmpty;
	}
	
	@Override
	public void update(User u) {
		User user = this.userRepository.findOne(u.getId());
		user.setId(u.getId());
		user.setName(u.getName());
		user.setEmail(u.getEmail());
		user.setUsername(u.getUsername());
		user.setPassword(u.getPassword());
		user.setStatus(u.getStatus());
		user.setProfiles(u.getProfiles());
		
		this.add(user);
	}

	@Override
	public void deleteById(long id) {
		User user = this.userRepository.findOne(id);
		this.userRepository.delete(user);
	}

	@Override
	public User findUserByEmail(String email) {
		User user = this.userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		User user = this.userRepository.findByUsername(username);
		return user;
	}

	@Override
	public List<User> findUsersByProfiles(Profile... profiles) {
		List<Profile> profileList = new ArrayList<Profile>(Arrays.asList(profiles));
		List<User> users = this.userRepository.findByProfiles(profileList);

		if (users == null || users.isEmpty())
			throw new EmptyUserListException("Query returned 0 results");

		return users;
	}
}
