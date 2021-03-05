package by.cd.common;

public interface ConnectionListener {
  void onConnectionReady(Connection con);

  <T extends Object> void onReceiveObject(Connection con, T t);

  void onDisconnect(Connection con);

  default void onException(Connection con, Exception e) throws Exception {
    throw e;
  }
}
