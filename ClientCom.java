import java.util.List;

import org.jgroups.*;

public class ClientCom {

	private JChannel channel;
	private ClientInterface core;

	ClientCom(ClientInterface _core) {

		System.setProperty("java.net.preferIPv4Stack", "true");
		core = _core;

		try {
			channel = new JChannel();
			channel.setName("Client");
			channel.setReceiver(new ReceiverAdapter() {

				@SuppressWarnings("unchecked")
				public void receive(Message msg) {

					if (msg.getObject() instanceof NewsMessage) {
						if (((NewsMessage) msg.getObject()).getType() == 1) {
							core.store(((NewsMessage) msg.getObject())
									.getNews());
							System.out.println("Stored");
						}
					} else if (msg.getObject() instanceof List<?>) {
						core.store((List<News>) msg.getObject());
					}

				}
			});
			channel.connect("News");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		channel.close();
	}

	public void request(Filter filter) {
		Message msg = new Message(null, null, filter);

		try {
			channel.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(News news) {

		Message msg = new Message(null, null, new NewsMessage(0, news));
		try {
			channel.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}