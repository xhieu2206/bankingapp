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
@Table(name = "loan_profile_queue")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanProfileQueue {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "loan_profile_id")
    private Long loanProfileId;
	
	@Column(name = "otp_code")
	private String otpCode;
	
	@Column(name = "expried_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date expriedAt;

	public LoanProfileQueue(Long id, String otpCode, long loanProfileId, Date expriedAt) {
		this.id = id;
		this.loanProfileId = loanProfileId;
		this.otpCode = otpCode;
		this.expriedAt = expriedAt;
	}

	public LoanProfileQueue() {
	}

	public LoanProfileQueue(String otpCode, long loanProfileId, Date expriedAt) {
		this.otpCode = otpCode;
		this.loanProfileId = loanProfileId;
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

	public java.util.Date getExpriedAt() {
		return expriedAt;
	}

	public void setExpriedAt(java.util.Date expriedAt) {
		this.expriedAt = expriedAt;
	}

	public Long getLoanProfileId() {
		return loanProfileId;
	}

	public void setLoanProfileId(Long loanProfileId) {
		this.loanProfileId = loanProfileId;
	}
	
}
