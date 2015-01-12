/**
 * 
 */
package g7w14.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This clas converts Date to a string in format "yyyy-MM-dd"
 * @author Svetlana Shopova
 * @since 13.03.2014
 * @version 1.0
 */
public class DateConverter {
	
	/**
	 * This method converts type Date value to String type
	 * @param date - variable from type Date
	 * @return a string that contain a date in format "yyyy-MM-dd"
	 */
	public static String convertDate(Date date)
	{
		String format = "yyyy-MM-dd";	   
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	String stringDate = sdf.format(date );
	   
	   
		return stringDate;
	}

}
