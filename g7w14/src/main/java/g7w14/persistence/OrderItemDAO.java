
package g7w14.persistence;

import g7w14.data.OrderBean;
import g7w14.data.OrderItemBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * This class manages the connection between 
 * database and  OrderItemManager class
 * @author Svetlana Shopova
 * @since 22.03.2014
 */
@Named
@RequestScoped
public class OrderItemDAO implements Serializable{

	
	private static final long serialVersionUID = 7999657911888904298L;
	
	@Resource(name="jdbc/g7w14")
	DataSource ds;
	@Inject 
	private OrderBean orderBean;
	
	/**
	 * This method return all available items in 
	 * one order
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<OrderItemBean> getAllOrderItems(String sql ) throws SQLException
	{
		
		if(ds==null)
		{
			throw new SQLException("Cannot get the data source"); 
		}
		
		ArrayList<OrderItemBean> allItems = new ArrayList<OrderItemBean>();
		
		try(Connection conn=ds.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql);)
			{
				pStatement.setLong(1, orderBean.getOrderId());
				
				try(ResultSet rs=pStatement.executeQuery())
				{
					while(rs.next())
					{
						OrderItemBean oib = new OrderItemBean();
						
						oib.setBookId(rs.getLong("BookId"));
						oib.setBookTitle(rs.getString("Title"));
						oib.setOrderId(orderBean.getOrderId());
						oib.setAvailable(rs.getBoolean("Available"));
						oib.setPrice(rs.getDouble("Price"));
						oib.setQuantity(rs.getInt("Quantity"));
						allItems.add(oib);
					}
				}
			
			}
		
		return allItems;
	}//end of getAllOrderItems()
	
	/**
	 * This method update an item  in an order
	 * @param sql	
	 * @param available
	 * @param orderId
	 * @param bookId
	 * @throws SQLException 
	 */
	public void update(String sql,boolean available, long orderId, long bookId) throws SQLException
	{
		if(ds==null)
		{
			throw new SQLException("Cannot get the data source"); 
		}
		
		try(Connection conn=ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);)
				{
					
					pStatement.setBoolean(1, available);
					pStatement.setLong(2, orderId);
					pStatement.setLong(3, bookId);
					
					pStatement.executeUpdate();
				}
	}//end of update()
}
