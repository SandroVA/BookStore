package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This class contain data for the bestsellers
 * @author Svetlana Shopova	
 * @since - 17.03.2014
 */
@Named("topSellerBean")
@SessionScoped
public class TopSellersBean implements Serializable {

	private static final long serialVersionUID = -96065862695079712L;
	private String isbn;
	private String title;
	
	private double sold;
	
	
	public TopSellersBean() {
		super();
		
		isbn="";
		title="";
		
		sold=0.00;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	

	


	public double getSold() {
		return sold;
	}


	public void setSold(double sold) {
		this.sold = sold;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		
		long temp;
		temp = Double.doubleToLongBits(sold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		TopSellersBean other = (TopSellersBean) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		
		if (Double.doubleToLongBits(sold) != Double
				.doubleToLongBits(other.sold))
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
		return "TopSellersBean [isbn=" + isbn + ", title=" + title
				+  ", sold=" + sold + "]";
	}
	
	

}
