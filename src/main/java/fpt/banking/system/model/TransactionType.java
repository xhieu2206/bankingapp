package fpt.banking.system.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transaction_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TransactionType implements Serializable {

	private static final long serialVersionUID = 8372487913112651199L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	// Constructor
	public TransactionType() {
	}

	public TransactionType(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public TransactionType(String name) {
		this.name = name;
	}
	// ---------------------------------------
	
	// Relationship --------------------------
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "transactionType",
			cascade = {
				CascadeType.ALL
			})
	@JsonIgnore
	private List<Transaction> transactions;
	// ---------------------------------------

	// GETTER/SETTER -------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	// ---------------------------------------
	
}
