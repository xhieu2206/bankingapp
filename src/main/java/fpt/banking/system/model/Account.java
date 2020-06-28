package fpt.banking.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "account")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account implements Serializable {
	
	private static final long serialVersionUID = 8372487453190651199L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@JsonIgnore
	@Column(name = "status", nullable = false)
	private boolean status;
	
	@Column(name = "amount")
	private long amount;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "expired_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date expiredAt;
	
	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name = "updated_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	// Relationship ---------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "account",
			cascade = { CascadeType.ALL })
	@JsonIgnore
	private List<Transaction> transactions;
	
	@OneToOne(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "account"
			)
	private Card card;
	// ----------------------------------
	
	// Constructor ----------------------
	public Account() { }

	public Account(Long id, boolean status, int amount,
			Date expiredAt, Date createdAt, Date updatedAt,
			String accountNumber) {
		this.id = id;
		this.status = status;
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.expiredAt = expiredAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Account(boolean status, int amount,
			Date expiredAt, Date createdAt, Date updatedAt,
			String accountNumber) {
		this.status = status;
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.accountNumber = accountNumber;
		this.expiredAt = expiredAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	// ----------------------------------

	// GETTER/SETTER
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	// ----------------------------------
}
