/**
 * 
 */
package g7w14.data;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import g7w14.data.BookBean;

//import org.apache.commons.io.FileUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;


// Needed for writing file
import com.google.common.io.Files;

/**
 * This code was found at:
 * http://showcase.richfaces.org/richfaces/component-sample.jsf?demo=fileUpload
 * 
 * Code added to write the file to a folder
 * 
 * @author Ilya Shaikovsky
 */
@Named
@SessionScoped
public class FileUploadBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2097119104534914138L;
	private ArrayList<UploadedImage> files = new ArrayList<UploadedImage>();
	@Inject
	private BookBean book;

	/**
	 * Send the image to the browser. Used by the RichFaces a4j:mediaOutput tag.
	 * 
	 * @param stream
	 * @param object
	 * @throws IOException
	 */
	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer) object).getData());
		stream.close();
	}

	/**
	 * This method is called for each file uploaded by rich:fileUpload. It
	 * copies the data from the RichFaces UploadedFile object into an
	 * UploadedImage object. This object is required so that the image can be
	 * displayed in the browser using the stream.write
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void listener(FileUploadEvent event) throws Exception {
		
		UploadedFile rawUploadedFile = event.getUploadedFile();
		UploadedImage uploadedImageFile = new UploadedImage();
		
		uploadedImageFile.setLength(rawUploadedFile.getData().length);
		uploadedImageFile.setName(rawUploadedFile.getName());
		uploadedImageFile.setData(rawUploadedFile.getData());		
		
		book.setImage(uploadedImageFile.getName());
		
		files.add(uploadedImageFile);
		
		// Get the path to this application on the server
		String absoluteWebPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/");
		// Create a File pointing to the folder shown
		File file = new File(absoluteWebPath + "/resources/images/"
				+ uploadedImageFile.getName());
		// Using the Google guava commons.io library 
		Files.write(uploadedImageFile.getData(), file);
	}

	public String clearUploadData() {
		files.clear();
		return null;
	}

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<UploadedImage> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UploadedImage> files) {
		this.files = files;
	}
}