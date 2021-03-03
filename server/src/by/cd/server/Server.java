package by.cd.server;

import by.cd.infra.InjectProperty;

public class Server {
  @InjectProperty("server.port")
  private Integer port;

  public void start() {
    //TODO: implement me
    System.out.println("Server started on port " + port);
  }
}
