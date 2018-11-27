package com.pavan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "t_fixed_deposit")
public class FixedDepositBO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "BANK")
	private String bank;

	@Column(name = "DEP_AMOUNT")
	private Float depAmount;

	@Column(name = "MAT_AMOUNT")
	private Float matAmount;

	@Column(name = "ROI")
	private Float roi;

	@Column(name = "DEPOSITED_ON")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date depositedOn;

	@Column(name = "PERIOD")
	private Integer period;

	@Column(name = "MATURED_ON")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date maturedOn;

	@Column(name = "STATUS")
	private String status;
	
	private String remaining;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Float getDepAmount() {
		return depAmount;
	}

	public void setDepAmount(Float depAmount) {
		this.depAmount = depAmount;
	}

	public Float getMatAmount() {
		return matAmount;
	}

	public void setMatAmount(Float matAmount) {
		this.matAmount = matAmount;
	}

	public Float getRoi() {
		return roi;
	}

	public void setRoi(Float roi) {
		this.roi = roi;
	}

	public Date getDepositedOn() {
		return depositedOn;
	}

	public void setDepositedOn(Date depositedOn) {
		this.depositedOn = depositedOn;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Date getMaturedOn() {
		return maturedOn;
	}

	public void setMaturedOn(Date maturedOn) {
		this.maturedOn = maturedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
}