package by.cd.client.tcp;

import by.cd.common.tcp.TCPConnection;
import by.cd.infra.ApplicationContext;
import by.cd.infra.InjectByType;
import by.cd.infra.InjectProperty;
import lombok.SneakyThrows;

import java.net.Socket;

public class TCPClient implements AutoCloseable {
  @InjectProperty("server.port")
  private Integer port;

  @InjectProperty("server.ip")
  private String serverIp;

  @InjectByType
  private ApplicationContext context;

  @SneakyThrows
  public void connect() {
    context.getObject(TCPConnection.class).accept(new Socket(serverIp, port));
  }

  @Override
  @SneakyThrows
  public void close() {
    System.out.println("Client is closed");
  }
}
