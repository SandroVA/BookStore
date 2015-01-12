package g7w14.interfaces;

import g7w14.data.BookBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Inferface for the book data access object
 * 
 * @author Martin Nafekh 1032763
 * 
 */
public interface BookDAOInterface {

	/**
	 * This method retrieve all records from the books table
	 * 
	 * @param sql
	 *            -String - sql select statement
	 */
	public ArrayList<BookBean> retrieveRecords() throws SQLException;

	/**
	 * This method receives a String that represents and sql statement and a
	 * Long that represents the id of the record to be deleted in the database.
	 * It then proceeds to delete the record.
	 * 
	 * @param sql
	 *            -String - sql statement that defines values in which fields
	 *            have to be inserted
	 * @param id
	 *            - id of the record that has to be deleted
	 * @return int result of the operation(1-successful,0- error, 2 - all
	 *         records are deleted)
	 * @throws SQLException
	 */
	public int deleteBook(String sql, long id) throws SQLException;

	/**
	 * This method opens database, insert a record, and close database
	 * 
	 * @param sql
	 *            -String - sql statement that defines values in which fields
	 *            have to be inserted
	 * @param values
	 *            - ArrayList<Object> - list of values which have to be inserted
	 * @return int result of the operation(1 - successful, 0- error)
	 */
	public int insertRecord(String sql, ArrayList<Object> values)
			throws SQLException;

	/**
	 * This method retrieves records from books table
	 * 
	 * @param sql
	 *            - string - sql statement that defines which records to be
	 *            retrieved
	 * @param values
	 *            - ArrayList<Object> - list with values that defines where
	 *            clause of the sql statement
	 * @return ArrayList<BookBean> - list of records that respect the conditions
	 *         of the sql statement
	 */
	public ArrayList<BookBean> searchBook(String sql, ArrayList<Object> values)
			throws SQLException;

	/**
	 * This method opens database, find a record by id, update fields with the
	 * values sent by the BookManager, and closes database
	 * 
	 * @param id
	 *            -long, id of the record that has to be updated
	 * @param sql
	 *            - string - sql statement defining which fields of the table
	 *            have to be changed
	 * @param values
	 *            - list of values that have to be insert in the table
	 * @return result of the operation(1-successful, 0 - error)
	 */
	public int updateBook(long id, String sql, ArrayList<Object> values)
			throws SQLException;
}
