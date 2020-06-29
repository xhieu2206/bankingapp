package fpt.banking.system.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "cheque")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cheque {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "reciever_fullname", length = 255, nullable = false)
	private String recieverFullname;
	
	@Column(name = "reciever_id_card_number", length = 50, nullable = false)
	private String recieverIdCardNumber;
	
	@Column(name = "transaction_amount")
	private long transactionAmount;
	
	@Column(name = "status", nullable = false)
	private boolean status;
	
	@Column(name = "canceled", nullable = false)
	private boolean canceled;
	
	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name = "expired_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date expiredDate;

	// Relationship ---------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private Account account;
	// ----------------------------------
	
	// Constructor ----------------------
	public Cheque() { }
	
	public Cheque(Long id, String recieverFullname, String recieverIdCardNumber, long transactionAmount, boolean status,
			boolean canceled, Date createdAt, Date expiredDate) {
		this.id = id;
		this.recieverFullname = recieverFullname;
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.transactionAmount = transactionAmount;
		this.status = status;
		this.canceled = canceled;
		this.createdAt = createdAt;
		this.expiredDate = expiredDate;
	}

	public Cheque(String recieverFullname, String recieverIdCardNumber, long transactionAmount, boolean status,
			boolean canceled, Date createdAt, Date expiredDate) {
		this.recieverFullname = recieverFullname;
		this.recieverIdCardNumber = recieverIdCardNumber;
		this.transactionAmount = transactionAmount;
		this.status = status;
		this.canceled = canceled;
		this.createdAt = createdAt;
		this.expiredDate = expiredDate;
	}
	// ----------------------------------
	
	// GETTER/SETTER --------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecieverFullname() {
		return recieverFullname;
	}

	public void setRecieverFullname(String recieverFullname) {
		this.recieverFullname = recieverFullname;
	}

	public String getRecieverIdCardNumber() {
		return recieverIdCardNumber;
	}

	public void setRecieverIdCardNumber(String recieverIdCardNumber) {
		this.recieverIdCardNumber = recieverIdCardNumber;
	}

	public long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	// ----------------------------------
}
