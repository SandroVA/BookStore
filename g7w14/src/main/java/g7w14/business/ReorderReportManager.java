package g7w14.business;

import g7w14.data.ReorderReportBean;

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
 * Action Bean that retrieves all books where the number of copies is below 5
 * 
 * @author Ian Ozturk
 */

@Named("reorderReportManager")
@RequestScoped
public class ReorderReportManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	ReorderReportBean rrb;

	/**
	 * Retrieves all books from the book table where the number of copies is less then 5
	 * @return rows Array list of ReorderReportBean's
	 * @throws SQLException
	 */
	public ArrayList<ReorderReportBean> getAll() throws SQLException {
		ArrayList<ReorderReportBean> rows = new ArrayList<ReorderReportBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn
						.prepareStatement("Select book.Isbn, book.Title, book.Number_copies, author.Last_Name, author.First_Name,"
								+ " publisher.Publisher_Name from author join bookauthor on bookauthor.authorId = author.authorId "
								+ "join book on book.BookId = bookauthor.BookId join publisher on book.PublisherId = publisher.PublisherId"
								+ " where book.Number_copies < 5");
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				ReorderReportBean report = new ReorderReportBean();
				
				report.setIsbn(resultSet.getString("ISBN"));
				report.setTitle(resultSet.getString("Title"));
				report.setAuthorLast(resultSet.getString("Last_Name"));
				report.setAuthorFirst(resultSet.getString("First_Name"));
				report.setPublisher(resultSet.getString("Publisher_Name"));
				report.setNumberCopies(resultSet.getInt("Number_copies"));
				
				rows.add(report);

			}

		}
		return rows;

	}

}
