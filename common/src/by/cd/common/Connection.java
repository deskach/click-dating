package by.cd.common;

import java.net.Socket;
import java.util.function.Consumer;

public interface Connection extends Consumer<Socket> {
  void sendObject(Object object);

  @Override
  void accept(Socket t);

  void disconnect();
}
