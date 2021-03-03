package by.cd.client;

import lombok.SneakyThrows;

public class Client implements AutoCloseable {
  @Override
  @SneakyThrows
  public void close() {
    System.out.println("Client is closed");
  }
}
