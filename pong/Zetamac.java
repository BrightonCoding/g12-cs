package pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Zetamac extends JFrame{
    private JPanel mainPanel;
    private int winner;

    private int num1, num2;

    public boolean win = false;

        public Zetamac() {

        num1 = (int) (Math.random()*10 + 1);
        num2 = (int) (Math.random()*10 + 1);       

        setTitle("Multi-Screen Number Input");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridLayout (2, 1));
        mainPanel.add(createScreen1());
        mainPanel.add(createScreen2());
        add(mainPanel);

    }

        private JPanel createScreen1() {
        JPanel panel = new JPanel(new FlowLayout());

        JLabel label = new JLabel(num1 + " + " + num2 + " = ?");
        JTextField textField = new JTextField(10);
        JButton checkBtn = new JButton("P1 Submit");

    checkBtn.addActionListener(e -> {
        try {
            int answer = Integer.parseInt(textField.getText().trim());

            if (answer == num1 + num2) {
                winner = 1;
                JOptionPane.showMessageDialog(this, "Correct!");
                dispose(); // closes the minigame window
            } else {
                JOptionPane.showMessageDialog(this, "Try again!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a whole number!");
        }
    });

    panel.add(label);
    panel.add(textField);
    panel.add(checkBtn);

    return panel;
}

        private JPanel createScreen2() {
        JPanel panel = new JPanel(new FlowLayout());

        JLabel label = new JLabel(num1 + " + " + num2 + " = ?");
        JTextField textField = new JTextField(10);
        JButton checkBtn = new JButton("P2 Submit");

    checkBtn.addActionListener(e -> {
        try {
            int answer = Integer.parseInt(textField.getText().trim());

            if (answer == num1 + num2) {
                winner = 2;
                JOptionPane.showMessageDialog(this, "Correct!");
                dispose(); // closes the minigame window
            } else {
                JOptionPane.showMessageDialog(this, "Try again!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a whole number!");
        }
    });

    panel.add(label);
    panel.add(textField);
    panel.add(checkBtn);

    return panel;
}

public int getWinner(){
    return winner;
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Zetamac().setVisible(true);
        });
    }


}
    

