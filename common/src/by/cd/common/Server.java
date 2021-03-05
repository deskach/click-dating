package by.cd.common;

import lombok.SneakyThrows;

import java.net.ServerSocket;

public interface Server {
  @SneakyThrows
  void start();

  @SneakyThrows
  void listen(ServerSocket socket);
}
