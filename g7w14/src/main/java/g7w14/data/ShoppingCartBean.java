package g7w14.data;

import g7w14.business.BookManager;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class containing the fields used in the shopping cart when users review items
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@Named("shoppingCartBean")
@SessionScoped
public class ShoppingCartBean implements Serializable {

	@Inject
	BookManager bManager;
	private static final long serialVersionUID = 2765666708691702111L;

	private long bookId;
	private boolean inStock;
	private BigDecimal price;
	private long quantity;
	private boolean delete;

	public ShoppingCartBean() {
		super();
		bookId = 0;
		inStock = false;
		price = new BigDecimal(0.00);
		quantity = 0;
		delete = false;
	}

	public boolean isDeletable() {
		return delete;
	}

	public void setDeletable(boolean delete) {
		this.delete = delete;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ShoppingCartBean [bookId=" + bookId + ", inStock=" + inStock
				+ ", price=" + price + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bManager == null) ? 0 : bManager.hashCode());
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result + (delete ? 1231 : 1237);
		result = prime * result + (inStock ? 1231 : 1237);
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + (int) (quantity);
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
		ShoppingCartBean other = (ShoppingCartBean) obj;
		if (bManager == null) {
			if (other.bManager != null)
				return false;
		} else if (!bManager.equals(other.bManager))
			return false;
		if (bookId != other.bookId)
			return false;
		if (delete != other.delete)
			return false;
		if (inStock != other.inStock)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
