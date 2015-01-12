package g7w14.data;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 * This class represent Review object from Review table
 * @author Sandro Victoria Arena
 */
@Named("reviewBean")
@RequestScoped
public class ReviewBean implements Serializable{

    private static final long serialVersionUID = 161561516L;
	
	private Timestamp review_Date;
    private long customerId;
    private int rating;
    private String reviewText;
    private boolean approved;
    private long bookId;
    private long reviewId;
    private boolean editable;
    
    public ReviewBean() {
        super();
        customerId = -1;
        rating = -1;
        reviewText = "";
        approved = false;
        bookId = -1;
    }   

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public Timestamp getReview_Date() {
		return review_Date;
	}

	public void setReview_Date(Timestamp review_Date) {
		this.review_Date = review_Date;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId2) {
		this.customerId = customerId2;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId2) {
		this.bookId = bookId2;
	}

   

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (editable ? 1231 : 1237);
		result = prime * result + rating;
		result = prime * result + (int) (reviewId ^ (reviewId >>> 32));
		result = prime * result
				+ ((review_Date == null) ? 0 : review_Date.hashCode());
		result = prime * result
				+ ((reviewText == null) ? 0 : reviewText.hashCode());
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
		ReviewBean other = (ReviewBean) obj;
		if (approved != other.approved)
			return false;
		if (bookId != other.bookId)
			return false;
		if (customerId != other.customerId)
			return false;
		if (editable != other.editable)
			return false;
		if (rating != other.rating)
			return false;
		if (reviewId != other.reviewId)
			return false;
		if (review_Date == null) {
			if (other.review_Date != null)
				return false;
		} else if (!review_Date.equals(other.review_Date))
			return false;
		if (reviewText == null) {
			if (other.reviewText != null)
				return false;
		} else if (!reviewText.equals(other.reviewText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReviewBean [review_Date=" + review_Date + ", customerId="
				+ customerId + ", rating=" + rating + ", reviewText="
				+ reviewText + ", approved=" + approved
				+ ", bookId=" + bookId + ", reviewId=" + reviewId
				+ ", editable=" + editable + "]";
	}
	
	
}
