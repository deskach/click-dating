package by.cd.server.tcp;

import by.cd.common.Server;
import by.cd.common.tcp.TCPConnection;
import by.cd.infra.ApplicationContext;
import by.cd.infra.InjectByType;
import by.cd.infra.InjectProperty;
import by.cd.infra.Singleton;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.ServerSocket;

@Singleton
public class TCPServer implements Server {
  @InjectProperty("server.port")
  private Integer port;

  @InjectByType
  private ApplicationContext context;

  @Override
  @SneakyThrows
  public void start() {
    try (ServerSocket socket = new ServerSocket(this.port)) {
      String hostName = InetAddress.getLocalHost().getHostName();

      System.out.printf("Server started on %s:%s\n", hostName, port);
      listen(socket);
    }
  }

  @Override
  @SneakyThrows
  public void listen(ServerSocket socket) {
    context.getObject(TCPConnection.class).accept(socket.accept());
  }
}
