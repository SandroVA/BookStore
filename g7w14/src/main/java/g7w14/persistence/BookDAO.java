package g7w14.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import g7w14.data.*;
import g7w14.interfaces.BookDAOInterface;

/**
 * 
 * This class makes a connection to the database to either Search, delete,
 * insert of modify records in the book table
 * 
 * @author Ian Ozturk and Sandro Victoria Arena
 * 
 */
@Named
@RequestScoped
public class BookDAO implements Serializable, BookDAOInterface {

	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	BookBean bb;

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.BookDAOInterface#retrieveRecords()
	 */
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 */
	@Override
	public ArrayList<BookBean> retrieveRecords() throws SQLException {
		ArrayList<BookBean> rows = new ArrayList<BookBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn
						.prepareStatement("Select * from book");
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				BookBean book = new BookBean();
				
				book.setBookId(resultSet.getLong("BookId"));
				book.setIsbn(resultSet.getString("ISBN"));
				book.setFormatId(resultSet.getLong("FormatId"));
				book.setTitle(resultSet.getString("Title"));
				book.setPublisherId(resultSet.getLong("PublisherId"));
				book.setNumberCopies(resultSet.getInt("Number_copies"));
				book.setWholesalePrice(resultSet
						.getBigDecimal("Wholesale_price"));
				book.setSalePrice(resultSet.getBigDecimal("Sale_price"));
				book.setListPrice(resultSet.getBigDecimal("List_price"));
				book.setCategoryId(resultSet.getLong("CategoryId"));
				book.setWeight(resultSet.getInt("Weight"));
				book.setDimensions(resultSet.getString("Dimensions"));
				book.setRemovalStatus(resultSet.getBoolean("Removal_Status"));
				book.setDateAdded(resultSet.getTimestamp("Date_Added"));
				book.setNumberPages(resultSet.getInt("Number_Pages"));
				book.setImage(resultSet.getString("Image"));

				rows.add(book);

			}

		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.BookDAOInterface#deleteBook(java.lang.String, long)
	 */
	@Override
	public int deleteBook(String sql, long id) throws SQLException {
		int result = 0;

		try (Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, id);// Sets the parameter in the
								// query with a long that
								// holds the appointments id
			result = ps.executeUpdate();// Updates the query

		}
		return result;// Returns the number of records deleted as an integer
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.BookDAOInterface#insertRecord(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int insertRecord(String sql, ArrayList<Object> values)
			throws SQLException {
		int result = 0;
		try (Connection conn = ds.getConnection();

		PreparedStatement pStatement = conn.prepareStatement(sql)) {

			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(BigDecimal.class)) {
					pStatement.setBigDecimal(i, (BigDecimal) values.get(j));
				} else if (values.get(j).getClass().equals(Timestamp.class)) {
					pStatement.setTimestamp(i, (Timestamp) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				}  else if (values.get(j).getClass().equals(Boolean.class)) {
					pStatement.setBoolean(i, (Boolean) values.get(j));
				}else {
					pStatement.setInt(i, (int) values.get(j));
				}
			}

			result = pStatement.executeUpdate();

		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.BookDAOInterface#searchBook(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public ArrayList<BookBean> searchBook(String sql, ArrayList<Object> values)
			throws SQLException {
		ArrayList<BookBean> rows = new ArrayList<BookBean>();

		try (Connection conn = ds.getConnection();

		PreparedStatement pStatement = conn.prepareStatement(sql);) {

			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(BigDecimal.class)) {
					pStatement.setBigDecimal(i, (BigDecimal) values.get(j));
				} else if (values.get(j).getClass().equals(Timestamp.class)) {
					pStatement.setTimestamp(i, (Timestamp) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				} else if (values.get(j).getClass().equals(Boolean.class)) {
					pStatement.setBoolean(i, (Boolean) values.get(j));
				} else {
					pStatement.setInt(i, (int) values.get(j));
				}
			}

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					BookBean book=new BookBean();
					book.setBookId(resultSet.getLong("BookId"));
					book.setIsbn(resultSet.getString("ISBN"));
					book.setFormatId(resultSet.getLong("FormatId"));
					book.setTitle(resultSet.getString("Title"));
					book.setPublisherId(resultSet.getLong("PublisherId"));
					book.setNumberCopies(resultSet.getInt("Number_copies"));
					book.setWholesalePrice(resultSet
							.getBigDecimal("Wholesale_price"));
					book.setSalePrice(resultSet.getBigDecimal("Sale_price"));
					book.setListPrice(resultSet.getBigDecimal("List_price"));
					book.setCategoryId(resultSet.getLong("CategoryId"));
					book.setWeight(resultSet.getInt("Weight"));
					book.setDimensions(resultSet.getString("Dimensions"));
					book.setRemovalStatus(resultSet.getBoolean("Removal_Status"));					
					book.setDateAdded(resultSet.getTimestamp("Date_Added"));
					book.setNumberPages(resultSet.getInt("Number_Pages"));
					book.setImage(resultSet.getString("Image"));
					rows.add(book);

				}
			}

		}

		return rows;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.BookDAOInterface#updateBook(long, java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int updateBook(long id, String sql, ArrayList<Object> values)
			throws SQLException {
		int result = 0;
		
		try (Connection conn = ds.getConnection();

		PreparedStatement pStatement = conn.prepareStatement(sql)) {
			int j;
			int i;
			// add values to columns for updating
			for (i = 1, j = 0; i <= values.size(); i++, j++) {
				
				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(BigDecimal.class)) {
					pStatement.setBigDecimal(i, (BigDecimal) values.get(j));
				} else if (values.get(j).getClass().equals(Timestamp.class)) {
					pStatement.setTimestamp(i, (Timestamp) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				} else if (values.get(j).getClass().equals(Boolean.class)) {
					pStatement.setBoolean(i, (Boolean) values.get(j));
				} else {
					pStatement.setInt(i, (int) values.get(j));
				}
			}

			pStatement.setLong(i, id);// add value to where clause

			result = pStatement.executeUpdate();

		}

		return result;
	}
	
	/**
	 * @author Svetlana Shopova and Joseph He
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public long getLastId(String sql) throws SQLException
	{
		if (ds == null)
			throw new SQLException("Can't get data source");
		long id=0;
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);
				ResultSet rs=pStatement.executeQuery())
				{
						while(rs.next())
						{
							id=rs.getLong("BookId");
						}
					
				}
		
		
		return id;
	}//end of getLastId()

}
