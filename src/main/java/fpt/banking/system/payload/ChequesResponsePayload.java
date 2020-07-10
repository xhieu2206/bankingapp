package fpt.banking.system.payload;

import java.util.List;

import fpt.banking.system.model.Cheque;

public class ChequesResponsePayload {
	private long totalCount;
	private long totalPage;
	private long pageNumber;
	private long pageSize;
	private List<ChequeForAdmin> items;
	public ChequesResponsePayload(long totalCount, long totalPage, long pageNumber, long pageSize,
			List<ChequeForAdmin> items) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.items = items;
	}
	public ChequesResponsePayload() {
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public long getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public List<ChequeForAdmin> getItems() {
		return items;
	}
	public void setItems(List<ChequeForAdmin> items) {
		this.items = items;
	}
}
