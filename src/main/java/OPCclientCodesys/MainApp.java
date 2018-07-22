package OPCclientCodesys;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.AutoReconnectController;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;
import utgard.BaseConfiguration;

/**
 * A Camel Application
 */
public class MainApp {

    private static final long SLEEP = 1000;
    private static final int PERIOD = 10000;


    public static void main(String... args) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException, AddFailedException, InterruptedException  {
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


