package g7w14.data;



import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This class represent user object from  User table
 * @author Svetlana Shopova
 * @since 03.02.2014
 * @version 1
 */
@Named
@SessionScoped
public class UserBean implements Serializable {
	
	
	private static final long serialVersionUID = 456874565476546L;
	private String username;
	private String password;
	private long userId;
	private long UserTypeId;
	private boolean editable;
		
	public UserBean() {
		super();
		UserTypeId=1l;
		//username="";
		//password="";
		editable=false;
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getUserTypeId() {
		return UserTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		UserTypeId = userTypeId;
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
		result = prime * result + (int) (UserTypeId ^ (UserTypeId >>> 32));
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		UserBean other = (UserBean) obj;
		if (UserTypeId != other.UserTypeId)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId != other.userId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
		
	}
	@Override
	public String toString() {
		return "UserBean [username=" + username + ", password=" + password
				+ ", userId=" + userId + ", UserTypeId=" + UserTypeId
				+ ", editable=" + editable + "]";
	}
	
	

	
}
