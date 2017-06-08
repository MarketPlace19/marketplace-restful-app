package br.com.marketplace.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.marketplace.enums.UserStatus;

@Entity
@Table(name = "tb_user")
public class User extends ErrorMessageEntity implements UserDetails {

	private static final long serialVersionUID = 8673226052812586383L;

	public User() {
	}
	
	public User(String name, String email, String username, String password, UserStatus status, List<Profile> profiles) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.status = status;
		this.profiles = profiles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 80)
	private String name;
	
	@Column(name = "email", nullable = false, unique = true, length = 80)
	private String email;
	
	@Column(name = "username", nullable = false, unique = true, length = 80)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@ManyToMany
	@JoinTable(name = "tb_user_profile", 
		joinColumns = {@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_profile_user_fk"))}, 
		inverseJoinColumns = {@JoinColumn(name = "profile_id", foreignKey = @ForeignKey(name = "user_profile_profile_fk"))}, 
		uniqueConstraints = 
			{ @UniqueConstraint(columnNames = {"user_id", "profile_id"}, name = "user_profile_pk") })
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private List<Profile> profiles;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return profiles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return status.equals(UserStatus.ACTIVE);
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return status.equals(UserStatus.ACTIVE);
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return status.equals(UserStatus.ACTIVE);
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return status.equals(UserStatus.ACTIVE);
	}
}
