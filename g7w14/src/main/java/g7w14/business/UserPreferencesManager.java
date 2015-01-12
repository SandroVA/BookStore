package g7w14.business;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import g7w14.data.BookBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This bean keeps track of user preferences
 * 
 * @author Sandro Victoria Arena
 * 
 */

@Named("userPreferencesManager")
@SessionScoped
public class UserPreferencesManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1342L;
	long categoryId = -1L;
	long bookId = -1;
	boolean firstTimeCalled = true;

	@Inject
	BookManager manager;
	
	private ArrayList<BookBean> data;
	
	public String setCategoryId(long categoryId) {
		this.categoryId = categoryId;
		return "";
	}
	
	public String setBookId (long bookId) {
		this.bookId = bookId;
		return "";
	}
	
	public long getBookId () {
		return bookId;
	}
	
	public String setFirstTimeCalled() {
		this.firstTimeCalled = false;
		return "";
	}
	
	public boolean isFirstTimeCalled() {
		return firstTimeCalled;
	}
	
	/**
	 * Retrieves the 5 latest books in the book table by category
	 * @author Sandro Victoria Arena
	 * @return data List of all books
	 */
	public ArrayList<BookBean> retrieveLatestBooksByCategory() {
		data = manager.retrieveLatestBooksByCategory(categoryId, bookId);
		
		return data;
	}
	
	/**
	 * @author Sandro Victoria Arena 
	 * @return
	 */
	public String redirectHome() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("userHome.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
