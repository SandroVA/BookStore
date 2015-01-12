package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This bean contains data for 
 * total sales report
 * @author Svetlana Shopova	
 * @since 17.03.2014
 *
 */
@Named("totalSalesBean")
@SessionScoped
public class TotalSalesBean implements Serializable{

	
	private static final long serialVersionUID = -2932304969491963670L;

	private double netValue;
	private double taxes;
	private double grossTotal;
	
	
	public TotalSalesBean() {
		super();
		
		netValue=0.0;
		taxes = 0.0;
		grossTotal=0.0;
	}


	public double getNetValue() {
		return netValue;
	}


	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}


	public double getTaxes() {
		return taxes;
	}


	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}


	public double getGrossTotal() {
		return grossTotal;
	}


	public void setGrossTotal(double grossTotal) {
		this.grossTotal = grossTotal;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(grossTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(netValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(taxes);
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
		TotalSalesBean other = (TotalSalesBean) obj;
		if (Double.doubleToLongBits(grossTotal) != Double
				.doubleToLongBits(other.grossTotal))
			return false;
		if (Double.doubleToLongBits(netValue) != Double
				.doubleToLongBits(other.netValue))
			return false;
		if (Double.doubleToLongBits(taxes) != Double
				.doubleToLongBits(other.taxes))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TotalSalesBean [netValue=" + netValue + ", taxes=" + taxes
				+ ", grossTotal=" + grossTotal + "]";
	}
	
	
}
