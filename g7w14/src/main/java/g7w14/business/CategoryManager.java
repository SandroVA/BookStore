package g7w14.business;

import g7w14.data.BookBean;
import g7w14.data.CategoryBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;

/**
 * Category action bean that retrieves data from the category table
 * @author Ian Ozturk
 * Updated by Joseph He
 */

@Named
@SessionScoped
public class CategoryManager implements Serializable {

	
	private static final long serialVersionUID = 5626349912902042719L;

	@Inject
	private CategoryBean cb;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;
	@Inject
	private BookBean book;
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());

	/**
	 * Gets the names of all categories in the category table
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return categoryNames Array list of all category names
	 * @throws SQLException
	 */
	public ArrayList<CategoryBean> getCategoryNames() throws SQLException {

		ArrayList<CategoryBean> categoryNames = new ArrayList<CategoryBean>();

		String sql = "select * from category";

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					CategoryBean bean = new CategoryBean();

					bean.setName(resultSet.getString("Category_Name"));
					bean.setId(resultSet.getLong("CategoryId"));

					categoryNames.add(bean);
				}
			}

			return categoryNames;
		}
	}

	/**
	 * Gets the names of all categories in the category table
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return categoryNames Array list of all category names
	 * @throws SQLException
	 */
	public ArrayList<String> getNames() throws SQLException {

		ArrayList<String> categoryNames = new ArrayList<String>();

		String sql = "select * from category";
		categoryNames.add("");
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					CategoryBean bean = new CategoryBean();

					bean.setName(resultSet.getString("Category_Name"));

					categoryNames.add(bean.getName());
				}
			}

			return categoryNames;

		}

	}

	/**
	 * Gets the name of a category based on the id
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param id
	 * @return categoryName Array list containing the category name that corresponds to the sent in id
	 * @throws SQLException
	 */
	public ArrayList<CategoryBean> getCategoryNameById(long id)
			throws SQLException {

		ArrayList<CategoryBean> categoryName = new ArrayList<CategoryBean>();

		String sql = "select Category_Name from category where CategoryId = ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setLong(1, id);
			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					CategoryBean bean = new CategoryBean();

					bean.setName(resultSet.getString("Category_Name"));
					
					categoryName.add(bean);
				}
			}

			return categoryName;

		}

	}

	/**
	 * This method insert into table category a new category
	 * 
	 * @author Svetlana Shopova
	 * @return redirection to adminInsertPublisher
	 */
	public String saveCategory() {
		String sql = "Insert into category(Category_Name) values(?)";
		int result = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql)) {
			pStatement.setString(1, cb.getName().trim());

			result = pStatement.executeUpdate();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("duplicatedCategories")));
			log.error(e.getMessage());
		}

		if (result == 0) {

			return null;
		}
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(strings.getString("insertedCategory")));
		
		return null;
	}// end of saveCategory

	/**
	 * This method find the id of 
	 * a category by its name
	 * @author Svetlana Shopova
	 * @return id of the category
	 */
	public long getCategoryIdByName() {
		ArrayList<CategoryBean> categoryName = new ArrayList<CategoryBean>();
	
		String sql = "select CategoryId from category where Category_Name = ?";

		System.out.println(cb.getName());
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setString(1, cb.getName());
			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					CategoryBean bean = new CategoryBean();

					bean.setId(resultSet.getLong("CategoryId"));

					categoryName.add(bean);
				}
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		
		return categoryName.get(0).getId();
	}//end getCategoryIdByName()
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String setBookId()
	{
		if(cb.getName()==null || cb.getName().trim().equals(""))
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("insertCategoryFail")));
			return null;
		}
		
		book.setCategoryId(getCategoryIdByName());
		
		return "adminInsertPublisher";
	}//end of setBookId()
}// end of CategoryManager class
