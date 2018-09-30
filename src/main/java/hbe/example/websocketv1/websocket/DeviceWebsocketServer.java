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
        sessionHandler.addSession(session);
        log(Level.INFO, "Session added: " + session.getId());
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
        log(Level.INFO, "Session closed: " + session.getId());
    }

    @OnError
    public void onError(Throwable error) {
        log(Level.SEVERE, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try{
            JSONObject jsonMessage = (JSONObject) new JSONParser().parse(message);

            if ("add".equals(jsonMessage.get("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.get("name").toString());
                device.setDescription(jsonMessage.get("description").toString());
                device.setType(jsonMessage.get("type").toString());
                device.setStatus("Off");
                sessionHandler.addDevice(device);
                log(Level.INFO, "Device added: " + device.getName());
            }

            if ("remove".equals(jsonMessage.get("action"))) {
                String id = jsonMessage.get("id").toString();
                Device d = sessionHandler.removeDevice(Integer.parseInt(id));
                log(Level.INFO, "Device removed: " + d.getName());
            }

            if ("toggle".equals(jsonMessage.get("action"))) {
                String id = jsonMessage.get("id").toString();
                Device d = sessionHandler.getDeviceById(Integer.parseInt(id));
                sessionHandler.toggleDevice(Integer.parseInt(id));
                log(Level.INFO, "Device toggled: " + d.getName() +
                        " to status " + d.getStatus());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void log(Level level, Throwable error){
        Logger.getLogger(DeviceWebsocketServer.class.getName()).log(level, "----- onError: " + error.getMessage(), error);
    }
    private void log(Level level, String message){
        Logger.getLogger(DeviceWebsocketServer.class.getName()).log(level, "----- log: " + message);
    }
}
