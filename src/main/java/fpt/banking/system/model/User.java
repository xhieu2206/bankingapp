package fpt.banking.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "username", length = 255, nullable = false)
	private String username;
	
	@JsonIgnore
	@Column(name = "password", length = 255, nullable = false)
	private String password;
	
	@Column(name = "fullname", length = 255, nullable = false)
	private String fullname;
	
	@Column(name = "address", length = 500, nullable = true)
	private String address;
	
	@Column(name = "phone", length = 50, nullable = true)
	private String phone;
	
	@Column(name = "email", length = 255, nullable = false)
	private String email;
	
	@JsonIgnore
	@Column(name = "status", nullable = false)
	private boolean status;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

	public User(String username, String password, String fullname, String address, String phone, String email,
			boolean status) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.status = status;
	}

	public User(Long id, String username, String password, String fullname, String address, String phone, String email,
			boolean status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
