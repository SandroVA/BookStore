package g7w14.business;

import g7w14.data.CalendarBean;
import g7w14.data.TopSellersBean;
import g7w14.util.DateConverter;

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
 * This class is responsible for creating 
 * report for adminTopSellers for the books with 
 * most sales
 * @author Svetlana Shopova
 * @since 17.03.2014
 *
 */
@Named("topSellerManager")
@RequestScoped
public class TopSellerManager implements Serializable{

	private static final long serialVersionUID = -4000248063295039848L;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	private CalendarBean calendar;	
	
	private boolean render;
	
	
	public TopSellerManager() {
		super();
		render=false;
	}

	/**
	 * This method return a list with 
	 * book that have been sold and total sales for each book
	 * @return list with books and their total sales
	 * @throws SQLException
	 */
	public ArrayList<TopSellersBean> getTopSellers() throws SQLException
	{
		String sql = "Select distinct  book.ISBN, book.Title,"
				+ " (select  sum(quantity*price) from orderItem where book.BookId=orderItem.BookId) as Sold "
				+ "from book join orderItem using(BookId) "
				+ "join orders using (OrderId) "
				+ "where book.BookId  in (select BookId from orderItem) "
				+ "and Order_Date between ? and ? "
				+ "order by Sold desc";
		ArrayList<TopSellersBean> topSellers = new ArrayList<TopSellersBean>();
		
		if(ds==null)
		{
			throw new SQLException("Cannot get data source");
		}
		
		try(Connection conn= ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);
				)
				{
					pStatement.setString(1, DateConverter.convertDate(calendar.getStartSelectedDate()));
					pStatement.setString(2, DateConverter.convertDate(calendar.getEndSelectedDate()));
					
					try(ResultSet rSet = pStatement.executeQuery();)
					{
						while(rSet.next())
						{
							TopSellersBean bean = new TopSellersBean();
							bean.setIsbn(rSet.getString("ISBN"));
							bean.setTitle(rSet.getString("Title"));
							bean.setSold(rSet.getDouble("Sold"));
							topSellers.add(bean);
						}
					}
				}
		
		return topSellers;
	}//end of getTopSellers()
	
	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

}//end of TopSellerManager
