package fpt.banking.system.payload;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AccountForAdminResponse {

	private long id;
	private String accountNumber;
	private long amount;
	private boolean status;
	private Date expiredAt;
	private Date createdAt;
	private Date updatedAt;

	public AccountForAdminResponse() {
	}

	public AccountForAdminResponse(long id, String accountNumber, long amount, boolean status, Date expiredAt, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.status = status;
		this.expiredAt = expiredAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
