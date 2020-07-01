package fpt.banking.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transaction_queue_internal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TransactionQueueInternal {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "otp_code")
	private String otpCode;
	
	@Column(name = "tranfer_account_id")
    private Long tranferAccountId;
	
	@Column(name = "receiver_account_id")
    private Long receiverAccountId;
	
	@Column(name = "amount")
	private long amount;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "expried_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date expriedAt;

	public TransactionQueueInternal() {
	}

	public TransactionQueueInternal(Long id, String otpCode, Long tranferAccountId, Long receiverAccountId, long amount, String description,
			Date expriedAt) {
		this.id = id;
		this.otpCode = otpCode;
		this.tranferAccountId = tranferAccountId;
		this.receiverAccountId = receiverAccountId;
		this.amount = amount;
		this.description = description;
		this.expriedAt = expriedAt;
	}

	public TransactionQueueInternal(String otpCode, Long tranferAccountId, Long receiverAccountId, long amount, String description,
			Date expriedAt) {
		this.otpCode = otpCode;
		this.tranferAccountId = tranferAccountId;
		this.receiverAccountId = receiverAccountId;
		this.amount = amount;
		this.description = description;
		this.expriedAt = expriedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public Long getTranferAccountId() {
		return tranferAccountId;
	}

	public void setTranferAccountId(Long tranferAccountId) {
		this.tranferAccountId = tranferAccountId;
	}

	public Long getReceiverAccountId() {
		return receiverAccountId;
	}

	public void setReceiverAccountId(Long receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
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

	public java.util.Date getExpriedAt() {
		return expriedAt;
	}

	public void setExpriedAt(java.util.Date expriedAt) {
		this.expriedAt = expriedAt;
	}
}
