package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Bean that represents a row in the reorder report
 * @author Ian Ozturk
 *
 */

@Named("reorderReportBean")
@RequestScoped
public class ReorderReportBean implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String isbn;
    private String title;
    private String authorLast;
    private String authorFirst;
    private String publisher;
    private int numberCopies;
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorLast() {
		return authorLast;
	}
	public void setAuthorLast(String authorLast) {
		this.authorLast = authorLast;
	}
	public String getAuthorFirst() {
		return authorFirst;
	}
	public void setAuthorFirst(String authorFirst) {
		this.authorFirst = authorFirst;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getNumberCopies() {
		return numberCopies;
	}
	public void setNumberCopies(int numberCopies) {
		this.numberCopies = numberCopies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authorFirst == null) ? 0 : authorFirst.hashCode());
		result = prime * result
				+ ((authorLast == null) ? 0 : authorLast.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + numberCopies;
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ReorderReportBean other = (ReorderReportBean) obj;
		if (authorFirst == null) {
			if (other.authorFirst != null)
				return false;
		} else if (!authorFirst.equals(other.authorFirst))
			return false;
		if (authorLast == null) {
			if (other.authorLast != null)
				return false;
		} else if (!authorLast.equals(other.authorLast))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (numberCopies != other.numberCopies)
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReorderReportBean [isbn=" + isbn + ", title=" + title
				+ ", authorLast=" + authorLast + ", authorFirst=" + authorFirst
				+ ", publisher=" + publisher + ", numberCopies=" + numberCopies
				+ "]";
	}
    
	
	
	
	
	
    
    

}
