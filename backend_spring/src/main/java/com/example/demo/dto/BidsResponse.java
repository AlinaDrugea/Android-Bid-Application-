package com.example.demo.dto;

import java.math.BigDecimal;

public class BidsResponse {

	private String message;
	private BigDecimal currentbalance;
	private BigDecimal actualprice;
	private String status;
	
	public BidsResponse(){}
	
	public BidsResponse(String message,BigDecimal currentbalance, BigDecimal actualprice, String status) {
		this.message = message;
		this.actualprice = actualprice;
		this.currentbalance = currentbalance;
		this.status = status;
	}
	
	public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public BigDecimal getCurrentBalance() { return currentbalance; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentbalance = currentBalance; }

    public BigDecimal getActualPrice() { return actualprice; }
    public void setActualPrice(BigDecimal actualPrice) { this.actualprice = actualPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
