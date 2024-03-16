import java.awt.Dimension;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Color;

class World extends JPanel{
    /**
     * This function hosts the world map screen with buttons to go to town or dungeon
     * @param player The player character object
     * @throws IOException
     */
    public World(){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        Color customColorBeige = new Color(253, 236, 166);
        Color customColorBrown = new Color(102, 72, 54);

        JButton quit = new JButton("Quit");
        buttons.add(quit);
        JButton town = new JButton("Town");
        buttons.add(town);
        JButton dungeon = new JButton("Dungeon");
        buttons.add(dungeon);
        JButton mine = new JButton("Mining");
        buttons.add(mine);
        JButton wood = new JButton("Woodcutting");
        buttons.add(wood);
        JButton home = new JButton("Home");
        buttons.add(home);
        JButton leave = new JButton("Main Menu");
        buttons.add(leave);

        // This section adds the components and controls layout
        add(Box.createVerticalGlue());
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(leave);
        add(Box.createRigidArea(new Dimension(150, 20)));
        add(town);
        add(Box.createRigidArea(new Dimension(150, 20)));
        add(mine);
        add(Box.createRigidArea(new Dimension(150, 20)));
        add(wood);
        add(Box.createRigidArea(new Dimension(150, 20)));
        add(home);
        add(Box.createRigidArea(new Dimension(250, 20)));
        add(dungeon);
        add(Box.createRigidArea(new Dimension(100, 20)));
        add(quit);
        add(Box.createVerticalGlue());

        //For loop that formats all the buttons
        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).setPreferredSize(new Dimension(200, 80));
            buttons.get(i).setMaximumSize(new Dimension(200, 80));
            buttons.get(i).setBackground(customColorBrown);
            buttons.get(i).setForeground(customColorBeige);
            buttons.get(i).setFont(new Font("Serif", Font.BOLD, 24));

            if (i == 0) {
                buttons.get(0).setAlignmentX(CENTER_ALIGNMENT);
                buttons.get(0).setBackground(Color.RED);
                buttons.get(0).setForeground(Color.WHITE);
                buttons.get(i).setPreferredSize(new Dimension(200, 80));
                buttons.get(i).setMaximumSize(new Dimension(200, 80));
            }
        }

        quit.setAlignmentX(BOTTOM_ALIGNMENT);
        town.setAlignmentX(BOTTOM_ALIGNMENT);
        dungeon.setAlignmentX(BOTTOM_ALIGNMENT);
        leave.setAlignmentX(BOTTOM_ALIGNMENT);
        
        // Quit button exits the game
        quit.addActionListener(e -> {
            System.exit(0);
        });

        // Town button takes you to the town
        town.addActionListener(e -> {
            try {
                Driver.changePanel("town");
                MusicPlayer.playMusic("assets/images/Music/Village Consort.wav");
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Dungeon button takes you to the dungeon
        dungeon.addActionListener(e -> {
            try {
                Driver.changePanel("dungeon");
                MusicPlayer.playMusic("assets/images/Music/Fantasy Medieval Music - Song of the North.wav");
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Leave button takes you to the start screen
        leave.addActionListener(e -> {
            try {
                Driver.changePanel("start");              
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
