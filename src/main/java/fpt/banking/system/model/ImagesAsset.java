package fpt.banking.system.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "images_asset")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImagesAsset {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "url")
	private String url;
	
	// Relationship ------------------------
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { 
					CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "asset_id")
	@JsonIgnore
	private Asset asset;
	// -------------------------------------
	
	// Constructor -------------------------
	public ImagesAsset() {
	}

	public ImagesAsset(String url) {
		this.url = url;
	}

	public ImagesAsset(Long id, String url) {
		this.id = id;
		this.url = url;
	}
	// -------------------------------------
	
	// GETTER/SETTER -----------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	// -------------------------------------
}
