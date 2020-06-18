package fpt.banking.system.payload;

public class ApiResponse<TData> {
    private Boolean success;
    private String message;
    private TData data;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public TData getData() {
		return data;
	}

	public void setData(TData data) {
		this.data = data;
	}
    
}

