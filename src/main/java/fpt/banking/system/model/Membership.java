package fpt.banking.system.model;

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

@Entity
@Table(name = "membership")
public class Membership {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name")
	private String name;
	
	// Relationship
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "membership",
			cascade = {
				CascadeType.ALL
			})
	private List<User> users;
	// ------------------------------

	// Construtor
	public Membership() {
	}

	public Membership(String name, List<User> users) {
		this.name = name;
		this.users = users;
	}

	public Membership(Long id, String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
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
