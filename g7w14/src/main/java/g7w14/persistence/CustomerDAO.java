/**
 * 
 */
package g7w14.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import g7w14.data.CustomerBean;
import g7w14.interfaces.CustomerDAOInterface;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * This class provides the functionality to: open a database, retrieve records
 * from a customer table and return them as an arrayList, delete records, insert
 * records, update records of Customer table
 * 
 * @author Svetlana Shopova
 * updated by Joseph He
 * @since 20.02.2014
 * @version 1
 */

@Named
@RequestScoped
public class CustomerDAO implements Serializable, CustomerDAOInterface {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	CustomerBean customer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.CustomerDAOInterface#getAll()
	 */
	@Override
	public ArrayList<CustomerBean> getAll() throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");

		ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM customer order by L_Name");
				ResultSet resultSet = stmt.executeQuery();) {
			while (resultSet.next()) {
				CustomerBean cust = new CustomerBean();
				cust.setCustomerId(resultSet.getLong("CustomerId"));
				cust.setUserId(resultSet.getLong("UserId"));
				cust.setTitle(resultSet.getString("Title"));
				cust.setL_Name(resultSet.getString("L_Name"));
				cust.setF_Name(resultSet.getString("F_Name"));
				cust.setCompany(resultSet.getString("Company"));
				cust.setAddress1(resultSet.getString("Address1"));
				cust.setAddress2(resultSet.getString("Address2"));
				cust.setCity(resultSet.getString("City"));
				cust.setProvince(resultSet.getString("Province"));
				cust.setCountry(resultSet.getString("Country"));
				cust.setPostal_Code(resultSet.getString("Postal_Code"));
				cust.setHome_Phone(resultSet.getString("Home_Phone"));
				cust.setCell_Phone(resultSet.getString("Cell_Phone"));
				cust.setEmail(resultSet.getString("Email"));

				customers.add(cust);
			}
		}
		return customers;
	}// end getAll()

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.CustomerDAOInterface#select(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public ArrayList<CustomerBean> select(String sql, ArrayList<Object> values)
			throws SQLException {

		ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					pStatement.setLong(i, l);
				} else if (values.get(j).getClass().equals(Boolean.class)) {
					pStatement.setBoolean(i, (Boolean) values.get(j));
				} else if (values.get(j).getClass().equals(Double.class)) {
					pStatement.setDouble(i, (double) values.get(j));
				}
			}

			try (ResultSet resultSet = pStatement.executeQuery();) {
				while (resultSet.next()) {
					CustomerBean cust = new CustomerBean();
					cust.setCustomerId(resultSet.getLong("CustomerId"));
					cust.setUserId(resultSet.getLong("UserId"));
					cust.setTitle(resultSet.getString("Title"));
					cust.setL_Name(resultSet.getString("L_Name"));
					cust.setF_Name(resultSet.getString("F_Name"));
					cust.setCompany(resultSet.getString("Company"));
					cust.setAddress1(resultSet.getString("Address1"));
					cust.setAddress2(resultSet.getString("Address2"));
					cust.setCity(resultSet.getString("City"));
					cust.setProvince(resultSet.getString("Province"));
					cust.setCountry(resultSet.getString("Country"));
					cust.setPostal_Code(resultSet.getString("Postal_Code"));
					cust.setHome_Phone(resultSet.getString("Home_Phone"));
					cust.setCell_Phone(resultSet.getString("Cell_Phone"));
					cust.setEmail(resultSet.getString("Email"));
					
					customers.add(cust);
				}
			}
		}

		return customers;

	}// end of select

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.CustomerDAOInterface#delete(java.lang.String, long)
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
	 * @see g7w14.interfaces.CustomerDAOInterface#select(java.lang.String,
	 * java.util.ArrayList)
	 */
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CustomerBean> getCustomerId(long values)
			throws SQLException {
		String sql = "select * from customer where userId = ?";
		ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();
		try (Connection conn = ds.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql);) {
		    pStatement.setLong(1, values);

			try (ResultSet resultSet = pStatement.executeQuery();) {
				while (resultSet.next()) {
					CustomerBean cust = new CustomerBean();
					cust.setCustomerId(resultSet.getLong("CustomerId"));								
					customers.add(cust);
				}
			}
		}

		return customers;

	}// end of select
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.CustomerDAOInterface#insert(java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int insert(String sql, ArrayList<Object> data) throws SQLException {
		int result = 0;
		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= data.size(); i++, j++) {
				if (data.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) data.get(j));
				} else if (data.get(j).getClass().equals(Long.class)) {
					long l = ((Long) data.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}
			result = pStatement.executeUpdate();
		}

		return result;
	}// end of insert()

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.CustomerDAOInterface#update(long, java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public int update(long id, String sql, ArrayList<Object> data)
			throws SQLException {
		int result = 0;
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			int i;
			int j;

			for (i = 1, j = 0; i <= data.size(); i++, j++) {
				if (data.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) data.get(j));
				} else if (data.get(j).getClass().equals(Long.class)) {
					long l = ((Long) data.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}

			pStatement.setLong(i, id);
			result = pStatement.executeUpdate();
		}
		return result;
	}// end of update()

	public int update(String sql, ArrayList<Object> data)
			throws SQLException {
		int result = 0;
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			int i;
			int j;

			for (i = 1, j = 0; i <= data.size(); i++, j++) {
				if (data.get(j).getClass().equals(String.class)) {
					pStatement.setString(i, (String) data.get(j));
				} else if (data.get(j).getClass().equals(Long.class)) {
					long l = ((Long) data.get(j)).longValue();
					pStatement.setLong(i, l);
				}
			}
			result = pStatement.executeUpdate();
		}
		return result;
	}// end of update()

	/**
	 * This method find the sum of all purchases for a chosen customer
	 * 
	 * @param sql
	 *            The query statement
	 * @param firstName
	 *            Fist name of the customer
	 * @param lastName
	 *            Last name of the customer
	 * @param address
	 *            Addres of the customer
	 * @param startDate
	 *            the beginning of the desired period
	 * @param endDate
	 *            the end of desired period
	 * @return
	 * @throws SQLException
	 */
	public double salesByCustomer(String sql, String firstName,
			String lastName, String address, String startDate, String endDate)
			throws SQLException {
		ArrayList<Double> results = new ArrayList<Double>();

		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setString(3, address);
			pStatement.setString(4, startDate);
			pStatement.setString(5, endDate);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					results.add(resultSet.getDouble(1));
				}
			}
		}

		return results.get(0);
	}// end of salesByCustomer()

	/**
	 * This method returns a list with the clients
	 * who already have placed orders. The list is
	 * ordered by the value of sales in descendant order
	 * for defined by the admin period
	 * @param sql -SQL statement of the query
	 * @param startDate -start of desired report period
	 * @param endDate - end of desired  report period
	 * @return list with customers who have orders
	 * @throws SQLException if data base is not found
	 */
	public ArrayList<CustomerBean> getTopClients(String sql, String startDate,
			String endDate) throws SQLException {
			ArrayList<CustomerBean> topClients= new ArrayList<CustomerBean>();
			
			if(ds==null)
			{
				throw new SQLException("Cannot get data source");
			}
			try (Connection conn = ds.getConnection();
					PreparedStatement pStatement = conn.prepareStatement(sql);) {
				pStatement.setString(1, startDate);
				pStatement.setString(2, endDate);				

				try (ResultSet resultSet = pStatement.executeQuery()) {
					while (resultSet.next()) {
						CustomerBean client = new CustomerBean();
						client.setCustomerId(resultSet.getLong("CustomerId"));
						client.setF_Name(resultSet.getString("F_Name"));
						client.setL_Name(resultSet.getString("L_Name"));
						client.setCity(resultSet.getString("City"));
						client.setAddress1(resultSet.getString("Address1"));
						client.setSales(resultSet.getDouble("Sales"));
						
						topClients.add(client);
					}
				}
			}
			
			return topClients;
	}//end of getTopClients()
}// end of CustomerDAO class
