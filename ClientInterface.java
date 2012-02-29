import java.util.List;

public interface ClientInterface {
	public int getStatus();

	public void getNews();

	public void writeNews(News news);

	public void store(List<News> newsList);

	public void store(News news);
}