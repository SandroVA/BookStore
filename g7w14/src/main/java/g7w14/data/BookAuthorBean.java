package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Bean containing a book ID and an associated author ID
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@Named
@RequestScoped
public class BookAuthorBean implements Serializable {

	private static final long serialVersionUID = -7691221401725361787L;

	private long bookId;
	private long authorId;

	public BookAuthorBean() {
		super();
		bookId = 0;
		authorId = 0;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "BookAuthorBean [bookId=" + bookId + ", authorId=" + authorId
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (authorId ^ (authorId >>> 32));
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
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
		BookAuthorBean other = (BookAuthorBean) obj;
		if (authorId != other.authorId)
			return false;
		if (bookId != other.bookId)
			return false;
		return true;
	}

}
