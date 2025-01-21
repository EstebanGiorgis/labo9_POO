package disc;

import javax.swing.*;
import java.awt.*;

public class DiscsFrame extends JFrame {

    private final DrawingPanel drawingPanel;

    public DiscsFrame() {
        setTitle("Laboratoire 9: Disques");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        clearButton.addActionListener(e -> drawingPanel.clearDiscs());
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}
