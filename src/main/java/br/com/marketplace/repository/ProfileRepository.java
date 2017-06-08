package br.com.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.marketplace.entity.Profile;
import br.com.marketplace.entity.User;


public interface ProfileRepository extends CrudRepository<Profile, Long> {

	Profile findByName(String name);

	@Query(value = "SELECT p FROM Profile p INNER JOIN p.users u WHERE u IN (:users)")
	List<Profile> findByUsers(@Param("users") List<User> users);

}
