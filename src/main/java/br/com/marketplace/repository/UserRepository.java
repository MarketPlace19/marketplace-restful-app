package br.com.marketplace.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;

public interface UserRepository extends CrudRepository<User, Long>, Serializable {

	User findByEmail(String email);

	User findByUsername(String username);

	@Query(value = "SELECT u FROM User u INNER JOIN u.profiles p WHERE p IN (:profiles)")
	List<User> findByProfiles(@Param(value = "profiles") List<Profile> profiles);
}
