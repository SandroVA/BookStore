
package g7w14.business;

import g7w14.data.BookBean;
import g7w14.data.FormatBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;



/**
 * @author Svetlana Shopova
 * 
 * @since 24.03.2014
 */
@Named("formatManager")
@SessionScoped
public class FormatManager implements Serializable{
	
	
	private static final long serialVersionUID = -6797852091825441299L;
	@Resource(name="jdbc/g7w14")
	private DataSource ds;
	@Inject
	private BookBean book;
	@Inject
	private FormatBean formatBean;
	
	public ArrayList<String> getFormatNames() {
		if(formatNames==null)
		{
			formatNames=getAllFormatNames();
		}
		return formatNames;
	}


	public void setFormatNames(ArrayList<String> formatNames) {
		this.formatNames = formatNames;
	}



	private ArrayList<String> formatNames;
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	
	
	private ArrayList<FormatBean> getAll()
	{
		ArrayList<FormatBean> all = new ArrayList<FormatBean>();
		
		try(Connection conn=ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement("select * from formats");
				ResultSet rs=pStatement.executeQuery())
				{
					while(rs.next())
					{
						FormatBean fb = new FormatBean();
						fb.setFormatId(rs.getLong("FormatId"));
						fb.setFormatName(rs.getString("Type_Format"));
						all.add(fb);
					}
				} catch (SQLException e) {
					
					log.error(e.getMessage());
				}
		
		return all;
		
	}

	
	/**
	 * This method returns a list with names of all formats
	 * @return
	 */
	private ArrayList<String> getAllFormatNames()
	{
		ArrayList<String> allNames = new ArrayList<String>();
		ArrayList<FormatBean> allFormats = getAll();
		allNames.add(" ");
		for(FormatBean fb:allFormats)
		{
			allNames.add(fb.getFormatName());
		}
		
		return allNames;
	}
	
	/**
	 * This method takes the name of the chosen format
	 * and find its id in the database. After that 
	 * the method sets the formatId in BookBean 
	 * (see adminInsertBook.xhtml)
	 */
	public void setFormatId()
	{
		ArrayList<Long> all = new ArrayList<Long>();
		
		try(Connection conn=ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement("select FormatId from formats where Type_Format=?");
				)
				{
					pStatement.setString(1, formatBean.getFormatName());
					try(ResultSet rs=pStatement.executeQuery()){
						while(rs.next())
						{
							
							
							all.add(rs.getLong("FormatId"));
						}
					}
					
				} catch (SQLException e) {
					
					log.error(e.getMessage());
				}
		book.setFormatId(all.get(0));
		
	}//end of setFormatId()

}
