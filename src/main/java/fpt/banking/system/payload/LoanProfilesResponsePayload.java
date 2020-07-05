package fpt.banking.system.payload;

import java.util.List;

import fpt.banking.system.model.LoanProfile;

public class LoanProfilesResponsePayload {
	private long totalCount;
	private int totalPage;
	private int pageNumber;
	private int pageSize;
	private List<LoanProfile> items;
	public LoanProfilesResponsePayload(long totalCount, int totalPage, int pageNumber, int pageSize,
			List<LoanProfile> items) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.items = items;
	}
	public LoanProfilesResponsePayload() {
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<LoanProfile> getItems() {
		return items;
	}
	public void setItems(List<LoanProfile> items) {
		this.items = items;
	}
	
}
