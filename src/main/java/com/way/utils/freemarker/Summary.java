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
public class Summary {

	// the name of Summary
	// private String summaryName;

	private String startDate = "";

	private String endDate = "";

	// Time Spent(s)
	private String timeSpent = "";

	private String passed = "";

	private String failed = "";

	private String autoTestCase = "";

	private String totalTestCase = "";

	private String passingRate = "";

	private String coverageRate = "";

	private List<Case> cases = new ArrayList<Case>();

	public Summary() {
		super();
	}

	/**
	 * 不带List<Case> cases的构造函数
	 * 
	 * @param startDate
	 * @param endDate
	 * @param timeSpent
	 * @param passed
	 * @param failed
	 * @param autoTestCase
	 * @param totalTestCase
	 * @param passingRate
	 * @param coverageRate
	 */
	public Summary(String startDate, String endDate, String timeSpent, String passed, String failed,
			String autoTestCase, String totalTestCase, String passingRate, String coverageRate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.timeSpent = timeSpent;
		this.passed = passed;
		this.failed = failed;
		this.autoTestCase = autoTestCase;
		this.totalTestCase = totalTestCase;
		this.passingRate = passingRate;
		this.coverageRate = coverageRate;
	}

	/**
	 * 带List<Case> cases的构造函数
	 * 
	 * @param startDate
	 * @param endDate
	 * @param timeSpent
	 * @param passed
	 * @param failed
	 * @param autoTestCase
	 * @param totalTestCase
	 * @param passingRate
	 * @param coverageRate
	 * @param cases
	 */
	public Summary(String startDate, String endDate, String timeSpent, String passed, String failed,
			String autoTestCase, String totalTestCase, String passingRate, String coverageRate, List<Case> cases) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.timeSpent = timeSpent;
		this.passed = passed;
		this.failed = failed;
		this.autoTestCase = autoTestCase;
		this.totalTestCase = totalTestCase;
		this.passingRate = passingRate;
		this.coverageRate = coverageRate;
		this.cases = cases;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

	public String getAutoTestCase() {
		return autoTestCase;
	}

	public void setAutoTestCase(String autoTestCase) {
		this.autoTestCase = autoTestCase;
	}

	public String getTotalTestCase() {
		return totalTestCase;
	}

	public void setTotalTestCase(String totalTestCase) {
		this.totalTestCase = totalTestCase;
	}

	public String getPassingRate() {
		return passingRate;
	}

	public void setPassingRate(String passingRate) {
		this.passingRate = passingRate;
	}

	public String getCoverageRate() {
		return coverageRate;
	}

	public void setCoverageRate(String coverageRate) {
		this.coverageRate = coverageRate;
	}

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}

	@Override
	public String toString() {
		return "Summary [startDate=" + startDate + ", endDate=" + endDate + ", timeSpent=" + timeSpent + ", passed="
				+ passed + ", failed=" + failed + ", autoTestCase=" + autoTestCase + ", totalTestCase=" + totalTestCase
				+ ", passingRate=" + passingRate + ", coverageRate=" + coverageRate + ", cases=" + cases + "]";
	}

}
