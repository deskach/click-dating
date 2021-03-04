package by.cd.server;

import java.net.Socket;

public interface Connection extends Consumer<Socket> {
  void sendObject(Object object);

  @Override
  void accept(Socket t);

  void disconnect();

  @Override
  String toString();
}
