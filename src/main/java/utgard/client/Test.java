package utgard.client;


import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import utgard.BaseConfiguration;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;


public class Test {

	private static final long SLEEP = 1000;
	private static final int PERIOD = 10000;

	public Test() {

	}

	public static void main(String[] args) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException, AddFailedException, InterruptedException {
		// create connection information
		// if ProgId is not working, try it using the Clsid instead
		ConnectionInformation ci =  BaseConfiguration.getCLSIDConnectionInfomation();
		
		// create a new server
		Server server = new Server(ci,
				Executors.newSingleThreadScheduledExecutor());

		AutoReconnectController controller = new AutoReconnectController(server);
		
		// aoto reconnect to server
		controller.connect();

		// add sync access, poll every PERIOD ms
		AccessBase access = new SyncAccess(server, PERIOD);

		access.addItem("Random.Real4", new DataCallback() {
			private int i;

			@Override
			public void changed(Item item, ItemState itemstate) {
				System.out.println((++i) + ",ItemName:"+ item.getId()
						+ ",value:" + itemstate.getValue());
			}
		});

		// start reading
		access.bind();
		
		// wait a little bit
		Thread.sleep(SLEEP);
		
		// stop reading
		access.unbind();
		
		// disconnect from server
		controller.disconnect();
	}

}
