package fpt.banking.system.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.*;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import fpt.banking.system.model.TransactionOffice;

@Entity
@Table(name = "branch_office")
public class BranchOffice {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "information", nullable = false)
	private String information;
	// ------------------------------------
	
	// Relationship -----------------------
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "branchOffice",
			cascade = { 
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	private List<TransactionOffice> transactionOffices;

	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "branchOffice",
			cascade = { 
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JsonIgnore
	private List<User> users;
	// --------------------------------------
	
	// Constructor
	public BranchOffice() { }

	public BranchOffice(Long id, String name, String address, String information) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.information = information;
	}

	public BranchOffice(String name, String address, String information) {
		this.name = name;
		this.address = address;
		this.information = information;
	}
	// --------------------------------------------

	// GETTER/SETTER ------------------------------
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public List<TransactionOffice> getTransactionOffices() {
		return transactionOffices;
	}

	public void setTransactionOffices(List<TransactionOffice> transactionOffices) {
		this.transactionOffices = transactionOffices;
	}
	// -------------------------------------
	
}
