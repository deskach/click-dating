package by.cd.common;

import by.cd.infra.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ConnectionPool {
  private final List<Connection> connections = new ArrayList<>();

  public void addConnection(Connection conn) {
    connections.add(conn);
  }

  public void removeConnection(Connection conn) {
    connections.remove(conn);
  }

  public void sendToAllConnections(Object o) {
    connections.forEach(c -> c.sendObject(o));
  }
}
