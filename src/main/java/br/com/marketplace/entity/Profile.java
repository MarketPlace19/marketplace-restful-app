package br.com.marketplace.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.marketplace.enums.EnumProfile;


@Entity
@Table(name = "tb_profile")
public class Profile extends ErrorMessageEntity implements GrantedAuthority {

	private static final long serialVersionUID = 3645590858397959872L;
	
	public Profile() {
	}
	
	public Profile(EnumProfile profile) {
		this.name = profile.getName();
		this.desc = profile.getDescription();
	}
	
	public Profile(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "profile_id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String desc;
	
	@ManyToMany(mappedBy = "profiles")
	@JsonBackReference
	private List<User> users;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	@Transient
	public String getAuthority() {
		return name;
	}
	
}
