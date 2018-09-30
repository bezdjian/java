package hbe.example.websocketv1.websocket;

import hbe.example.websocketv1.model.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeviceSessionHandlerTest {

    @InjectMocks
    private DeviceSessionHandler sessionHandler;
    @Mock
    private Session session;
    @Mock
    private RemoteEndpoint.Basic basic;

    @Test
    public void getDevicesTest(){
        //Those work when you @Mock sessionHandler, does not work with @InjectMocks :/
        //when(sessionHandler.getDevices()).thenReturn(deviceList());
        List<Device> d = sessionHandler.getDevices();
        //verify(sessionHandler).getDevices();
        assertEquals("Size no right: " + d.size(), 0, d.size());
    }

    @Test
    public void addDeviceTest(){
        when(session.getBasicRemote()).thenReturn(basic);
        //Add with ID 1
        sessionHandler.addDevice(deviceList().get(0));
        //Add session
        sessionHandler.addSession(session);
        //Toggle with the same device ID 1
        sessionHandler.toggleDevice(deviceList().get(0).getId());
        //Change status to On
        deviceList().get(0).setStatus("On");
        //Toggle with the same device ID 1 with status On
        sessionHandler.toggleDevice(deviceList().get(0).getId());
        // Remove with the same device ID 1
        sessionHandler.removeDevice(deviceList().get(0).getId());
    }

    @Test
    public void removeSessionTest(){
        sessionHandler.removeSession(session);
    }

    @Test
    public void sendToSessionExceptionTest() throws IOException {
        //doThrow(new RuntimeException("oh!")).when(session).getBasicRemote().sendText("lala");
        sessionHandler.addDevice(deviceListWithError().get(0));
        sessionHandler.removeDevice(deviceListWithError().get(0).getId());
    }

    private List<Device> deviceList(){
        ArrayList<Device> devices = new ArrayList<>();
        Device d = new Device();
        d.setStatus("On");
        d.setDescription("desc");
        //d.setId(1);
        d.setName("name");
        d.setType("El");
        devices.add(d);
        return devices;
    }
    private List<Device> deviceListWithError(){
        ArrayList<Device> devices = new ArrayList<>();
        Device d = new Device();
        d.setStatus("¡é╣");
        //d.setId(1);
        d.setDescription("desc");
        d.setName("name");
        d.setType("El");
        devices.add(d);
        return devices;
    }
    public JsonObjectBuilder getJsonObject(){
        JsonProvider provider = JsonProvider.provider();
        JsonObjectBuilder addMessage = provider.createObjectBuilder()
                .add("action", "¡é╣")
                .add("id", 0)
                .add("name", "name")
                .add("type", "elz")
                .add("status", "On")
                .add("description", "descz");
        return addMessage;
    }
}
