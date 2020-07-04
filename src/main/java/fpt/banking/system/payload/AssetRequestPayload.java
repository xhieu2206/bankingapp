package fpt.banking.system.payload;

public class AssetRequestPayload {

	private String name;
	private String description;
	private long price;
	private String[] images;
	public AssetRequestPayload() {
	}
	public AssetRequestPayload(String name, String description, long price, String[] images) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.images = images;
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
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	
}
