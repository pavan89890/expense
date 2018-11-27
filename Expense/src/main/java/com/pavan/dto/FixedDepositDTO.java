package com.pavan.dto;

import java.util.List;

import com.pavan.model.FixedDepositBO;

public class FixedDepositDTO {
	private List<FixedDepositBO> fds;

	private int fdsCount;
	private Float totDepAmount;
	private Float totMatAmount;

	public List<FixedDepositBO> getFds() {
		return fds;
	}

	public void setFds(List<FixedDepositBO> fds) {
		this.fds = fds;
	}

	public int getFdsCount() {
		return fdsCount;
	}

	public void setFdsCount(int fdsCount) {
		this.fdsCount = fdsCount;
	}

	public Float getTotDepAmount() {
		return totDepAmount;
	}

	public void setTotDepAmount(Float totDepAmount) {
		this.totDepAmount = totDepAmount;
	}

	public Float getTotMatAmount() {
		return totMatAmount;
	}

	public void setTotMatAmount(Float totMatAmount) {
		this.totMatAmount = totMatAmount;
	}
}