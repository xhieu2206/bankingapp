package fpt.banking.system.payload;

import javax.validation.constraints.*;

public class FindUserForTranferPayload {

	private String term;
	private String type;
	
	public FindUserForTranferPayload(String term, String type) {
		this.term = term;
		this.type = type;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}
