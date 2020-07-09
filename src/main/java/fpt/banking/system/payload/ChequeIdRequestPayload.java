package fpt.banking.system.payload;

public class ChequeIdRequestPayload {

	private long chequeId;

	public ChequeIdRequestPayload(long chequeId) {
		this.chequeId = chequeId;
	}

	public ChequeIdRequestPayload() {
	}

	public long getChequeId() {
		return chequeId;
	}

	public void setChequeId(long chequeId) {
		this.chequeId = chequeId;
	}
	
}
