package br.com.marketplace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;
import br.com.marketplace.repository.ProfileRepository;
import br.com.marketplace.service.ProfileService;


@Service("profileService")
@Transactional
public class ProfileServiceImpl extends GenericServiceImpl implements ProfileService {

	private static final long serialVersionUID = 6572080078374180997L;
	
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public List<Profile> findAll() {
		List<Profile> profiles = Lists.newArrayList(this.profileRepository.findAll());
		return profiles;
	}

	@Override
	public Profile findOne(long id) {
		Profile profile = this.profileRepository.findOne(id);
		return profile;
	}

	@Override
	public void add(Profile p) {
		this.profileRepository.save(p);
	}

	@Override
	public void update(Profile p) {
		Profile profile = this.findOne(p.getId());
		
		profile.setId(p.getId());
		profile.setName(p.getName());
		profile.setDesc(p.getDesc());
		
		this.add(profile);
	}

	@Override
	public void deleteById(long id) {
		Profile profile = this.findOne(id);
		
		this.profileRepository.delete(profile);
	}

	@Override
	public Profile findProfileByName(String name) {
		Profile profile = this.profileRepository.findByName(name);
		return profile;
	}

	@Override
	public List<Profile> findProfilesByUser(User user) {
		List<User> users = new ArrayList<User>();
		users.add(user);
		List<Profile> profiles = this.profileRepository.findByUsers(users);
		return profiles;
	}

}
