/**
 * 
 */
package g7w14.business;

import g7w14.data.CalendarBean;
import g7w14.data.TotalSalesBean;
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
 * This action bean return a report with total sells
 * @author Svetlana Shopova
 * @since 04.03.2014
 * @version 2.0 - 13.03.2013
 *
 */

@Named("total")
@RequestScoped
public class TotalSalesManager implements Serializable {

	
	private static final long serialVersionUID = -9096015865734344304L;
	
	@Inject 
	private CalendarBean calendar;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	private boolean render;
	
	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	/**
	 * This method create array with three
	 * values of type double - net sales, taxes
	 * and gross total of all invoices
	 * @return array with three values
	 * @throws SQLException
	 */
	public ArrayList<TotalSalesBean> getTotalSales() throws SQLException
	{
		
		ArrayList<TotalSalesBean> totalSales = new ArrayList<TotalSalesBean>();
		String sql =  "select sum(Net_Value) as Total, sum(pst+gst+hst) "
				+ "as 'Taxes',sum(Total_Gross) as 'Total Gross' "
				+ "	from  invoice join orders using (OrderId) "
				+ "	where Order_Date between ? and ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);)
				{
					pStatement.setString(1, DateConverter.convertDate(calendar.getStartSelectedDate()));
					pStatement.setString(2, DateConverter.convertDate(calendar.getEndSelectedDate() ));
					
					try (ResultSet resultSet = pStatement.executeQuery())
					{
						while(resultSet.next())
						{
							TotalSalesBean tbean = new TotalSalesBean();
							tbean.setNetValue(resultSet.getDouble("Total"));
							tbean.setTaxes(resultSet.getDouble("Taxes"));
							tbean.setGrossTotal(resultSet.getDouble("Total Gross"));
							totalSales.add(tbean);
						}
						
					}
				}		
		
		return totalSales;
	}//end of getTotalSales()
}
