package fpt.banking.system.model;

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
@Table(name = "loan_profile")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanProfile {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "amount")
	private long amount;
	
	@Column(name = "description", length = 500, nullable = true)
	private String description;
	
	@Column(name = "confirmed")
	private boolean confirmed;
	
	@Column(name = "approved")
	private boolean approved;
	
	@Column(name = "rejected")
	private boolean rejected;
	
	@Column(name = "rejected_reason", length = 500, nullable = true)
	private String rejectedReason;
	
	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	// Relationship -----------------
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "loan_interest_rate_id")
	private LoanInterestRate loanInterestRate;
	
	@OneToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "transaction_office_id")
	private TransactionOffice transactionOffice;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "loanProfile",
			cascade = {
				CascadeType.ALL
			})
	private List<Asset> assets;
	// ------------------------------
	
	// Construtor -------------------
	public LoanProfile() {
	}

	public LoanProfile(Long id, long amount, String description, boolean confirmed, boolean approved, boolean rejected,
			String rejectedReason, String status, Date createdAt) {
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.confirmed = confirmed;
		this.approved = approved;
		this.rejected = rejected;
		this.rejectedReason = rejectedReason;
		this.status = status;
		this.createdAt = createdAt;
	}

	public LoanProfile(long amount, String description, boolean confirmed, boolean approved, boolean rejected, String rejectedReason,
			String status, Date createdAt) {
		this.amount = amount;
		this.description = description;
		this.confirmed = confirmed;
		this.approved = approved;
		this.rejected = rejected;
		this.rejectedReason = rejectedReason;
		this.status = status;
		this.createdAt = createdAt;
	}
	// ------------------------------
	
	// GETTER/SETTER ----------------
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isRejected() {
		return rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public LoanInterestRate getLoanInterestRate() {
		return loanInterestRate;
	}

	public void setLoanInterestRate(LoanInterestRate loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TransactionOffice getTransactionOffice() {
		return transactionOffice;
	}

	public void setTransactionOffice(TransactionOffice transactionOffice) {
		this.transactionOffice = transactionOffice;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	// ------------------------------
}
