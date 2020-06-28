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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "amount", nullable = false)
	private long amount;
	
	@Column(name = "amount_after_transaction", nullable = false)
	private long amountAfterTransaction;
	
	@Column(name = "description", length = 500, nullable = false)
	private String description;
	
	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	// Relationship -------------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_type_id")
	private TransactionType transactionType;
	// --------------------------------------

	// Constructor --------------------------
	public Transaction() {
	}

	public Transaction(Long id, long amount, long amountAfterTransaction, String description, Date createdAt) {
		this.id = id;
		this.amount = amount;
		this.amountAfterTransaction = amountAfterTransaction;
		this.description = description;
		this.createdAt = createdAt;
	}

	public Transaction(long amount, long amountAfterTransaction, String description, Date createdAt) {
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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getAmountAfterTransaction() {
		return amountAfterTransaction;
	}

	public void setAmountAfterTransaction(long amountAfterTransaction) {
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

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	// --------------------------------------
}
