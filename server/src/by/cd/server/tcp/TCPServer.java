package by.cd.server.tcp;

import by.cd.infra.ApplicationContext;
import by.cd.infra.InjectByType;
import by.cd.infra.InjectProperty;
import by.cd.infra.Singleton;
import by.cd.server.Connection;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TCPServer {
  @InjectProperty("server.port")
  private Integer port;

  @InjectByType
  private ApplicationContext context;

  private final List<Connection> connections = new ArrayList<>();

  @SneakyThrows
  public void start() {
    try (ServerSocket socket = new ServerSocket(this.port)) {
      String hostName = InetAddress.getLocalHost().getHostName();

      System.out.printf("Server started on %s:%s\n", hostName, port);
      listen(socket);
    }
  }

  @SneakyThrows
  protected void listen(ServerSocket socket) {
    TCPConnection connection = context.getObject(TCPConnection.class);

    connection.accept(socket.accept());
  }

  public void addConnection(Connection conn) {
    connections.add(conn);
  }

  public void removeConnection(Connection conn) {
    connections.remove(conn);
  }
}
