package fpt.banking.system.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transaction_office")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TransactionOffice {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "information", nullable = false)
	private String information;
	
	// Relationship
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE,
			CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "branch_office_id")
	private BranchOffice branchOffice;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "transactionOffice",
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	private List<User> users;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "transactionOffice",
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	private List<LoanProfile> loanProfiles;
	// -------------------------------------
	
	// Constructor --------------------------
	public TransactionOffice() { }

	public TransactionOffice(String address, String name, String information) {
		this.address = address;
		this.name = name;
		this.information = information;
	}
	
	public TransactionOffice(Long id, String address, String phone, String name, String information) {
		this.id = id;
		this.address = address;
		this.phone = phone;
		this.name = name;
		this.information = information;
	}

	// -------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<LoanProfile> getLoanProfiles() {
		return loanProfiles;
	}

	public void setLoanProfiles(List<LoanProfile> loanProfiles) {
		this.loanProfiles = loanProfiles;
	}
}
