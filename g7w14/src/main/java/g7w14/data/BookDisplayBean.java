package g7w14.data;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Associates a book bean with an array list of authors
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@Named
@RequestScoped
public class BookDisplayBean implements Serializable {

	private static final long serialVersionUID = -1659564124020185061L;

	private BookBean book;
	private ArrayList<AuthorBean> authors;

	public BookDisplayBean() {
		super();
		book = new BookBean();
		authors = new ArrayList<AuthorBean>();
	}

	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}

	public ArrayList<AuthorBean> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<AuthorBean> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "BookDisplayBean [book=" + book + ", authors=" + authors + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDisplayBean other = (BookDisplayBean) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		return true;
	}

}
