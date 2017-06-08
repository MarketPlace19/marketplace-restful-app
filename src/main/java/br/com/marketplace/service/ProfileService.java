package br.com.marketplace.service;

import java.util.List;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;

public interface ProfileService extends GenericService<Profile> {

	Profile findProfileByName(String name);

	List<Profile> findProfilesByUser(User user);
	
}
