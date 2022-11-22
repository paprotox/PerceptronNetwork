import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {

    private Network network;
    private JFrame frame;

    private JPanel panelNorth;
    private JLabel info;

    private JPanel center;
    private JTextArea textArea;

    private JPanel panelSouth;
    private JButton button;
    private JLabel label;

    public Window(Network network) {
        this.network = network;
        frame = new JFrame("Neural network");
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panelNorth = new JPanel(new FlowLayout());
            info = new JLabel("", SwingConstants.CENTER);
            info.setFont(new Font("DialogInput", Font.PLAIN, 16));
            info.setText("Wpisz tekst w celu jego klasyfikacji językowej");
            info.setPreferredSize(new Dimension(480, 50));
        panelNorth.add(info);

        center = new JPanel(new BorderLayout());
            textArea = new JTextArea();
//            textArea.setPreferredSize(new Dimension(500, 350));
            textArea.setEditable(true);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
        center.add(textArea);

        panelSouth = new JPanel(new FlowLayout());
            button = new JButton("Check language");
            button.setPreferredSize(new Dimension(450, 50));
            button.addActionListener(this);

            label = new JLabel("", SwingConstants.CENTER);
            label.setBorder(BorderFactory.createTitledBorder("RESULT"));
            label.setFont(new Font("DialogInput", Font.PLAIN, 15));
            label.setPreferredSize(new Dimension(150,50));
        panelSouth.add(button);
        panelSouth.add(label);

        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(panelSouth, BorderLayout.SOUTH);
        //frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText(network.resultLanguage(textArea.getText()));
    }
}
