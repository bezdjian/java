package hbe.example.websocketv1.websocket;

import hbe.example.websocketv1.model.Device;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebsocketServer {

    //@Inject
    private DeviceSessionHandler sessionHandler = new DeviceSessionHandler();

    @OnOpen
    public void open(Session session) {
        System.out.println("Session: " + session.getId());
        System.out.println("SessionHandler: " + sessionHandler.toString());
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebsocketServer.class.getName()).log(Level.SEVERE, "----- onError: " + error.getMessage(), error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try{
            //JsonReader jsonReader = new Json.createReader(new StringReader(message));
            //JsonObject jsonMessage = jsonReader.readObject();

            JSONObject jsonMessage = (JSONObject) new JSONParser().parse(message);
            System.out.println("----- handleMessage -> jsonMessage: " + message);


            if ("add".equals(jsonMessage.get("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.get("name").toString());
                device.setDescription(jsonMessage.get("description").toString());
                device.setType(jsonMessage.get("type").toString());
                device.setStatus("Off");
                sessionHandler.addDevice(device);
            }

            if ("remove".equals(jsonMessage.get("action"))) {
                String id = jsonMessage.get("id").toString();
                sessionHandler.removeDevice(Integer.parseInt(id));
            }

            if ("toggle".equals(jsonMessage.get("action"))) {
                String id = jsonMessage.get("id").toString();
                sessionHandler.toggleDevice(Integer.parseInt(id));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
