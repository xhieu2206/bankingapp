package fpt.banking.system.payload;

public class AssetIdRequestPayload {

	private long assetId;

	public AssetIdRequestPayload() {
	}

	public AssetIdRequestPayload(long assetId) {
		this.assetId = assetId;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

}
