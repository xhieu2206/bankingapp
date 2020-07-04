package fpt.banking.system.model;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asset")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Asset {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description", length = 500, nullable = true)
	private String description;
	
	@Column(name = "price")
	private long price;
	
	// Relationship ------------------------
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { 
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "loan_profile_id")
	@JsonIgnore
	private LoanProfile loanProfile;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "asset",
			cascade = {
				CascadeType.ALL
			})
	private List<ImagesAsset> imagesAssets;
	// -------------------------------------
	
	// Constructor -------------------------
	public Asset() {
	}

	public Asset(String name, String description, long price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Asset(Long id, String name, String description, long price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	// -------------------------------------
	
	// GETTER/SETTER -----------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public LoanProfile getLoanProfile() {
		return loanProfile;
	}

	public void setLoanProfile(LoanProfile loanProfile) {
		this.loanProfile = loanProfile;
	}

	public List<ImagesAsset> getImagesAssets() {
		return imagesAssets;
	}

	public void setImagesAssets(List<ImagesAsset> imagesAssets) {
		this.imagesAssets = imagesAssets;
	}
	// -------------------------------------
}
