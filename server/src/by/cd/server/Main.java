package by.cd.server;

import by.cd.infra.Application;
import by.cd.infra.ApplicationContext;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = Application.run("by.cd", new HashMap<>());
    Server server = context.getObject(Server.class);

    server.start();
  }
}
