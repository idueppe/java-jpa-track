package de.crowdcode.jpa.vehicles;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Price extends AbstractEntity {
	
	private double value;
	
	@Column(length=5)
	private String currency;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date toDate;
	
	public Price() {
	}

	public Price(double value, String currency, Date fromDate, Date toDate) {
		super();
		this.value = value;
		this.currency = currency;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	

}
