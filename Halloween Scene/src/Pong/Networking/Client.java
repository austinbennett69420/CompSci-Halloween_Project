package Pong.Networking;

import java.io.*;
import java.net.Socket;

public class Client {

    protected Socket sock;
    protected ObjectInputStream packet_in;
    protected ObjectOutputStream packet_out;

    public Client(String ip, int port) throws IOException {

        sock = new Socket(ip, port);


        System.out.println("Connected to server: " + sock.getInetAddress().getHostAddress() + ":" + port);
        packet_out = new ObjectOutputStream(sock.getOutputStream());
        packet_in = new ObjectInputStream(sock.getInputStream());

    };

    public void sendString(String message) throws IOException {
        packet_out.writeObject(message);
        packet_out.flush();
    }

    public String recvString() throws IOException, ClassNotFoundException {
        return (String)packet_in.readObject();
    }

    public void sendPacket(Serializable pack) throws IOException {
        packet_out.writeObject(pack);
        packet_out.flush();
    }

    public Serializable recvPacket() throws IOException, ClassNotFoundException {
        return (Serializable) packet_in.readObject();
    }
}
