package fpt.banking.system.payload;

import java.util.List;

import fpt.banking.system.model.Transaction;
import fpt.banking.system.model.User;

public class UsersResponse {
	private long totalCount;
	private int totalPage;
	private int pageNumber;
	private int pageSize;
	private List<User> items;
	public UsersResponse() {
	}
	public UsersResponse(int totalCount, int totalPage, int pageNumber, int pageSize, List<User> items) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.items = items;
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
	public List<User> getItems() {
		return items;
	}
	public void setItems(List<User> items) {
		this.items = items;
	}
}
