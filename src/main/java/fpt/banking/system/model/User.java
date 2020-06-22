package fpt.banking.system.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
	
	@Column(name = "email", length = 255, nullable = false)
	private String email;

	@JsonIgnore
	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "fullname", length = 255, nullable = false)
	private String fullname;

	@Column(name = "birthday", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "address", length = 500, nullable = true)
	private String address;
	
	@Column(name = "gender", length = 10, nullable = false)
	private String gender;
	
	@Column(name = "image", length = 255, nullable = true)
	private String image;
	
	@Column(name = "id_card_number", length = 50, nullable = false)
	private String idCardNumber;
	
	@Column(name = "phone", length = 50, nullable = true)
	private String phone;
	
	@Column(name = "attemped_login_failed")
	private int attempedLoginFailed;
	
	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name = "updated_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	@JsonIgnore
	@Column(name = "status", nullable = false)
	private boolean status;
	
	// Relationship
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "transaction_office_id")
	private TransactionOffice transactionOffice;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "membership_id")
	private Membership membership;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinColumn(name = "branch_office_id")
	private BranchOffice branchOffice;
	
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "user",
			cascade = {
				CascadeType.ALL
			})
	private List<Account> accounts;
	// ---------------------------------------
	
	// Constructor 
	public User() { }
    
	public User(String username, String email, String password, String fullname, Date birthday, String address,
			String gender, String image, String idCardNumber, String phone, int attempedLoginFailed, Date createdAt,
			Date updatedAt, boolean status) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
		this.image = image;
		this.idCardNumber = idCardNumber;
		this.phone = phone;
		this.attempedLoginFailed = attempedLoginFailed;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}

	public User(Long id, String username, String email, String password, String fullname, Date birthday, String address,
			String gender, String image, String idCardNumber, String phone, int attempedLoginFailed, Date createdAt,
			Date updatedAt, boolean status) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
		this.image = image;
		this.idCardNumber = idCardNumber;
		this.phone = phone;
		this.attempedLoginFailed = attempedLoginFailed;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}
	// ---------------------------------------

	// for role
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	// ---------------------------------------

	// GETTER/SETTER
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAttempedLoginFailed() {
		return attempedLoginFailed;
	}

	public void setAttempedLoginFailed(int attempedLoginFailed) {
		this.attempedLoginFailed = attempedLoginFailed;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public TransactionOffice getTransactionOffice() {
		return transactionOffice;
	}

	public void setTransactionOffice(TransactionOffice transactionOffice) {
		this.transactionOffice = transactionOffice;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}	
	// ---------------------------------------
}
