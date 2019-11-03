package quickfix.demo.application;

import org.springframework.stereotype.Component;
import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.StringField;
import quickfix.UnsupportedMessageType;
import quickfix.demo.api.FixSession;
import quickfix.field.MsgType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DemoApplication implements Application {

  private Map<SessionID, FixSession> cache = new ConcurrentHashMap<>();

  @Override
  public void onCreate(SessionID sessionId) {
    System.out.println("Session created, sessionId:"+sessionId);
    FixSession session = cache.get(sessionId);
    if(session != null) {
      session.setShowStart(false);
      session.setShowStop(false);
      session.setStatus("Connecting...");
    }
  }

  @Override
  public void onLogon(SessionID sessionId) {
    System.out.println("Logged On, sessionId:"+sessionId);
    FixSession session = cache.get(sessionId);
    if(session != null) {
      session.setShowStart(false);
      session.setShowStop(true);
      session.setStatus("Connected");
    }
  }

  @Override
  public void onLogout(SessionID sessionId) {
    System.out.println("Logged Out, sessionId:"+sessionId);
    FixSession session = cache.get(sessionId);
    if(session != null) {
      session.setShowStart(true);
      session.setShowStop(false);
      session.setStatus("Disconnected");
    }
  }

  @Override
  public void toAdmin(Message message, SessionID sessionId) {
    // can handling outgoing(request) message to exchange
    try {
      StringField field = message.getHeader().getField(new MsgType());
      if(field != null && "A".equalsIgnoreCase(field.getValue())) {
        // can add username and password to login message before sending to exchange
      }
    } catch (FieldNotFound fieldNotFound) {
      fieldNotFound.printStackTrace();
    }
  }

  @Override
  public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
    // similarly outgoing(response) messages, can handle incoming messages here...
    try {
      StringField field = message.getHeader().getField(new MsgType());
      if(field != null && "A".equalsIgnoreCase(field.getValue())) {

      } else if(field != null && "0".equalsIgnoreCase(field.getValue())) {
        FixSession session = cache.get(sessionId);
        if(session != null) {
          session.setShowStart(false);
          session.setShowStop(true);
          session.setStatus("Running...");
        }
      }
    } catch (FieldNotFound fieldNotFound) {
      fieldNotFound.printStackTrace();
    }
  }

  @Override
  public void toApp(Message message, SessionID sessionId) throws DoNotSend {
    // here app message request handling(order messages)
    System.out.println("ToApp:"+message +", from sessionId:"+sessionId);
  }

  @Override
  public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
    // here app message response handling(order messages)
  }

  public void addSession(FixSession fixSession, SessionID sessionID) {
    fixSession.setShowStart(true);
    fixSession.setShowStop(false);
    cache.put(sessionID, fixSession);
  }

  public Collection<FixSession> getSessions() {
    return this.cache.values();
  }
}
