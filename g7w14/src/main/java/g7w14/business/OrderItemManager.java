
package g7w14.business;

import g7w14.data.OrderBean;
import g7w14.data.OrderItemBean;
import g7w14.persistence.OrderItemDAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;

/**
 * This class manages OrderItem Bean
 * @author Svetlana Shopova
 * updated by Joseph He
 * @since 22.03.2014
 */
@Named("orderItemManager")
@RequestScoped
public class OrderItemManager implements Serializable{

	private static final long serialVersionUID = -2161640351747480976L;
	@Inject
	private OrderItemBean orderItemBean;
	@Inject
	private OrderBean orderBean;
	@Inject
	private OrderItemDAO odao;
	
	
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	private ArrayList<OrderItemBean> allItems;
	
	/**
	 * This method creates a list with 
	 * all items(only available) in a desired order
	 * @return list with items
	 */
	public ArrayList<OrderItemBean> getAllOrderItems()
	{
		String sql = "select BookId, book.Title, Quantity, Price, Available "
				+ "from book join orderitem using(BookId) "
				+ "	where OrderId=?";
		try {
			allItems = odao.getAllOrderItems(sql);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		
		return allItems;		
	}//end of getAllOrderItems()

	public ArrayList<OrderItemBean> getAllItems() {
		if(allItems==null)
		{
			allItems=getAllOrderItems();
		}
		return allItems;
	}

	public void setAllItems(ArrayList<OrderItemBean> allItems) {
		this.allItems = allItems;
	}
	
	/**
	 * This method update in an order
	 * @return redirect to adminEditOrder.xhtml
	 */
	public String updateAll()
	{
		String sql = "Update orderitem set Available =? "
				+ "where OrderId=? and bookId=?";

		

		for (OrderItemBean oib : allItems) {			

			try {
				odao.update(sql, oib.isAvailable(), oib.getOrderId(), oib.getBookId());
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
			
			
		}		
		return "adminEditOrder";
	}// end of updateAll()
	
}
