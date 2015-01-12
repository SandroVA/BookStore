/**
 * 
 */
package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author Svetlana Shopova
 * @since 01.03.2014
 * @version 1.0
 *
 */
@Named("publisherBean")
@SessionScoped
public class PublisherBean implements Serializable{

	
	private static final long serialVersionUID = 2267578170595101477L;

	private long publisherId;
	private String publisherName;
	
	public PublisherBean() {
		super();
		publisherId = 0l;
		publisherName = "";
	}

	public long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		
		
		
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		if(publisherName.contains("\'"))
		{
			publisherName.replace("\'", "\\'");
		}
		this.publisherName = publisherName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (publisherId ^ (publisherId >>> 32));
		result = prime * result
				+ ((publisherName == null) ? 0 : publisherName.hashCode());
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
		PublisherBean other = (PublisherBean) obj;
		if (publisherId != other.publisherId)
			return false;
		if (publisherName == null) {
			if (other.publisherName != null)
				return false;
		} else if (!publisherName.equals(other.publisherName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PublisherBean [publisherId=" + publisherId + ", publisherName="
				+ publisherName + "]";
	}
	
	
	
}
