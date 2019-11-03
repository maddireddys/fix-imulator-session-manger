package quickfix.demo.api;

public class FixSession {
  private String senderCompId;
  private String targetCompId;
  private String beginString;
  private String startTime;
  private String endTime;
  private int heartBtInt;
  private int socketConnectPort;
  private String socketConnectHost;
  private String status;
  private Boolean isLoggedOn = false;
  private Boolean showStart = false;
  private Boolean showStop = false;

  public String getSenderCompId() {
    return senderCompId;
  }

  public void setSenderCompId(String senderCompId) {
    this.senderCompId = senderCompId;
  }

  public String getTargetCompId() {
    return targetCompId;
  }

  public void setTargetCompId(String targetCompId) {
    this.targetCompId = targetCompId;
  }

  public String getBeginString() {
    return beginString;
  }

  public void setBeginString(String beginString) {
    this.beginString = beginString;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public int getHeartBtInt() {
    return heartBtInt;
  }

  public void setHeartBtInt(int heartBtInt) {
    this.heartBtInt = heartBtInt;
  }

  public int getSocketConnectPort() {
    return socketConnectPort;
  }

  public void setSocketConnectPort(int socketConnectPort) {
    this.socketConnectPort = socketConnectPort;
  }

  public String getSocketConnectHost() {
    return socketConnectHost;
  }

  public void setSocketConnectHost(String socketConnectHost) {
    this.socketConnectHost = socketConnectHost;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Boolean getLoggedOn() {
    return isLoggedOn;
  }

  public void setLoggedOn(Boolean loggedOn) {
    isLoggedOn = loggedOn;
  }

  public Boolean getShowStart() {
    return showStart;
  }

  public void setShowStart(Boolean showStart) {
    this.showStart = showStart;
  }

  public Boolean getShowStop() {
    return showStop;
  }

  public void setShowStop(Boolean showStop) {
    this.showStop = showStop;
  }
}
