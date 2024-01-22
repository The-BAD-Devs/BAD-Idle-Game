import javax.swing.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

class Town extends JFrame {
    public Town(PlayerCharacter player){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();

        JPanel town = new JPanel();

        this.getContentPane().add(town);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        device.setFullScreenWindow(this);
    }
}