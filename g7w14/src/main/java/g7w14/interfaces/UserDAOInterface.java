package g7w14.interfaces;

import g7w14.data.UserBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for the User data access interface
 * 
 * @author Martin Nafekh 1032763
 * 
 */
public interface UserDAOInterface {

	/**
	 * This method retrieve all users from USERS table
	 * 
	 * @return ArrayList with all registered users
	 * @throws SQLException
	 */
	public ArrayList<UserBean> getAll() throws SQLException;

	/**
	 * This method retrieves records from the users table
	 * 
	 * @param sql
	 *            - String with sql statement
	 * @param values
	 *            - ArrayList<Object> - list with values that defines where
	 *            clause of the sql statement
	 * @return list of records that respect the conditions of the sql statement
	 * @throws SQLException
	 */
	public ArrayList<UserBean> select(String sql, ArrayList<Object> values)
			throws SQLException;

	/**
	 * This method receive an sql statement from UserManager, open database,
	 * delete a record that is sent by manager, and close database
	 * 
	 * @param sql
	 *            - string - sql delete statement
	 * @param id
	 *            - - id of the record that has to be deleted
	 * @return int result of the operation(1-successful,0- error, 2 - all
	 *         records are deleted)
	 * @throws SQLException
	 *             - if database is unaccessible
	 */
	public int delete(String sql, long id) throws SQLException;

	/**
	 * This method opens database, insert a record, and close database
	 * 
	 * @param sql
	 *            --String - sql statement that defines values in which fields
	 *            have to be inserted
	 * @param data
	 *            - ArrayList<Object> - list of values which have to be inserted
	 * @return int result of the operation(1 - successful, 0- error)
	 * @throws SQLException
	 *             - if database is unaccessible
	 */
	public int insert(String sql, ArrayList<Object> data) throws SQLException;

	/**
	 * This method opens database, find a record by id, update fields with the
	 * values sent by the UserManager, and closes database
	 * 
	 * @param id
	 *            -long, id of the record that has to be updated
	 * @param sql
	 *            - string - sql statement defining which fields of the table
	 *            have to be changed
	 * @param password
	 *            - user's new password
	 * @return result of the operation(1-successful, 0 - error)
	 */
	public int update(String sql, long id, String password) throws SQLException;
}
