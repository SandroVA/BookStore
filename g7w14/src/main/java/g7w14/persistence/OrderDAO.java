package g7w14.persistence;

import g7w14.data.OrderBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * 
 * @author Martin Nafekh 1032763, Svetlana Shopova
 * 
 *         Connects to the database in order to perform query, insert, or update
 *         operations on the orders table
 */
@Named
@RequestScoped
public class OrderDAO implements Serializable{

	private static final long serialVersionUID = -2666135325453811158L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.OrderDAOInterface#queryRecords(java.lang.String,
	 * java.util.ArrayList)
	 */
	public ArrayList<OrderBean> queryRecords(String sql,
			ArrayList<Object> values) throws SQLException {
		ArrayList<OrderBean> records = new ArrayList<OrderBean>();
		
		try (Connection connection = ds.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {
			for (int i = 1, j = 0; i <= values.size(); i++, j++) {
				if (values.get(j).getClass().equals(String.class)) {
					statement.setString(i, (String) values.get(j));
				} else if (values.get(j).getClass().equals(Long.class)) {
					long l = ((Long) values.get(j)).longValue();
					statement.setLong(i, l);
				} else if (values.get(j).getClass().equals(Timestamp.class)) {
					statement.setTimestamp(i, (Timestamp) values.get(j));
				}
			}
			
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					OrderBean ord = new OrderBean();
					ord.setOrderId(resultSet.getInt("OrderId"));
					ord.setCustomerId(resultSet.getInt("CustomerId"));
					ord.setOrderDate(resultSet.getTimestamp("Order_Date"));
					ord.setShipDate(resultSet.getTimestamp("Ship_Date"));
					ord.setTitle(resultSet.getString("Title"));
					ord.setsLastName(resultSet.getString("SL_Name"));
					ord.setsFirstName(resultSet.getString("SF_Name"));
					ord.setsCompany(resultSet.getString("SCompany"));
					ord.setsAddress1(resultSet.getString("SAddress1"));
					ord.setsAddress2(resultSet.getString("SAddress2"));
					ord.setsCity(resultSet.getString("SCity"));
					ord.setsProvince(resultSet.getString("SProvince"));
					ord.setsCountry(resultSet.getString("SCountry"));
					ord.setsPostalCode(resultSet.getString("SPostal_code"));

					records.add(ord);
				}
			}
		}

		return records;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.OrderDAOInterface#insert(java.lang.String,
	 * g7w14.data.OrderBean)
	 */
	public int insert(String sql, OrderBean order) throws SQLException {
		int result = 0;
		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setLong(1, order.getCustomerId());
			statement.setTimestamp(2, order.getOrderDate());
			statement.setTimestamp(3, order.getShipDate());
			statement.setString(4, order.getTitle());
			statement.setString(5, order.getsLastName());
			statement.setString(6, order.getsFirstName());
			statement.setString(7, order.getsCompany());
			statement.setString(8, order.getsAddress1());
			statement.setString(9, order.getsAddress2());
			statement.setString(10, order.getsCity());
			statement.setString(11, order.getsProvince());
			statement.setString(12, order.getsCountry());
			statement.setString(13, order.getsPostalCode());

			result = statement.executeUpdate();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see g7w14.interfaces.OrderDAOInterface#update(java.lang.String,
	 * g7w14.data.OrderBean)
	 */
	public int update(String sql, OrderBean order) throws SQLException {
		int result = 0;
	
		try (Connection conn = ds.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql)) {
				
			
			statement.setString(1, order.getTitle());
			statement.setString(2, order.getsLastName());
			statement.setString(3, order.getsFirstName());
			statement.setString(4, order.getsCompany());
			statement.setString(5, order.getsAddress1());
			statement.setString(6, order.getsAddress2());
			statement.setString(7, order.getsCity());
			statement.setString(8, order.getsProvince());
			statement.setString(9, order.getsCountry());
			statement.setString(10, order.getsPostalCode());
			statement.setLong(11, order.getOrderId());
			result = statement.executeUpdate();
		}

		return result;
	}

	/**
	 * 
	 * @param sql
	 * @param field
	 * @return
	 * @author Svetlana Shopova
	 * @throws SQLException
	 */
	public ArrayList<OrderBean> retrieveAll(String sql) throws SQLException {
		ArrayList<OrderBean> all = new ArrayList<OrderBean>();

		if (ds == null) {
			throw new SQLException("Cannot get the data source");
		}

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);
				ResultSet resultSet = pStatement.executeQuery();) {
			while (resultSet.next()) {
				OrderBean ord = new OrderBean();
				ord.setOrderId(resultSet.getLong("OrderId"));
				ord.setCustomerId(resultSet.getLong("CustomerId"));
				ord.setOrderDate(resultSet.getTimestamp("Order_Date"));
				ord.setShipDate(resultSet.getTimestamp("Ship_Date"));
				ord.setTitle(resultSet.getString("Title"));
				ord.setsLastName(resultSet.getString("SL_Name"));
				ord.setsFirstName(resultSet.getString("SF_Name"));
				ord.setsCompany(resultSet.getString("SCompany"));
				ord.setsAddress1(resultSet.getString("SAddress1"));
				ord.setsAddress2(resultSet.getString("SAddress2"));
				ord.setsCity(resultSet.getString("SCity"));
				ord.setsProvince(resultSet.getString("SProvince"));
				ord.setsCountry(resultSet.getString("SCountry"));
				ord.setsPostalCode(resultSet.getString("SPostal_code"));
				all.add(ord);
			}

		}

		return all;
	}

	/**
	 *  
	 * @return
	 * @author Svetlana Shopova
	 * @throws SQLException
	 */
	public ArrayList<Long> retrieveAll(String sql, String firstName,
			String lastName, String address) throws SQLException {
		ArrayList<Long> all = new ArrayList<Long>();
		
		if (ds == null) {
			throw new SQLException("Cannot get the data source");
		}

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {			
				pStatement.setString(1, firstName);
				pStatement.setString(2, lastName);
				pStatement.setString(3, address);
			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					
					all.add(resultSet.getLong("OrderId"));
				}
			}

		}
		return all;
	}
}
