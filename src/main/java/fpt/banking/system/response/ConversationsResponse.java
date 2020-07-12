package fpt.banking.system.response;

import java.util.List;

public class ConversationsResponse {
	private long totalCount;
	private long totalPage;
	private long pageNumber;
	private long pageSize;
	private List<ConversationForEmployee> items;
	public ConversationsResponse(long totalCount, long totalPage, long pageNumber, long pageSize,
			List<ConversationForEmployee> items) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.items = items;
	}
	public ConversationsResponse() {
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
	public List<ConversationForEmployee> getItems() {
		return items;
	}
	public void setItems(List<ConversationForEmployee> items) {
		this.items = items;
	}
}
