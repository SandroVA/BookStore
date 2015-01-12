/**
 * 
 */
package g7w14.business;

import g7w14.data.BookBean;
import g7w14.data.CalendarBean;
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
 * Action Bean that gets all books that haven't bean sold in specified time span
 * @author Ian Ozturk
 * 
 */

@Named("zeroSalesReportManager")
@RequestScoped
public class ZeroSalesReportManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7267072130639345778L;

	@Inject
	BookBean bb;
	@Inject
	CalendarBean calendar;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;

	private boolean render;
	
	/**
	 * Gets all books that haven't been sold in a certain time span
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BookBean> getZeroSales() throws SQLException {

		ArrayList<BookBean> rows = new ArrayList<BookBean>();
		String sql = "select isbn, title from book where bookid not in (select distinct orderitem.bookid "
				+ "from orderitem join orders on orders.OrderId = orderitem.OrderId where orders.Order_Date between DATE(?) and DATE(?))";

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setString(1,
					DateConverter.convertDate(calendar.getStartSelectedDate()));
			pStatement.setString(2,
					DateConverter.convertDate(calendar.getEndSelectedDate()));

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					BookBean book = new BookBean();

					book.setIsbn(resultSet.getString("ISBN"));
					book.setTitle(resultSet.getString("Title"));

					rows.add(book);
				}
			}
		}

		return rows;
	}

	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

}
