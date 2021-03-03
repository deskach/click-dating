package by.cd.client;

import by.cd.infra.Application;
import by.cd.infra.ApplicationContext;

import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = Application.run("by.cd", new HashMap<>());
    Client client = context.getObject(Client.class);
  }
}
