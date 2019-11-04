package com.mps.message;

public class Response {
	private String responseStatus;
	private Object responseBody;
	private String responseMessage;

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Object getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "Response [responseStatus=" + responseStatus + ", responseBody=" + responseBody + ", responseMessage="
				+ responseMessage + "]";
	}

}
