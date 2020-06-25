package fpt.banking.system.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "membership")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Membership {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name")
	private String name;
	
	// Relationship
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "membership",
			cascade = {
				CascadeType.PERSIST,
				CascadeType.MERGE,
				CascadeType.REFRESH,
				CascadeType.DETACH
			})
	@JsonIgnore
	private List<User> users;
	// ------------------------------

	// Construtor
	public Membership() {
	}

	public Membership(String name) {
		this.name = name;
	}

	public Membership(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	// ------------------------------
	
	// GETTER/SETTER
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	// ------------------------------
}
