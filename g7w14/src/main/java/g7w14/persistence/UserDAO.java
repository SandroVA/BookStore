/**
 * 
 */
package g7w14.persistence;

import g7w14.data.CustomerBean;
import g7w14.data.UserBean;
import g7w14.interfaces.UserDAOInterface;

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
 * This class provides the functionality to: open a database, retrieve records
 * from a users table and return them as an arrayList, delete records, insert
 * records, update records of User table
 * 
 * @author Svetlana Shopova
 * @since 13.02.2014
 * @version 1.0
 */

@Named
@RequestScoped
public class UserDAO implements Serializable, UserDAOInterface {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4804235427490905969L;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	UserBean user;

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.UserDAOInterface#getAll()
	 */
	@Override
	public ArrayList<UserBean> getAll() throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");

		ArrayList<UserBean> users = new ArrayList<UserBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM users order by Username");
				ResultSet resultSet = stmt.executeQuery();) {
			while (resultSet.next()) {
				UserBean us= new UserBean();
				us.setUsername(resultSet.getString("Username"));
				us.setPassword(resultSet.getString("Password"));
				us.setUserId(resultSet.getLong("UserId"));
				us.setUserTypeId(resultSet.getLong("UserTypeId"));

				users.add(us);
			}
		}
		return users;
	}// end getAll()
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * This method find all users that still are not clients 
	 * and return their names 
	 * @return
	 * @throws SQLException 
	 */
	public ArrayList<String> getAllNew() throws SQLException	
	{
		if (ds == null)
			throw new SQLException("Can't get data source");
		
		ArrayList<String> newUsers = new ArrayList<String>();
		try(Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT Username FROM users where UserId not in(select UserId from customer)");
				ResultSet resultSet = stmt.executeQuery();)
				{
					while(resultSet.next())
					{
						newUsers.add(resultSet.getString("Username"));
						
					}
				}
		
		return newUsers;
	}//end getAlNew()
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.UserDAOInterface#select(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public ArrayList<UserBean> select(String sql, ArrayList<Object> values)
			throws SQLException {
		
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= values.size(); i++, j++) {

				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}

			try (ResultSet resultSet = pStatement.executeQuery();) {
				while (resultSet.next()) {
					UserBean us = new UserBean();
					us.setUsername(resultSet.getString("Username"));
					us.setPassword(resultSet.getString("Password"));
					us.setUserId(resultSet.getLong("UserId"));
					us.setUserTypeId(resultSet.getLong("UserTypeId"));

					users.add(us);
				}
			}
		}

		return users;

	}// end of select
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.UserDAOInterface#select(java.lang.String,
	 * java.util.ArrayList)
	 */
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param sql
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<UserBean> selectUsername(String sql, ArrayList<Object> values)
			throws SQLException {
		
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= values.size(); i++, j++) {

				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}

			try (ResultSet resultSet = pStatement.executeQuery();) {
				while (resultSet.next()) {
					UserBean us = new UserBean();
					us.setUsername(resultSet.getString("Username"));

					users.add(us);
				}
			}
		}

		return users;

	}// end of select

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.UserDAOInterface#delete(java.lang.String, long)
	 */
	@Override
	public int delete(String sql, long id) throws SQLException {
		int result = 0;

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setLong(1, id);

			result = pStatement.executeUpdate();
		}

		return result;
	}// end of delete()

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.UserDAOInterface#insert(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int insert(String sql, ArrayList<Object> data) throws SQLException {
		int result = 0;
		if (ds == null)
			throw new SQLException("Can't get data source");
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setString(1, (String) data.get(0));
			pStatement.setString(2, (String) data.get(1));
			pStatement.setLong(3, (long) data.get(2));
			result = pStatement.executeUpdate();
		}

		return result;
	}// end of insert()

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.UserDAOInterface#update(java.lang.String, long,
	 * java.lang.String)
	 */
	@Override
	public int update(String sql, long id, String password) throws SQLException {
		int result = 0;
		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setString(1, password);
			pStatement.setLong(2, id);

			result = pStatement.executeUpdate();

		}

		return result;
	}// end of update()

	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return
	 */
	public ArrayList<CustomerBean> getCustomerId(String sql,
			ArrayList<Object> values) throws SQLException {
		ArrayList<CustomerBean> bean = new ArrayList<CustomerBean>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}

			try (ResultSet resultSet = pStatement.executeQuery();) {
				while (resultSet.next()) {
					CustomerBean cb = new CustomerBean();
					cb.setCustomerId(resultSet.getLong("CustomerId"));

					bean.add(cb);
				}
			}
		}
		return bean;
	}
}// end of UserDAO class
