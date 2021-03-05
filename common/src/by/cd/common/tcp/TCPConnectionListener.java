package by.cd.common.tcp;

import by.cd.common.Connection;
import by.cd.common.ConnectionListener;
import by.cd.common.ConnectionPool;
import by.cd.infra.InjectByType;

public class TCPConnectionListener implements ConnectionListener {
  @InjectByType
  private ConnectionPool connections;

  @Override
  public void onConnectionReady(Connection conn) {
    this.connections.addConnection(conn);
    System.out.println("Connected: " + conn.toString());
  }

  @Override
  public <T extends Object> void onReceiveObject(Connection conn, T t) {
    // TODO: implement me
  }

  @Override
  public void onDisconnect(Connection conn) {
    connections.removeConnection(conn);
    System.out.println("Disconnected: " + conn.toString());
  }
}
