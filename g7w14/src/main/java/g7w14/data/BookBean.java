package g7w14.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This class represent Book object from Book table
 * 
 * @author Sandro Victoria Arena, Ian Ozturk, Martin Nafekh
 */
@Named("bookBean")
@SessionScoped
public class BookBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long bookId;
	private String isbn;
	private String title;
	private long publisherId;
	private int numberCopies;
	private BigDecimal wholesalePrice;
	private BigDecimal listPrice;
	private BigDecimal salePrice;
	private long categoryId;
	private long formatId;
	private int weight;
	private String dimensions;
	private boolean removalStatus;
	private Timestamp dateAdded;
	private int numberPages;
	private String image;
	private boolean editable;

	public BookBean() {
		super();
		categoryId = 0;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

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

		if (title.contains("\'")) {
			title.replace("\'", "\\'");
		}

		this.title = title;
	}

	public long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(long publisherId2) {
		this.publisherId = publisherId2;
	}

	public int getNumberCopies() {
		return numberCopies;
	}

	public void setNumberCopies(int numberCopies) {
		this.numberCopies = numberCopies;
	}

	public BigDecimal getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(BigDecimal wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId2) {
		this.categoryId = categoryId2;
	}

	public long getFormatId() {
		return formatId;
	}

	public void setFormatId(long formatId) {
		this.formatId = formatId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public boolean isRemovalStatus() {
		return removalStatus;
	}

	public void setRemovalStatus(boolean removalStatus) {
		this.removalStatus = removalStatus;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public int getNumberPages() {
		return numberPages;
	}

	public void setNumberPages(int numberPages) {
		this.numberPages = numberPages;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result + (int) (categoryId ^ (categoryId >>> 32));
		result = prime * result
				+ ((dateAdded == null) ? 0 : dateAdded.hashCode());
		result = prime * result
				+ ((dimensions == null) ? 0 : dimensions.hashCode());
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result + (int) (formatId ^ (formatId >>> 32));
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result
				+ ((listPrice == null) ? 0 : listPrice.hashCode());
		result = prime * result + numberCopies;
		result = prime * result + numberPages;
		result = prime * result + (int) (publisherId ^ (publisherId >>> 32));
		result = prime * result + (removalStatus ? 1231 : 1237);
		result = prime * result
				+ ((salePrice == null) ? 0 : salePrice.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + weight;
		result = prime * result
				+ ((wholesalePrice == null) ? 0 : wholesalePrice.hashCode());
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
		BookBean other = (BookBean) obj;
		if (bookId != other.bookId)
			return false;
		if (categoryId != other.categoryId)
			return false;
		if (dateAdded == null) {
			if (other.dateAdded != null)
				return false;
		} else if (!dateAdded.equals(other.dateAdded))
			return false;
		if (dimensions == null) {
			if (other.dimensions != null)
				return false;
		} else if (!dimensions.equals(other.dimensions))
			return false;
		if (editable != other.editable)
			return false;
		if (formatId != other.formatId)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (listPrice == null) {
			if (other.listPrice != null)
				return false;
		} else if (!listPrice.equals(other.listPrice))
			return false;
		if (numberCopies != other.numberCopies)
			return false;
		if (numberPages != other.numberPages)
			return false;
		if (publisherId != other.publisherId)
			return false;
		if (removalStatus != other.removalStatus)
			return false;
		if (salePrice == null) {
			if (other.salePrice != null)
				return false;
		} else if (!salePrice.equals(other.salePrice))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (weight != other.weight)
			return false;
		if (wholesalePrice == null) {
			if (other.wholesalePrice != null)
				return false;
		} else if (!wholesalePrice.equals(other.wholesalePrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookBean [bookId=" + bookId + ", isbn=" + isbn + ", title="
				+ title + ", publisherId=" + publisherId + ", numberCopies="
				+ numberCopies + ", wholesalePrice=" + wholesalePrice
				+ ", listPrice=" + listPrice + ", salePrice=" + salePrice
				+ ", categoryId=" + categoryId + ", formatId=" + formatId
				+ ", weight=" + weight + ", dimensions=" + dimensions
				+ ", removalStatus=" + removalStatus + ", dateAdded="
				+ dateAdded + ", numberPages=" + numberPages + ", image="
				+ image + ", editable=" + editable + "]";
	}

}
