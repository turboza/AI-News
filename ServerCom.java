import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jgroups.*;
import org.jgroups.util.Util;

public class ServerCom {

	private JChannel clientChannel;
//	private JChannel serverChannel;
	private List<News> news;

	ServerCom() {

		System.setProperty("java.net.preferIPv4Stack", "true");
		news = new ArrayList<News>();

		try {
			clientChannel = new JChannel();
			clientChannel.setName("Server");
			clientChannel.setReceiver(new ReceiverAdapter() {

				public void viewAccepted(View new_view) {

					System.out.println("New host joined: " + new_view);

				}

				public void receive(Message msg) {
					if (msg.getObject() instanceof NewsMessage) {

						NewsMessage newsMessage = (NewsMessage) msg.getObject();
						if (newsMessage.getType() == 0) {// && msg.getSrc() is
															// authorize
							synchronized (news) {
								news.add(newsMessage.getNews());
							}
							send(newsMessage.getNews());
							System.out.println("Received");
						}
					} else if (msg.getObject() instanceof Filter) {
						Filter filter = (Filter) msg.getObject();
						Message msg2;
						if (filter.getWord() == "mana") {
							msg2 = new Message(null, null, news);
						} else {
							List<News> tmp = new ArrayList<News>();
							tmp.add(new News());
							msg2 = new Message(null, null, tmp);
						}

						try {
							clientChannel.send(msg2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			});

//			System.setProperty("jgroups.udp.mcast_port", "12345");
//			serverChannel = new JChannel();
//			serverChannel.setName("Server");
//			serverChannel.setReceiver(new ReceiverAdapter() {
//
//				public void viewAccepted(View new_view) {
//					System.out.println("New host joined: " + new_view);
//				}
//
//				public void receive(Message msg) {
//
//					if (msg.getObject() instanceof Filter) {
//						Filter filter = (Filter) msg.getObject();
//						Message msg2;
//						if (filter.getWord() == "mana") {
//							msg2 = new Message(null, null, news);
//						} else {
//							List<News> tmp = new ArrayList<News>();
//							tmp.add(new News());
//							msg2 = new Message(null, null, tmp);
//						}
//
//						try {
//							clientChannel.send(msg2);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//
//				}
//
//				public void getState(OutputStream output) {
//
//					synchronized (news) {
//						try {
//							Util.objectToStream(news, new DataOutputStream(
//									output));
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//
//				}
//
//				@SuppressWarnings("unchecked")
//				public void setState(InputStream input) {
//
//					List<News> list;
//
//					try {
//						list = (List<News>) Util
//								.objectFromStream(new DataInputStream(input));
//						synchronized (news) {
//							news.clear();
//							news.addAll(list);
//						}
//
//						System.out.println(list.size() + " news in history");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//				}
//			});

			clientChannel.connect("News");
//			serverChannel.connect("NewsServer", null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		clientChannel.close();
//		serverChannel.close();
	}

	public void send(News news) {

		Message msg = new Message(null, null, new NewsMessage(1, news));
		try {
			clientChannel.send(msg);
			System.out.println("News Sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new ServerCom();
	}

}