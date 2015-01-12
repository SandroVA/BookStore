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
 */
@Named
@SessionScoped
public class FormatBean implements Serializable{
	

	
	private static final long serialVersionUID = 5920604707965562568L;

	private long formatId;
	private String formatName;
	
	/**
	 * 
	 */
	public FormatBean() {
		formatId=0l;
		formatName = "";
	}

	public long getFormatId() {
		return formatId;
	}

	public void setFormatId(long formatId) {
		this.formatId = formatId;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (formatId ^ (formatId >>> 32));
		result = prime * result
				+ ((formatName == null) ? 0 : formatName.hashCode());
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
		FormatBean other = (FormatBean) obj;
		if (formatId != other.formatId)
			return false;
		if (formatName == null) {
			if (other.formatName != null)
				return false;
		} else if (!formatName.equals(other.formatName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FormatBean [formatId=" + formatId + ", formatName="
				+ formatName + "]";
	}
	
	

}
