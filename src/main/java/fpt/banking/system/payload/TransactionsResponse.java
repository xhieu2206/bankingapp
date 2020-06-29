package fpt.banking.system.payload;

import java.util.List;

import fpt.banking.system.model.Transaction;

public class TransactionsResponse {

	private int totalTransaction;
	private int totalPage;
	private int currentPage;
	private int itemsInPage;
	private List<Transaction> transactions;

	public TransactionsResponse() {
	}

	public TransactionsResponse(int totalTransaction, int totalPage, int currentPage, List<Transaction> transactions, int itemsInPage) {
		this.totalTransaction = totalTransaction;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.transactions = transactions;
		this.itemsInPage = itemsInPage;
	}

	public int getTotalTransaction() {
		return totalTransaction;
	}

	public void setTotalTransaction(int totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public int getItemsInPage() {
		return itemsInPage;
	}

	public void setItemsInPage(int itemsInPage) {
		this.itemsInPage = itemsInPage;
	}
}
