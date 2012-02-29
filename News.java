import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;


@SuppressWarnings("serial")
public class News implements Serializable{
	private String title;
	private String author;
	private Date date;
	private Image image;
	private String body;
	private String ip;
	
	News() {
		InetAddress addr = null;
		try {addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {e.printStackTrace();}
		
		ip = addr.getHostAddress();
		date = new Date(); //get the present time in milliseconds
		author = addr.getHostName();
	}
	News(String title){
		this.title = title;
		InetAddress addr = null;
		try {addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {e.printStackTrace();}
		
		ip = addr.getHostAddress();
		date = new Date(); //get the present time in milliseconds
		author = addr.getHostName();
	}
	
	public void setImagePath(String path)
	{
		image = Toolkit.getDefaultToolkit().getImage(path);
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public Date getDate() {
		return date;
	}
	public Image getImage() {
		return image;
	}
	public String getIp() {
		return ip;
	}
	public boolean isIn(Filter filter) {
		// is the news in the filter 
		return true;
	}
}
