package Pong.Networking;

import java.io.IOException;

public class ServerTester {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("server")) {
                Server server = new Server("localhost", 8080);
                server.accept();
                server.sendPacket(new TestPacket("Hi!", 5));
            } else if (args[0].equalsIgnoreCase("client")) {
                Client client = new Client(args[1], Integer.parseInt(args[2]));
                TestPacket pack = (TestPacket) client.recvPacket();
                System.out.println(pack.toString());
            }
        }
    }
}
