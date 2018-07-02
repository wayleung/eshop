package com.way.vo;

import java.util.Collections;
import java.util.List;

public class PageResult<T> {

/*	public static final PageResult<?> EMPTY_PAGE = new PageResult(
			Collections.EMPTY_LIST, 0, 1, 1);*/

	private List<T> data;

	private int pageSize;
	private int currentPage;
	private int totalCount;

	// private int prevPage;
	// private int nextPage;
	private int totalPage;

	public PageResult(List<T> data, int totalCount, int pageSize,
			int currentPage) {

		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.data = data;

		this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
		// this.prevPage = currentPage - 1 < 1 ? 1 : currentPage - 1;
		// this.nextPage = currentPage + 1 > endPage ? endPage : currentPage -
		// 1;

	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	// public int getPrevPage() {
	// return prevPage;
	// }
	//
	// public void setPrevPage(int prevPage) {
	// this.prevPage = prevPage;
	// }
	//
	// public int getNextPage() {
	// return nextPage;
	// }
	//
	// public void setNextPage(int nextPage) {
	// this.nextPage = nextPage;
	// }
	//
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
