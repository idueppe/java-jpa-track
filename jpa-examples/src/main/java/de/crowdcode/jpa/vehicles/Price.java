package de.crowdcode.jpa.vehicles;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlType()
@XmlAccessorType(XmlAccessType.FIELD)
public class Price extends AbstractEntity {
	
	private double value;
	
	@Column(length=5)
	private String currency;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;
	
	public Price() {
	}

	public Price(double value, String currency, Date validFrom) {
		this.value = value;
		this.currency = currency;
		this.validFrom = validFrom;
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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date fromDate) {
		this.validFrom = fromDate;
	}


}
