package Pong.Networking;

import java.io.Serial;
import java.io.Serializable;

public class TestPacket implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public String text;
    public int number;


    public TestPacket(String Text, int Number) {
        text = Text;
        number = Number;
    }

    public String toString() {
        return "Text: " + text + "\n" + "Number: " + number;
    }
}
