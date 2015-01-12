package g7w14.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import g7w14.data.ReviewBean;
import g7w14.interfaces.ReviewDAOInterface;

/**
 * This class provides the functionality to: open a database, retrieve records
 * from a user query and return them as an arrayList, delete records, insert
 * records, update records
 * 
 * @author Sandro Victoria Arena
 * @since 2.13.2014
 */

@Named
@RequestScoped
public class ReviewDAO implements Serializable, ReviewDAOInterface {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	ReviewBean bean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.ReviewDAOInterface#select(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public ArrayList<ReviewBean> select(String sql, ArrayList<Object> values)
			throws SQLException {

		ArrayList<ReviewBean> review = new ArrayList<ReviewBean>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(Integer.class)) {
					pStatement.setInt(i, (int) values.get(j));
				} else if (values.get(j).getClass().equals(Boolean.class)) {
					pStatement.setBoolean(i, (Boolean) values.get(j));
				} else if (values.get(j).getClass().equals(Timestamp.class)) {
					pStatement.setTimestamp(i, (Timestamp) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}

			try (ResultSet resultSet = pStatement.executeQuery();) {
				while (resultSet.next()) {

					bean.setBookId(resultSet.getInt("BookId"));
					bean.setCustomerId(resultSet.getInt("CustomerId"));
					bean.setRating(resultSet.getInt("Rating"));
					bean.setReview_Date(resultSet.getTimestamp("Review_Date"));
					bean.setReviewText(resultSet.getString("Review_Text"));
					bean.setReviewId(resultSet.getInt("ReviewId"));
					bean.setApproved(resultSet
							.getBoolean("Approved"));

					review.add(bean);
				}
			}
		}

		return review;

	}// end of select

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.ReviewDAOInterface#getReviews(java.lang.String)
	 */
	@Override
	public ArrayList<ReviewBean> getReviews(String sql) throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");

		ArrayList<ReviewBean> rows = new ArrayList<ReviewBean>();

		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(sql);) {
			while (resultSet.next()) {
				ReviewBean rb = new ReviewBean();
				rb.setBookId(resultSet.getInt("BookId"));
				rb.setCustomerId(resultSet.getInt("CustomerId"));
				rb.setRating(resultSet.getInt("Rating"));
				rb.setReview_Date(resultSet.getTimestamp("Review_Date"));
				rb.setReviewText(resultSet.getString("Review_Text"));
				rb.setReviewId(resultSet.getInt("ReviewId"));
				rb.setApproved(resultSet.getBoolean("Approved"));

				rows.add(rb);
			}
		}

		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.ReviewDAOInterface#getAll()
	 */
	@Override
	public ArrayList<ReviewBean> getAll() throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");

		ArrayList<ReviewBean> rows = new ArrayList<ReviewBean>();

		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt
						.executeQuery("Select * From Customer_Review order by review_date");) {
			while (resultSet.next()) {
				ReviewBean rb = new ReviewBean();
				rb.setBookId(resultSet.getInt("BookId"));
				rb.setCustomerId(resultSet.getInt("CustomerId"));
				rb.setRating(resultSet.getInt("Rating"));
				rb.setReview_Date(resultSet.getTimestamp("Review_Date"));
				rb.setReviewText(resultSet.getString("Review_Text"));
				rb.setReviewId(resultSet.getInt("ReviewId"));
				rb.setApproved(resultSet.getBoolean("Approved"));

				rows.add(rb);
			}
		}

		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.ReviewDAOInterface#deleteRecord(java.lang.String,
	 * long)
	 */
	@Override
	public int deleteRecord(String sql, long id) throws SQLException {
		int result = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setLong(1, id);
			result = stmt.executeUpdate();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.ReviewDAOInterface#insertRecord(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int insertRecord(String sql, ArrayList<Object> values)
			throws SQLException {
		int result = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					stmt.setString(i, (String) values.get(j));
				}

				else if (values.get(j).getClass().equals(Boolean.class)) {
					boolean bol = ((Boolean) values.get(j)).booleanValue();
					stmt.setBoolean(i, bol);
				}

				else if (values.get(j).getClass().equals(Integer.class)) {
					stmt.setInt(i, ((int) values.get(j)));
				}

				else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					stmt.setLong(i, l);
				}

				else {
					stmt.setTimestamp(i, (Timestamp) values.get(j));
				}
			}

			result = stmt.executeUpdate();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.ReviewDAOInterface#updateRecord(long,
	 * java.lang.String, java.util.ArrayList)
	 */
	@Override
	public int updateRecord(long id, String sql, ArrayList<Object> values)
			throws SQLException {
		
		int result = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
				int i;
				int j;
			for ( i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					stmt.setString(i, (String) values.get(j));
				}

				else if (values.get(j).getClass().equals(Boolean.class)) {
					boolean bol = ((Boolean) values.get(j)).booleanValue();
					stmt.setBoolean(i, bol);
				}

				else if (values.get(j).getClass().equals(Integer.class)) {
					stmt.setInt(i, ((int) values.get(j)));
				}

				else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					stmt.setLong(i, l);
				}

				else {
					stmt.setTimestamp(i, (Timestamp) values.get(j));
				}
			}
			
			stmt.setLong(i, id);
			result = stmt.executeUpdate();
		}
		return result;
	}
	
	/**
	 * @author Svetlana Shopova
	 * @param sql
	 * @param reviewId
	 * @param approved
	 * @return
	 * @throws SQLException
	 */
	public int approveReview(String sql, long reviewId, boolean approved) throws SQLException
	{
		int result = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);)
				{
					stmt.setBoolean(1, approved);
					stmt.setLong(2, reviewId);
					result=stmt.executeUpdate();
				}
		return result;
	}
}
