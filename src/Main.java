import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        Network network = new Network(new File("src/Data"));

        SwingUtilities.invokeLater(() -> {
            Window window = new Window(network);
        });

    }
}
