package hbe.example.websocketv1.websocket;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;

@RunWith(MockitoJUnitRunner.class)
public class DeviceWebsocketServerTest {

    @InjectMocks
    private DeviceWebsocketServer websocketServer;
    @Mock
    private Session session;

    @Test
    public void testOpenCloseError(){
        websocketServer.open(session);
        websocketServer.close(session);
    }

    @Test
    public void handleMessageActionWithActions(){
        websocketServer.handleMessage(jsonString("add"), session);
        websocketServer.handleMessage(jsonString("remove"), session);
        websocketServer.handleMessage(jsonString("toggle"), session);
    }

    private String jsonString(String action){
        JSONObject json = new JSONObject();
        json.put("action", action);
        json.put("description", "desc");
        json.put("name", "name");
        json.put("type", "EL");
        json.put("status", "On");
        json.put("id", "0");
        return json.toJSONString();
    }
}
