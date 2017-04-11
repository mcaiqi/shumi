package me.jamc.skeleton.controller.response;

/**
 * Created by Jamc on 11/7/16.
 */
public class ErrorResponse extends ReturnResponse {
    private boolean success;
    private String errorCode;
	private String errorMsg;

	
    
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
