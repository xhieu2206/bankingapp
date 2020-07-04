package fpt.banking.system.model;

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
@Table(name = "loan_interest_rate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanInterestRate {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "interest_rate")
	private double interestRate;
	
	@Column(name = "months")
	private int months;
	
	// Relationship -----------------
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "loanInterestRate",
			cascade = {
				CascadeType.ALL
			})
	@JsonIgnore
	private List<LoanProfile> loanProfiles;
	// ------------------------------
	
	// Construtor -------------------
	public LoanInterestRate() {
	}

	public LoanInterestRate(double interestRate, int months) {
		this.interestRate = interestRate;
		this.months = months;
	}

	public LoanInterestRate(Long id, double interestRate, int months) {
		this.id = id;
		this.interestRate = interestRate;
		this.months = months;
	}
	// ------------------------------
	
	// GETTER/SETTER ----------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public List<LoanProfile> getLoanProfiles() {
		return loanProfiles;
	}

	public void setLoanProfiles(List<LoanProfile> loanProfiles) {
		this.loanProfiles = loanProfiles;
	}
	// ------------------------------
	
}
