package by.cd.server.tcp;

import by.cd.infra.InjectByType;
import by.cd.server.Connection;
import by.cd.server.ConnectionListener;

public class TCPConnectionListener implements ConnectionListener {
  @InjectByType
  private TCPServer server;

  @Override
  public void onConnectionReady(Connection conn) {
    this.server.addConnection(conn);
  }

  @Override
  public <T extends Object> void onReceiveObject(Connection conn, T t) {
    // TODO: implement me
  }

  @Override
  public void onDisconnect(Connection conn) {
    server.removeConnection(conn);
    System.out.println("Клиент отключился: " + conn.toString());
  }
}
