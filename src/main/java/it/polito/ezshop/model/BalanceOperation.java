package it.polito.ezshop.model;

import java.io.Serializable;
import java.time.LocalDate;

public class BalanceOperation implements it.polito.ezshop.data.BalanceOperation, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8157819062232469923L;
	int balanceId;
	LocalDate date;
	double money;
	String type;
	
	

	public BalanceOperation(int balanceId, LocalDate date, double money, String type) {
		super();
		this.balanceId = balanceId;
		this.date = date;
		this.money = money;
		this.type = type;
	}

	public int getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
