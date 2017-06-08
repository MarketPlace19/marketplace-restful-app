package br.com.marketplace.service;


import java.util.List;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;

public interface UserService extends GenericService<User> {

	User findUserByEmail(String email);

	User findUserByUsername(String username);

	List<User> findUsersByProfiles(Profile... profiles);
}
