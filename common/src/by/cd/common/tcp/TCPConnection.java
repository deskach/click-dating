package by.cd.common.tcp;

import by.cd.infra.InjectByType;
import by.cd.common.Connection;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPConnection implements Connection {
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private Socket socket;
  private Thread thread;

  @InjectByType
  TCPConnectionListener listener;

  @Override
  @SneakyThrows
  public void accept(Socket socket) {
    this.socket = socket;
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.in = new ObjectInputStream(socket.getInputStream());
    this.thread = new Thread(new Runnable() {
      @Override
      @SneakyThrows
      public void run() {
        try {
          listener.onConnectionReady(TCPConnection.this);
          while (!thread.isInterrupted()) {
            listener.onReceiveObject(TCPConnection.this, in.readObject());
          }
        } catch (Exception e) {
          listener.onException(TCPConnection.this, e);
        } finally {
          listener.onDisconnect(TCPConnection.this);
        }
      }
    });
    thread.start();
  }

  @Override
  @SneakyThrows
  public synchronized void sendObject(Object object) {
    try {
      out.writeObject(object);
      // out.flush();
    } catch (IOException e) {
      disconnect();
      listener.onException(TCPConnection.this, e);
    }
  }

  @Override
  @SneakyThrows
  public synchronized void disconnect() {
    thread.interrupt();
    try {
      socket.close();
    } catch (IOException e) {
      listener.onException(TCPConnection.this, e);
    }
  }

  @Override
  public String toString() {
    return String.format("TCPConnection: %s:%d", socket.getLocalAddress(), socket.getPort());
  }
}
