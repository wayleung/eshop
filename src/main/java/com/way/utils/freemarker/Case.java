package com.way.utils.freemarker;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Way Liang ASNPHXW
 *
 * @date Jul 3, 2018
 *
 * @description:
 *
 */
public class Case {
	private String caseName = "";

	private String state = "";

	private String time = "";

	private String failures = "";

	private String success = "";

	private List<Detail> details = new ArrayList<Detail>();

	public Case() {
		super();
	}

	/**
	 * 不带List<Detail> detail的构造函数
	 * 
	 * @param caseName
	 * @param state
	 * @param time
	 * @param failures
	 * @param success
	 */
	public Case(String caseName, String state, String time, String failures, String success) {
		super();
		this.caseName = caseName;
		this.state = state;
		this.time = time;
		this.failures = failures;
		this.success = success;
	}

	/**
	 * 带List<Detail> detail的构造函数
	 * 
	 * @param caseName
	 * @param state
	 * @param time
	 * @param failures
	 * @param success
	 * @param details
	 */
	public Case(String caseName, String state, String time, String failures, String success, List<Detail> details) {
		super();
		this.caseName = caseName;
		this.state = state;
		this.time = time;
		this.failures = failures;
		this.success = success;
		this.details = details;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFailures() {
		return failures;
	}

	public void setFailures(String failures) {
		this.failures = failures;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Case [caseName=" + caseName + ", state=" + state + ", time=" + time + ", failures=" + failures
				+ ", success=" + success + ", details=" + details + "]";
	}

}
