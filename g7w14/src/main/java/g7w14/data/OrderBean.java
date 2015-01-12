/**
 * 
 */
package g7w14.data;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Class describing the Orders table in the database
 * 
 * @author 1032763 Martin Nafekh, Svetlana Shopova
 * 
 */
@Named("orderBean")
@SessionScoped
public class OrderBean implements Serializable {

	private static final long serialVersionUID = -4644817968128554089L;

	private long orderId;
	private long customerId;
	private Timestamp orderDate;
	private Timestamp shipDate;
	private String title;
	private String sLastName; // s prefix indicates shipping information
	private String sFirstName;
	private String sCompany;
	private String sAddress1;
	private String sAddress2;
	private String sCity;
	private String sProvince;
	private String sCountry;
	private String sPostalCode;
	private boolean editable;

	public OrderBean() {
		super();
		orderId = -1;
		customerId = -1;
		orderDate = null;
		shipDate = null;
		sLastName = "";
		sFirstName = "";
		sCompany = "";
		sAddress1 = "";
		sAddress2 = "";
		sCity = "";
		sProvince = "";
		sCountry = "";
		sPostalCode = "";
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Timestamp getShipDate() {
		return shipDate;
	}

	public void setShipDate(Timestamp shipDate) {
		this.shipDate = shipDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String string) {
		this.title = string;
	}

	public String getsLastName() {
		return sLastName;
	}

	public void setsLastName(String sLastName) {
		this.sLastName = sLastName;
	}

	public String getsFirstName() {
		return sFirstName;
	}

	public void setsFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public String getsCompany() {
		return sCompany;
	}

	public void setsCompany(String sCompany) {
		this.sCompany = sCompany;
	}

	public String getsAddress1() {
		return sAddress1;
	}

	public void setsAddress1(String sAddress1) {
		this.sAddress1 = sAddress1;
	}

	public String getsAddress2() {
		return sAddress2;
	}

	public void setsAddress2(String sAddress2) {
		this.sAddress2 = sAddress2;
	}

	public String getsCity() {
		return sCity;
	}

	public void setsCity(String sCity) {
		this.sCity = sCity;
	}

	public String getsProvince() {
		return sProvince;
	}

	public void setsProvince(String sProvince) {
		this.sProvince = sProvince;
	}

	public String getsCountry() {
		return sCountry;
	}

	public void setsCountry(String sCountry) {
		this.sCountry = sCountry;
	}

	public String getsPostalCode() {
		return sPostalCode;
	}

	public void setsPostalCode(String sPostalCode) {
		this.sPostalCode = sPostalCode;
	}

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @author Svetlana Shopova
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result
				+ ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result
				+ ((sAddress1 == null) ? 0 : sAddress1.hashCode());
		result = prime * result
				+ ((sAddress2 == null) ? 0 : sAddress2.hashCode());
		result = prime * result + ((sCity == null) ? 0 : sCity.hashCode());
		result = prime * result
				+ ((sCompany == null) ? 0 : sCompany.hashCode());
		result = prime * result
				+ ((sCountry == null) ? 0 : sCountry.hashCode());
		result = prime * result
				+ ((sFirstName == null) ? 0 : sFirstName.hashCode());
		result = prime * result
				+ ((sLastName == null) ? 0 : sLastName.hashCode());
		result = prime * result
				+ ((sPostalCode == null) ? 0 : sPostalCode.hashCode());
		result = prime * result
				+ ((sProvince == null) ? 0 : sProvince.hashCode());
		result = prime * result
				+ ((shipDate == null) ? 0 : shipDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		OrderBean other = (OrderBean) obj;
		if (customerId != other.customerId)
			return false;
		if (editable != other.editable)
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderId != other.orderId)
			return false;
		if (sAddress1 == null) {
			if (other.sAddress1 != null)
				return false;
		} else if (!sAddress1.equals(other.sAddress1))
			return false;
		if (sAddress2 == null) {
			if (other.sAddress2 != null)
				return false;
		} else if (!sAddress2.equals(other.sAddress2))
			return false;
		if (sCity == null) {
			if (other.sCity != null)
				return false;
		} else if (!sCity.equals(other.sCity))
			return false;
		if (sCompany == null) {
			if (other.sCompany != null)
				return false;
		} else if (!sCompany.equals(other.sCompany))
			return false;
		if (sCountry == null) {
			if (other.sCountry != null)
				return false;
		} else if (!sCountry.equals(other.sCountry))
			return false;
		if (sFirstName == null) {
			if (other.sFirstName != null)
				return false;
		} else if (!sFirstName.equals(other.sFirstName))
			return false;
		if (sLastName == null) {
			if (other.sLastName != null)
				return false;
		} else if (!sLastName.equals(other.sLastName))
			return false;
		if (sPostalCode == null) {
			if (other.sPostalCode != null)
				return false;
		} else if (!sPostalCode.equals(other.sPostalCode))
			return false;
		if (sProvince == null) {
			if (other.sProvince != null)
				return false;
		} else if (!sProvince.equals(other.sProvince))
			return false;
		if (shipDate == null) {
			if (other.shipDate != null)
				return false;
		} else if (!shipDate.equals(other.shipDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderBean [orderId=" + orderId + ", customerId=" + customerId
				+ ", orderDate=" + orderDate + ", shipDate=" + shipDate
				+ ", title=" + title + ", sLastName=" + sLastName
				+ ", sFirstName=" + sFirstName + ", sCompany=" + sCompany
				+ ", sAddress1=" + sAddress1 + ", sAddress2=" + sAddress2
				+ ", sCity=" + sCity + ", sProvince=" + sProvince
				+ ", sCountry=" + sCountry + ", sPostalCode=" + sPostalCode
				+ ", editable=" + editable + "]";
	}

}
