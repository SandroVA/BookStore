/**
 * 
 */
package g7w14.persistence;

import g7w14.data.PublisherBean;

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
 * This class is responsible for creating 
 * connection between database and 
 * PublishManager class. This class is needed 
 * for extracting and  saving data in Publisher table
 * @author Svetlana Shopova and Sandro Victoria Arena
 * @since 15.03.2014
 */

@Named
@RequestScoped
public class PublisherDAO implements Serializable {

	
	private static final long serialVersionUID = 1351407471502155090L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	private PublisherBean pb;
		
	/**
	 * This method get all existing records from
	 * publisher table
	 * @return list with all publishers
	 * @throws SQLException
	 */
	public ArrayList<PublisherBean> getAll() throws SQLException
	{
		if (ds == null)			
				throw new SQLException("Can't get data source");
			

		ArrayList<PublisherBean> publishers = new ArrayList<PublisherBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM publisher order by Publisher_Name");
				ResultSet resultSet = stmt.executeQuery();) {
			while (resultSet.next()) {
				PublisherBean pb= new PublisherBean();
				pb.setPublisherId(resultSet.getLong("PublisherId"));
				pb.setPublisherName(resultSet.getString("Publisher_Name"));
				

				publishers.add(pb);
			}
		}
		
		return publishers;
	}//end of getAll
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * This method gets the publishers name based on its id
	 * @param sql - the sql needed to get the publishers name
	 * @param id - the publishers id
	 * @return - The publishers name related to its id
	 * @throws SQLException
	 */
	public ArrayList<PublisherBean> getPublisherById(String sql) throws SQLException
	{
		ArrayList<PublisherBean> results = new ArrayList<PublisherBean>();
		
		if (ds == null)
			throw new SQLException("Can't get data source");
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);)
				{
					pStatement.setLong(1, pb.getPublisherId());
					
					try (ResultSet resultSet = pStatement.executeQuery())
					{
						while(resultSet.next())
						{
							PublisherBean bean = new PublisherBean();
							bean.setPublisherName(resultSet.getString("Publisher_Name"));
							
							results.add(bean);
						}
					}
				} 
		
		return results;
	}//end of getPublisherId
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * This method gets the publishers name based on its id
	 * @param sql - the sql needed to get the publishers name
	 * @param id - the publishers id
	 * @return - The publishers name related to its id
	 * @throws SQLException
	 */
	public ArrayList<PublisherBean> getPublisherById(String sql, long id) throws SQLException
	{
		ArrayList<PublisherBean> results = new ArrayList<PublisherBean>();
		
		if (ds == null)
			throw new SQLException("Can't get data source");
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);)
				{
					pStatement.setLong(1, id);
					
					try (ResultSet resultSet = pStatement.executeQuery())
					{
						while(resultSet.next())
						{
							PublisherBean bean = new PublisherBean();
							bean.setPublisherName(resultSet.getString("Publisher_Name"));
							
							results.add(bean);
						}
					}
				} 
		
		return results;
	}//end of getPublisherId
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * This method get from the data base
	 * all sales by a publisher for defined by the admin 
	 * date range
	 * @param sql - Sql statement that will extract the data
	 * @param publisherName - the name of the publisher 
	 * @param startDate the start of te period
	 * @param endDate - the end of the period
	 * @return - double - the amount of the sales
	 * @throws SQLException
	 */
	public double salesByPublisher(String sql,String publisherName, String startDate, String endDate ) throws SQLException
	{
		if (ds == null)			
			throw new SQLException("Can't get data source");
		ArrayList<Double> results = new ArrayList<Double>();
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);)
				{
					pStatement.setString(1, publisherName);
					pStatement.setString(2, startDate);
					pStatement.setString(3, endDate);
					
					try (ResultSet resultSet = pStatement.executeQuery())
					{
						while(resultSet.next())
						{
							results.add(resultSet.getDouble(1));
						}
					}
				} 
		return results.get(0);
	}// end of salesByPublisher()
	
	/**
	 * This method insert a new publisher in table publisher
	 * @param sql - insert sql statement
	 * @return result of the operation - 0(record is not inserted), 1(record is inserted)
	 * @throws SQLException
	 */
	public int insertPublisher(String sql) throws SQLException
	{
		
		int result =0;
			if(ds==null)
			{
				throw new SQLException("Cannot get data source");
				
			}
			
			try(Connection conn=ds.getConnection();
					PreparedStatement pStatement=conn.prepareStatement(sql))
					{
						pStatement.setString(1, pb.getPublisherName());
						result=pStatement.executeUpdate();
					}
		
		return result;
	}//end insertPublisher()
	
	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PublisherBean> getPublisherByName(String sql) throws SQLException
	{
		ArrayList<PublisherBean> results = new ArrayList<PublisherBean>();
		
		if (ds == null)
			throw new SQLException("Can't get data source");
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);)
				{
					pStatement.setString(1, pb.getPublisherName());
					
					try (ResultSet resultSet = pStatement.executeQuery())
					{
						while(resultSet.next())
						{
							PublisherBean bean = new PublisherBean();
							bean.setPublisherName(resultSet.getString("Publisher_Name"));
							bean.setPublisherId(resultSet.getLong("PublisherId"));
							results.add(bean);
						}
					}
				} 
		
		
		return results;
	}
}//end of PublisherDAO
