/**
 * 
 */
package g7w14.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;

/**
 * @author Svetlana Shopova
 * @since 04.03.2014
 * @version 1.0
 */



@Named
@RequestScoped
public class CalendarBean implements Serializable{

	
	private static final long serialVersionUID = 2475298326412013908L;
	
	private Date startSelectedDate;
	private Date endSelectedDate;
	private ResourceBundle strings = ResourceBundle.getBundle("validation_messages",
			Locale.getDefault());
	String str;
	public CalendarBean() {
		super();
		startSelectedDate = new Date();
		endSelectedDate = new Date();
		str=strings.getString("dateValidation");
	}
	public Date getStartSelectedDate() {
		return startSelectedDate;
	}
	public void setStartSelectedDate(Date startSelected) {
		this.startSelectedDate = startSelected;
	}
	public Date getEndSelectedDate() {
		return endSelectedDate;
	}
	public void setEndSelectedDate(Date endSelected) {
		this.endSelectedDate = endSelected;
	}
	
	
	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}
	
	@AssertTrue(message="The end of the period is earlier than the beginning")
	public boolean isDateValid() {
			boolean valid = false;
			if(endSelectedDate.after(startSelectedDate)|| endSelectedDate.compareTo(startSelectedDate)==0)
			{
				valid = true;
			}

	     return valid;

	}

}
