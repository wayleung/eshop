package com.way.utils.freemarker;

/**
 * @author Way Liang ASNPHXW
 *
 * @date Jul 3, 2018
 *
 * @description:
 *
 */
public class Detail {

private String step="";
	
	private String description="";
	
	private String status="";
	
	private String message="";

	private String comment="";
	
	public Detail() {
	}


	public Detail(String step, String description, String status,
			String message, String comment) {
		this.step = step;
		this.description = description;
		this.status = status;
		this.message = message;
		this.comment = comment;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Detail [step=" + step + ", description=" + description
				+ ", status=" + status + ", message=" + message + ", comment="
				+ comment + "]";
	}

}
