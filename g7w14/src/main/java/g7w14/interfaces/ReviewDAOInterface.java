package g7w14.interfaces;

import g7w14.data.ReviewBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for the Review data access object
 * 
 * @author Sandro Victoria Arena, Martin Nafekh
 * 
 */
public interface ReviewDAOInterface {

	/**
	 * This method retrieves records from the review table
	 * 
	 * @param sql
	 *            - String with sql statement
	 * @param values
	 *            - ArrayList<Object> - list with values that defines where
	 *            clause of the sql statement
	 * @return list of records that respect the conditions of the sql statement
	 * @throws SQLException
	 */
	public ArrayList<ReviewBean> select(String sql, ArrayList<Object> values)
			throws SQLException;

	/**
	 * Retrieves records from the review table, but with a regular statement
	 * 
	 * @param sql
	 * @return arraylist of results
	 * @throws SQLException
	 */
	public ArrayList<ReviewBean> getReviews(String sql) throws SQLException;

	/**
	 * Retrieves all the records from the review table
	 * 
	 * @return arraylist with all the records
	 * @throws SQLException
	 */
	public ArrayList<ReviewBean> getAll() throws SQLException;

	/**
	 * Deletes a record from the review table with matching id
	 * 
	 * @param sql
	 *            delete statement
	 * @param id
	 *            of record to delete
	 * @return1 if success, 0 if fail
	 * @throws SQLException
	 */
	public int deleteRecord(String sql, long id) throws SQLException;

	/**
	 * Inserts a record using the passed values
	 * 
	 * @param sql
	 *            insert statement
	 * @param values
	 *            of new record to insert
	 * @return 1 if success, 0 if fail
	 * @throws SQLException
	 */
	public int insertRecord(String sql, ArrayList<Object> values)
			throws SQLException;

	/**
	 * Updates an existing record matching the passed id
	 * 
	 * @param id
	 *            of record to update
	 * @param sql
	 *            update statement
	 * @param values
	 *            fields to be updated
	 * @return 1 if success, 0 if fail
	 * @throws SQLException
	 */
	public int updateRecord(long id, String sql, ArrayList<Object> values)
			throws SQLException;
}
