package fpt.banking.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "amount", nullable = false)
	private int amount;
	
	@Column(name = "amount_after_transaction", nullable = false)
	private int amountAfterTransaction;
	
	@Column(name = "description", length = 500, nullable = false)
	private String description;
	
	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	// Relationship -------------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
	// --------------------------------------

	// Constructor --------------------------
	public Transaction() {
	}

	public Transaction(Long id, int amount, int amountAfterTransaction, String description, Date createdAt) {
		this.id = id;
		this.amount = amount;
		this.amountAfterTransaction = amountAfterTransaction;
		this.description = description;
		this.createdAt = createdAt;
	}

	public Transaction(int amount, int amountAfterTransaction, String description, Date createdAt) {
		this.amount = amount;
		this.amountAfterTransaction = amountAfterTransaction;
		this.description = description;
		this.createdAt = createdAt;
	}
	// --------------------------------------
	
	// GETTER/SETTER ------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmountAfterTransaction() {
		return amountAfterTransaction;
	}

	public void setAmountAfterTransaction(int amountAfterTransaction) {
		this.amountAfterTransaction = amountAfterTransaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	// --------------------------------------
}
