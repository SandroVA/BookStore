package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author Svetlana Shopova
 * @since 19.03.2014
 */
@Named("orderItemBean")
@SessionScoped
public class OrderItemBean implements Serializable{
	
	private static final long serialVersionUID = -5217954244054337904L;
	
	private long orderId;
	private long bookId;
	private int quantity;
	private double price;
	private String bookTitle;
	private boolean available;
	private boolean editable;

	public OrderItemBean() {
		super();
		orderId=0l;
		bookId=0l;
		quantity = 0;
		price = 0.0;
		bookTitle="";
		available=true;		
		
	}


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	public long getBookId() {
		return bookId;
	}


	public void setBookId(long bookId) {
		this.bookId = bookId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}


	
	public boolean isAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean isEditable() {
		return editable;
	}


	public void setEditable(boolean editable) {
		this.editable = editable;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result
				+ ((bookTitle == null) ? 0 : bookTitle.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = (int) (prime * result + price);
		result = prime * result + quantity;
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
		OrderItemBean other = (OrderItemBean) obj;
		if (available != other.available)
			return false;
		if (bookId != other.bookId)
			return false;
		if (bookTitle == null) {
			if (other.bookTitle != null)
				return false;
		} else if (!bookTitle.equals(other.bookTitle))
			return false;
		if (orderId != other.orderId)
			return false;
		if (price != other.price)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "OrderItemBean [orderId=" + orderId + ", bookId=" + bookId
				+ ", quantity=" + quantity + ", price=" + price
				+ ", bookTitle=" + bookTitle + ", available=" + available + "]";
	}


	
	

}
