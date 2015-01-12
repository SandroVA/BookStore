/**
 * Martin Nafekh
 * 1032763
 * SearchBean.java
 */
package g7w14.data;

import g7w14.business.BookManager;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 * 
 * Class that encapsulates the field and query submitted by the search bar
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@ManagedBean
@RequestScoped
public class SearchBean implements Serializable {

	private static final long serialVersionUID = 204670713455362978L;

	@Inject
	BookManager manager;

	private String value;
	private String field;

	public SearchBean() {
		super();
		value = "";
		field = "";
	}

	public String getValue() {
		if (value == null || value.equals(""))
			return "Search our books here ...";
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
