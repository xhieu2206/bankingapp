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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "card")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Card {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;

	@Column(name = "card_number")
	private String cardNumber;

	@JsonIgnore
	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "expired_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date expiredAt;

	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	// Relationship -------------------------
	@OneToOne(fetch = FetchType.LAZY,
			cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE,
			CascadeType.REFRESH,
			CascadeType.DETACH
	})
	@JoinColumn(name = "account_id")
	private Account account;
	// --------------------------------------
	
	// Constructor --------------------------
	public Card() {
	}
	
	
	public Card(Long id, String cardNumber, boolean status, Date expiredAt, Date createdAt) {
		this.id = id;
		this.cardNumber = cardNumber;
		this.status = status;
		this.expiredAt = expiredAt;
		this.createdAt = createdAt;
	}

	public Card(String cardNumber, boolean status, Date expiredAt, Date createdAt) {
		this.cardNumber = cardNumber;
		this.status = status;
		this.expiredAt = expiredAt;
		this.createdAt = createdAt;
	}
	// --------------------------------------

	// SETTER/GETTER ------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	// ---------------------------------------
}
