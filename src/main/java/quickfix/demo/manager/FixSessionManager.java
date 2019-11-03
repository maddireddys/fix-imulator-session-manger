package quickfix.demo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.Dictionary;
import quickfix.FileStoreFactory;
import quickfix.MessageFactory;
import quickfix.ScreenLogFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.demo.api.FixSession;
import quickfix.demo.application.DemoApplication;
import quickfix.field.BeginString;
import quickfix.field.SenderCompID;
import quickfix.field.TargetCompID;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixSessionManager {

  @Autowired
  private Application application = new DemoApplication();

  private Map<SessionID, SocketInitiator> connectionCache = new ConcurrentHashMap<>();

  public FixSession addSession(FixSession session) {
    ((DemoApplication)application).addSession(session, createSessionId(session));
    return session;
  }

  public FixSession startSession(FixSession session) throws ConfigError {
    SessionSettings settings = new SessionSettings();
    SessionID sessionId = createSessionId(session);
    settings.set(sessionId, createDictionary(session));
    settings.setLong("HeartBtInt", session.getHeartBtInt());
    settings.setLong("SocketConnectPort", session.getSocketConnectPort());
    settings.setString("SocketConnectHost", session.getSocketConnectHost());
    FileStoreFactory storeFactory = new FileStoreFactory(settings);
    MessageFactory messageFactory = new DefaultMessageFactory();
    SocketInitiator initiator = new SocketInitiator(application, storeFactory, settings, messageFactory);
    initiator.start();
    connectionCache.put(sessionId, initiator);
    return session;
  }

  public FixSession stopSession(FixSession session) throws ConfigError {
    SessionID sessionId = createSessionId(session);
    SocketInitiator initiator = connectionCache.get(sessionId);
    if(initiator != null) {
      initiator.stop(true);
    }
    return session;
  }

  public Collection<FixSession> listSession() {
    return ((DemoApplication)application).getSessions();
  }


  private SessionID createSessionId(FixSession session) {
    // BeginString : FIX.4.2
    // SenderCompID : demo
    //TargetCompID : FixImulator
    SessionID id = new SessionID(new BeginString(session.getBeginString()),
        new SenderCompID(session.getSenderCompId()),
        new TargetCompID(session.getTargetCompId()));
    return id;
  }

  private Dictionary createDictionary(FixSession session) {
    Dictionary d = new Dictionary();

    d.setString("ConnectionType", "initiator");
    d.setLong("SocketConnectPort", session.getSocketConnectPort());
    d.setString("SocketConnectHost", session.getSocketConnectHost());
    d.setString("FileLogPath", "Log");
    d.setString("StartTime", session.getStartTime());
    d.setString("EndTime", session.getEndTime());
    d.setString("FileStorePath", "/Users/srinivas/Documents/");

//    d.setBool("UseDataDictionary", true);
//    d.setString("DataDictionary", "FIX42.xml"); // custom dictionary
    return d;
  }

  public static void main(String[] args) throws ConfigError {
    FixSession session = new FixSession();
    session.setBeginString("FIX.4.2");
    session.setEndTime("23:59:00");
    session.setHeartBtInt(20);
    session.setSenderCompId("BANZAI");
    session.setSocketConnectHost("localhost");
    session.setSocketConnectPort(9878);
    session.setStartTime("00:00:00");
    session.setTargetCompId("FIXIMULATOR");
    FixSessionManager manager = new FixSessionManager();
    manager.addSession(session);
    manager.startSession(session);
    while (true){}
  }

}

