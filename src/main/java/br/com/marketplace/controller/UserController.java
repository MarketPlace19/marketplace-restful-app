package br.com.marketplace.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;
import br.com.marketplace.enums.EnumProfile;
import br.com.marketplace.service.ProfileService;
import br.com.marketplace.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController implements Serializable {

	private static final long serialVersionUID = 4520130716597010878L;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/test/{nome}", method = RequestMethod.GET)
	public String getTest(@PathVariable(value = "nome") String nome) {
		return "Olá "+ nome + " bem-vindo à Aplicação !!!";
	}
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = this.userService.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("bookId") long id) {
		User user = this.userService.findOne(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Void> addUser(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		this.userService.add(user);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		User currentUser = this.userService.findOne(id);
		currentUser.setEmail(user.getEmail());
		currentUser.setName(user.getName());
		currentUser.setPassword(user.getPassword());
		currentUser.setUsername(user.getUsername());
		currentUser.setStatus(user.getStatus());
		currentUser.setProfiles(user.getProfiles());
		this.userService.update(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		User user = this.userService.findOne(id);
		this.userService.deleteById(user.getId());
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/listAllConsumers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> listAllConsumers() {
		Profile profileConsumer = this.profileService.findProfileByName(EnumProfile.CONSUMER.getName());
		return  listByProfile(profileConsumer);
	}

	@RequestMapping(value = "/listAllProviders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> listAllProviders() {
		Profile profileProvider = this.profileService.findProfileByName(EnumProfile.PROVIDER.getName());
		return  listByProfile(profileProvider);
	}

	private ResponseEntity<List<User>> listByProfile(Profile profile) {
		List<User> users = this.userService.findUsersByProfiles(profile);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
}
