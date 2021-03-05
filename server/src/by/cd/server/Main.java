package by.cd.server;

import by.cd.common.Server;
import by.cd.infra.Application;
import by.cd.infra.ApplicationContext;
import by.cd.server.tcp.TCPServer;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = Application.run("by.cd", new HashMap<>());
    Server server = context.getObject(TCPServer.class);

    server.start();
  }
}
