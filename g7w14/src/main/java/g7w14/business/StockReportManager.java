package g7w14.business;

import g7w14.data.BookBean;

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
 * Action Bean that retrieves all books in the inventory
 * @author Ian Ozturk
 */
@Named("stockReportManager")
@RequestScoped
public class StockReportManager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	BookBean bb;
	
	/**
	 * Retrieves all books in the inventory
	 * @return rows Array list of books
	 * @throws SQLException
	 */
	public ArrayList<BookBean> getAll() throws SQLException{
		ArrayList<BookBean> rows = new ArrayList<BookBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn
						.prepareStatement("Select * from book");
				ResultSet resultSet = pStatement.executeQuery();) {
			while (resultSet.next()) {
				BookBean book = new BookBean();
				
				book.setBookId(resultSet.getLong("BookId"));
				book.setIsbn(resultSet.getString("ISBN"));
				book.setTitle(resultSet.getString("Title"));
				book.setNumberCopies(resultSet.getInt("Number_copies"));
				book.setWholesalePrice(resultSet.getBigDecimal("Wholesale_price"));
				book.setListPrice(resultSet.getBigDecimal("List_price"));

				rows.add(book);

			}

		}
		return rows;
		
	}

}
