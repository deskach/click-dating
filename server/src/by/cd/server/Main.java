package by.cd.server;

import by.cd.infra.Application;
import by.cd.infra.ApplicationContext;
import by.cd.server.tcp.TCPServer;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = Application.run("by.cd", new HashMap<>());
    TCPServer server = context.getObject(TCPServer.class);

    server.start();
  }
}
