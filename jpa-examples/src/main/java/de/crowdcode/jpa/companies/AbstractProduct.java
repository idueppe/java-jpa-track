package de.crowdcode.jpa.companies;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import de.crowdcode.jpa.common.AbstractEntity;

@MappedSuperclass
public abstract class AbstractProduct extends AbstractEntity {

	@Column(name="product_no")
	private Long productNo = System.currentTimeMillis();

	public Long getProductNo() {
		return productNo;
	}

	public void setProductNo(Long productNo) {
		this.productNo = productNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productNo == null) ? 0 : productNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractProduct other = (AbstractProduct) obj;
		if (productNo == null) {
			if (other.productNo != null)
				return false;
		} else if (!productNo.equals(other.productNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractProduct [productNo=" + productNo + "]";
	}
	
}
