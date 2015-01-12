package g7w14.business;

import g7w14.data.BannerBean;

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
 * BannerManager used to communicate with the database
 * @author Ian Sumanion and Joseph He
 *
 */

@Named("bannerManager")
@RequestScoped
public class BannerManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7781553448019396717L;
	
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	private BannerBean bb;
	/**
	 * @author Ian Ozturk
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BannerBean> getRandomBanner() throws SQLException{
		
		ArrayList<BannerBean> banner = new ArrayList<BannerBean>();
		
		String sql = "select * from banners order by RAND() LIMIT 1";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn
						.prepareStatement(sql);
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				
				BannerBean bb = new BannerBean();
				
				bb.setBannerId(resultSet.getLong("BannerId"));
				bb.setBanner_Url(resultSet.getString("Banner_Url"));
				bb.setImage_name(resultSet.getString("Image_Name"));
				
				banner.add(bb);
			}

		}
		
		return banner;
	}
	
	
	/*
	 * Method searches the ad set to true by the admin
	 * 
	 */
	
	public ArrayList<BannerBean> adchosen() throws SQLException{
		
		ArrayList<BannerBean> banner = new ArrayList<BannerBean>();
		
		String sql = "select * from banners Where Chosen='1'";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn
						.prepareStatement(sql);
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				
				BannerBean bb = new BannerBean();
				
				bb.setBannerId(resultSet.getLong("BannerId"));
				bb.setBanner_Url(resultSet.getString("Banner_Url"));
				bb.setImage_name(resultSet.getString("Image_Name"));
				
				banner.add(bb);
			}

		}
		
		return banner;
	}


	public ArrayList<String> getAll() throws SQLException{
	ArrayList<String> site = new ArrayList<String>();
	ArrayList<BannerBean> banner = new ArrayList<BannerBean>();
	
	String sql = "select * from banners";
	
	try (Connection conn = ds.getConnection();
			PreparedStatement pStatement = conn
					.prepareStatement(sql);
			ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				
				BannerBean bb = new BannerBean();
				
				bb.setBannerId(resultSet.getLong("BannerId"));
				bb.setBanner_Url(resultSet.getString("Banner_Url"));
				bb.setImage_name(resultSet.getString("Image_Name"));
				
				banner.add(bb);
			}
		
		}
		for(BannerBean b : banner){
			site.add(b.getBanner_Url());
		}
		return site;
	}


	
	public String changeAd() throws SQLException{
		
		BannerBean bb1 = new BannerBean();
		String sql = "select * from banners where Chosen='1'";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn
						.prepareStatement(sql);
				ResultSet resultSet = pStatement.executeQuery()) {
				if (resultSet.next()) {
					
					
					
					bb1.setBannerId(resultSet.getLong("BannerId"));
					bb1.setBanner_Url(resultSet.getString("Banner_Url"));
					bb1.setImage_name(resultSet.getString("Image_Name"));
					
				}
			
			}
		
		int resultSet = 0;
		sql = "update banners set chosen='0' Where bannerId=?";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql)) {
			pStatement.setLong(1, bb1.getBannerId());

			resultSet = pStatement.executeUpdate();
			} catch (SQLException e) {
				
			}
		
		if (resultSet == 0) {

			return null;
		}
		
		sql = "update banners set chosen='1' Where banner_Url=?";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql)) {
			pStatement.setString(1, bb.getBanner_Url());

			resultSet = pStatement.executeUpdate();
			} catch (SQLException e) {
				
			}
		
		if (resultSet == 0) {

			return null;
		}
		
		
		
		return "adminBannerManagement";
	}
}
