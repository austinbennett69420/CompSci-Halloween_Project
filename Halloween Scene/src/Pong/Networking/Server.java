package Pong.Networking;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    protected ServerSocket sock;

    protected ObjectInputStream packet_in;
    protected ObjectOutputStream packet_out;

    public Server(String ip, int port) throws IOException {

        sock = new ServerSocket(port, 50, InetAddress.getByName(ip));

        System.out.println("Pong.Networking.Server started on ip: " + sock.getInetAddress().getHostAddress()
        + ":" + port);
    };

    public void accept() throws IOException {

        Socket client = sock.accept();
        System.out.println("Connected to client: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
        packet_out = new ObjectOutputStream(client.getOutputStream());
        packet_in = new ObjectInputStream(client.getInputStream());


    }

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
