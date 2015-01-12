package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Class describing the Invoice table in the database
 * 
 * @author 1032763 Martin Nafekh
 * 
 */
@Named("invoiceBean")
@SessionScoped
public class InvoiceBean implements Serializable {

	private static final long serialVersionUID = -3972772387974930077L;

	private int invoiceId;
	private int quantity;
	private int orderId;
	private double netValue;
	private double pst;
	private double gst;
	private double hst;
	private double totalGross;

	public InvoiceBean() {
		super();
	}

	// public InvoiceBean() {
	// super();
	// invoiceId = -1;
	// quantity = 0;
	// orderId = -1;
	// netValue = 0;
	// pst = 0;
	// gst = 0;
	// hst = 0;
	// totalGross = 0;
	// }
	//
	// public InvoiceBean(int invoiceId, int quantity, int orderId,
	// double netValue, double pst, double gst, double hst,
	// double totalGross) {
	// super();
	// this.invoiceId = invoiceId;
	// this.quantity = quantity;
	// this.orderId = orderId;
	// this.netValue = netValue;
	// this.pst = pst;
	// this.gst = gst;
	// this.hst = hst;
	// this.totalGross = totalGross;
	// }

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getNetValue() {
		return netValue;
	}

	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}

	public double getPst() {
		return pst;
	}

	public void setPst(double pst) {
		this.pst = pst;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getHst() {
		return hst;
	}

	public void setHst(double hst) {
		this.hst = hst;
	}

	public double getTotalGross() {
		return totalGross;
	}

	public void setTotalGross(double totalGross) {
		this.totalGross = totalGross;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "InvoiceBean [invoiceId=" + invoiceId + ", quantity=" + quantity
				+ ", orderId=" + orderId + ", netValue=" + netValue + ", pst="
				+ pst + ", gst=" + gst + ", hst=" + hst + ", totalGross="
				+ totalGross + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(gst);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hst);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + invoiceId;
		temp = Double.doubleToLongBits(netValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + orderId;
		temp = Double.doubleToLongBits(pst);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		temp = Double.doubleToLongBits(totalGross);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		InvoiceBean other = (InvoiceBean) obj;
		if (Double.doubleToLongBits(gst) != Double.doubleToLongBits(other.gst))
			return false;
		if (Double.doubleToLongBits(hst) != Double.doubleToLongBits(other.hst))
			return false;
		if (invoiceId != other.invoiceId)
			return false;
		if (Double.doubleToLongBits(netValue) != Double
				.doubleToLongBits(other.netValue))
			return false;
		if (orderId != other.orderId)
			return false;
		if (Double.doubleToLongBits(pst) != Double.doubleToLongBits(other.pst))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(totalGross) != Double
				.doubleToLongBits(other.totalGross))
			return false;
		return true;
	}

}
