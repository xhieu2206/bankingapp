package fpt.banking.system.payload;

import java.util.List;

import fpt.banking.system.model.Transaction;

public class TransactionsResponse {

	private int totalCount;
	private int totalPage;
	private int pageNumber;
	private int pageSize;
	private List<Transaction> items;

	public TransactionsResponse() {
	}

	public TransactionsResponse(int totalCount, int totalPage, int pageNumber, int pageSize, List<Transaction> items) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.items = items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
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

	public List<Transaction> getItems() {
		return items;
	}

	public void setItems(List<Transaction> items) {
		this.items = items;
	}

	
}
