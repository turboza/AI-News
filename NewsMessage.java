import java.io.Serializable;

public class NewsMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7702549940820599968L;
	private int type; // post=0,retpost=1,req=2,retreq=3
	private News news;

	NewsMessage() {
		type = 0;
		news = new News();
	}

	NewsMessage(News _news) {
		type = 0;
		news = _news;
	}

	NewsMessage(int _type) {
		type = _type;
		news = new News();
	}

	NewsMessage(int _type, News _news) {
		type = _type;
		news = _news;
	}

	public int getType() {
		return type;
	}

	public News getNews() {
		return news;
	}
}