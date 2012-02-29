import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClientCore implements ClientInterface {
	
	List<News> buffer;
	Filter filter;
	ClientCom clientCom;
	int status;
	
	ClientCore(){
		filter = new Filter("mana");
		buffer = new ArrayList<News>();
		clientCom = new ClientCom(this);
		status = 0;
		getNews();
	}
	
	@Override
	public int getStatus() {
		/*
		 * 0 : just start no news in buffer
		 * 1 : requesting buffer is not ready
		 * 2 : ready
		 */
		return status;
	}

	@Override
	public void getNews() {
		clientCom.request(filter);
		status = 1;
	}
	
	public void store(List<News> newsList){
		buffer = newsList;
		status = 2;
	}
	
	public void store(News news){
		if(news.isIn(filter))
		{
			if(status==2)
				buffer.add(news);
			else
				clientCom.request(filter);
		}
	}

	@Override
	public void writeNews(News news) {
		clientCom.write(news);
	}
	
	public void setFilter(Date begin,Date end, String word){
		filter = new Filter(begin,end,word);
	}
	
	public static void main(String args[])
	{
		int old=0;
		ClientCore cc = new ClientCore();
		while(true){
			if(cc.status!=old){
				System.out.println("status : "+cc.getStatus());
				old = cc.status;
			}
			if(cc.getStatus()==2){
				System.out.println("FUVK YOUUUUU   Title: "+cc.buffer.get(0).getTitle());
				System.out.println("FUVK YOUUUUU   IP: "+cc.buffer.get(0).getIp());
				break;
			}
		}
		
		cc.writeNews(new News("Mana Grean Mak"));
		System.out.println("status : "+cc.getStatus());
		while(true){
			if(cc.status!=old){
				System.out.println("status : "+cc.getStatus());
				old = cc.status;
			}
			if(cc.buffer.size()>1){
				System.out.println("FUVK YOUUUUU   Title: "+cc.buffer.get(1).getTitle());
				System.out.println("FUVK YOUUUUU   IP: "+cc.buffer.get(1).getDate());
				break;
			}
		}
		
		cc.clientCom.close();
	}
}