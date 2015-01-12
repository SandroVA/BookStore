package g7w14.business;

import g7w14.data.BookBean;
import g7w14.data.CustomerBean;
import g7w14.data.ShoppingCartBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Action bean that contains the business logic of the shopping cart
 * 
 * @author Ian Ozturk
 * 
 */

@Named("shoppingCartManager")
@SessionScoped
public class ShoppingCartManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	BookManager bookmanager;
	@Inject
	CustomerBean cb;
	@Inject
	ShoppingCartBean shoppingBean;
	@Inject
	CustomerManager cManager;
	
	private ArrayList<ShoppingCartBean> cart = new ArrayList<ShoppingCartBean>();
	
	/**
	 * Adds an item to the shopping cart
	 * @author Ian Ozturk
	 * @param id
	 */
	public void addItem(int id) {
		
		boolean match = false;
		ShoppingCartBean item = new ShoppingCartBean();
		
		if (id > 0) {
			
			for (ShoppingCartBean bean : cart) {
				//if the the book is already in the shopping cart, simply increment the 
				//quantity by 1
				if (bean.getBookId() == id) {
					bean.setQuantity(bean.getQuantity() + 1);
					match = true;
					break;
				}
			}
			
			//If book is not in the shopping cart, add it to the cart
			if (!match) {
				ArrayList<BookBean> bookToAdd = bookmanager.searchBookById(id);
				
				if (bookToAdd.get(0).getNumberCopies() > 0)
					item.setInStock(true);

				item.setBookId(bookToAdd.get(0).getBookId());

				if (bookToAdd.get(0).getSalePrice() != null
						&& bookToAdd.get(0).getSalePrice().signum() > 0) {
					item.setPrice(bookToAdd.get(0).getSalePrice());
				} else {
					item.setPrice(bookToAdd.get(0).getListPrice());
				}

				item.setQuantity(1);

				cart.add(item);
			}
		}
	}
	
	/**
	 * Removes all items in the shopping cart
	 * @author Sandro, Svetlana, Ian, Joseph
	 */
	public void resetShoppingCart() {
		cart = new ArrayList<ShoppingCartBean>();
	}
	
	/**
	 * Removes selected items from the shopping cart
	 * @author Sandro Victoria Arena and Ian Ozturk
	 */
	public void removeItems() {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).isDeletable()==true) {
				cart.remove(cart.get(i));
				i--;
			}
		}
	}
	
	/**
	 * Updates the quantity of a book in the shopping cart
	 * @author Sandro Victoria Arena and Ian Ozturk
	 */
	public void updateQuantity() {

	}
	
	/**
	 * Get the book that was added last in the shopping cart
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return shoppingCart Array list of of ShoppingCartbean's
	 */
	public ArrayList<ShoppingCartBean> getLatestCart() {

		ArrayList<ShoppingCartBean> shoppingCart = new ArrayList<ShoppingCartBean>();
		ArrayList<ShoppingCartBean> cartClone = new ArrayList<ShoppingCartBean>(cart);

		long bookId = -1;
		long amountOfBooks = 0;
		BigDecimal price = new BigDecimal(0.00);
		
		if (!cartClone.isEmpty()) {
			bookId = cartClone.get(cartClone.size()-1).getBookId();
	
			for (ShoppingCartBean bean:cartClone) {
				amountOfBooks = amountOfBooks + bean.getQuantity();
				price = price.add(bean.getPrice().multiply(new BigDecimal(bean.getQuantity())));
			}
		}
		ShoppingCartBean bean = new ShoppingCartBean();
		bean.setBookId(bookId);
		bean.setPrice(price);
		bean.setQuantity(amountOfBooks);

		shoppingCart.add(bean);
		
		return shoppingCart;
	}
	
	/**
	 * Returns the cart
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return cart The shopping cart
	 */
	public ArrayList<ShoppingCartBean> getCart() {
		return cart;
	}

	/**
	 * Calculates the total cost of the items in a customers shopping cart
	 * @author Ian Ozturk
	 * @param cart
	 * @return total The total cost of a customers
	 */
	public BigDecimal getTotalCost() {
		cManager.getMyInfo();
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);

		// calculates the price of all items in the shopping cart before taxes
		for (ShoppingCartBean bean : cart) {

			subtotal = subtotal.add(bean.getPrice().multiply(
					new BigDecimal(bean.getQuantity())));
		}

		// Adds taxes depending on the province the customer is from
		switch (cb.getProvince()) {
		case "Alberta":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			break;
		case "British Columbia":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			// add 7% of the subtotal to total
			total = total.add(subtotal.multiply(new BigDecimal(0.07)));
			break;
		case "Manitoba":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			// add 8% of the subtotal to total
			total = total.add(subtotal.multiply(new BigDecimal(0.08)));
			break;
		case "New Brunswick":
			// add the subtotal to total + 13%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.13))));
			break;
		case "Newfoundland and Labrador":
			// add the subtotal to total + 13%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.13))));
			break;
		case "Northwest Territories":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			break;
		case "Nova Scotia":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.15))));
			break;
		case "Nunavut":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			break;
		case "Ontario":
			// add the subtotal to total + 13%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.13))));
			break;
		case "Prince Edward Island":
			// add the subtotal to total + 14%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.14))));
			break;
		case "Quebec":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			// add 5% of the subtotal to total
			total = total.add(subtotal.multiply(new BigDecimal(0.05)));
			break;
		case "Saskatchewan":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			// add 5% of the subtotal to total
			total = total.add(subtotal.multiply(new BigDecimal(0.05)));
			break;
		case "Yukon":
			// add the subtotal to total + 5%
			total = total.add(subtotal.add(subtotal.multiply(new BigDecimal(
					0.05))));
			break;
		default:
			
			total = total.add(subtotal);
			break;
		}
		
		total = total.setScale(2,BigDecimal.ROUND_UP);
		
		return total;

	}

}
