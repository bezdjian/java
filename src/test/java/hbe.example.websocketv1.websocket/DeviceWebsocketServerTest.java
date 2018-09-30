package hbe.example.websocketv1.websocket;

import hbe.example.websocketv1.model.Device;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeviceWebsocketServerTest {

    @InjectMocks
    private DeviceSessionHandler sessionHandler;
    @Mock
    private Session session;
    @Mock
    private JsonProvider provider;
    @Mock
    private JsonObjectBuilder objectBuilder;

    @Before
    public void setup() {
        provider  = JsonProvider.provider();
        MockitoAnnotations.initMocks(this);
        when(provider.createObjectBuilder()).thenReturn(objectBuilder);
        when(provider.createObjectBuilder().build())
                .thenReturn(getJsonObject(deviceList().get(0)));
    }

    @Test
    public void getDevicesTest(){
        when(sessionHandler.getDevices()).thenReturn(deviceList());
        List<Device> d = sessionHandler.getDevices();
        verify(sessionHandler).getDevices();
        assertTrue("Size no right: " + d.size(), d.size() > 0);
    }

    @Test
    public void addDeviceTest(){
        sessionHandler.addDevice(deviceList().get(0));
    }

    private List<Device> deviceList(){
        ArrayList<Device> devices = new ArrayList<>();
        Device d = new Device();
        d.setStatus("On");
        d.setDescription("desc");
        d.setId(1);
        d.setName("name");
        d.setType("El");
        devices.add(d);
        return devices;
    }
    public JsonObject getJsonObject(Device device){
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getStatus())
                .add("description", device.getDescription())
                .build();
        return addMessage;
    }
}
