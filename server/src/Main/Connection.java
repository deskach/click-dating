package Main;

public interface Connection {
  void sendObject(Object object);

  void disconnect();

  @Override
  String toString();
}
