import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;


class Shop extends JPanel {

    private Inventory inventory; // Reference to the Inventory object
    private boolean sellScreenOpen = false; // Flag to track if the sell screen is open
    private boolean buyScreenOpen = false; // Flag to track if the buy screen is open
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
            try {
                g.drawImage(ImageIO.read(new File("assets/images/shop2.png")), 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                //Auto-generated catch block
                e.printStackTrace();
            }
    }
    /**
     * This function hosts the town screen with buttons to buy potions or leave
     * @param player The player character object
     * @throws IOException
     */
    public Shop(Inventory inventory) { // Accepts an Inventory object
        this.inventory = inventory; // Assign the provided Inventory object to the local variable
        JPanel buttonPanel = new JPanel();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        Color customColorBeige = new Color(253, 236, 166);
        Color customColorBrown = new Color(102, 72, 54);

        //Whenever calling a getter for the player, it breaks it.
        JLabel name = new JLabel("Name: ");

        JButton buy = new JButton("Buy");
        buttons.add(buy);
        JButton leave = new JButton("Leave");
        buttons.add(leave);
        JButton inventory1 = new JButton("Inventory");
        buttons.add(inventory1);
        JButton sell = new JButton("Sell");
        buttons.add(sell);
        
        // Adding the buttons to the start panel and controlling layout
        add(Box.createVerticalGlue());
        // add(name);
        add(Box.createRigidArea(new Dimension(100, 350)));
        add(buy);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(sell);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(inventory1);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(leave);
        add(Box.createVerticalGlue());

        //For loop that formats all the buttons
        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).setAlignmentX(CENTER_ALIGNMENT);

            buttons.get(i).setPreferredSize(new Dimension(200, 80));
            buttons.get(i).setMaximumSize(new Dimension(200, 80));
            buttons.get(i).setBackground(customColorBrown);
            buttons.get(i).setForeground(customColorBeige);
            buttons.get(i).setFont(new Font("Serif", Font.BOLD, 24));
        }

        this.setAlignmentX(CENTER_ALIGNMENT);

        /*
         * 
         * 
         * Buy Screen
         * 
         */
        // Buy button adds a potion to the player's inventory
        buy.addActionListener(e -> {
            SFX.playSound("assets/SFX/interface1.wav");
            if (buyScreenOpen == false) { // Check if buy screen is not already open
                buyScreenOpen = true; // Set buy screen as open
            
            // Remove existing buttons
            // this.removeAll();
            // this.revalidate();
            // this.repaint();

            // Create a panel to hold the gold count label and the sell panel
            JPanel mainPanel = new JPanel(new BorderLayout());

            // Create a label to display the player's gold count
            JLabel goldLabel = new JLabel("Gold: " + inventory.getResource("Gold"));
            goldLabel.setFont(new Font("Serif", Font.BOLD, 28));
            goldLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Create a close button
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(closeEvent -> {
                // Remove the sell panel and the close button
                remove(mainPanel);
                buyScreenOpen = false; // Set sell screen as closed
                revalidate();
                repaint();
            });

            // Create buy label message
            JLabel buy_label = new JLabel("Buy");
            buy_label.setFont(new Font("Serif", Font.BOLD, 28));

            // Create error message
            JLabel err_message = new JLabel("");
            err_message.setFont(new Font("Serif", Font.BOLD, 24));

            // Add the close button to the top right corner
            JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            closeButtonPanel.add(closeButton);
        
            // Create a menu for buying items
            JScrollPane scrollPane = new JScrollPane();
            JPanel buyPanel = new JPanel();
            buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS));
        
            // Add all items from inventory to shop. 
            for (String resourceName : inventory.getResources().keySet()) {
                    JButton buyItemButton = new JButton("Buy " + resourceName + " (" + inventory.getResource(resourceName) + ")");
                    // Format buttons
                    buyItemButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    
                    buyItemButton.addActionListener(sellEvent -> {
                        
                        // If Gold is greater than 0 then buy resource,, else output error message. 
                        if ((inventory.getResource("Gold") > 0)) {
                            err_message.setText("");
                            inventory.setResource(resourceName, inventory.getResource(resourceName)  + 1); // add the resource from inventory
                            int currentGold = inventory.getResource("Gold"); // update current resource amount
                            inventory.setResource("Gold", currentGold - 1); // decrease gold 
                            inventory.updateResourceLabels(); // Update the labels
                            goldLabel.setText("Gold: " + inventory.getResource("Gold")); // Update the gold label
                            buyItemButton.setText("Buy " + resourceName + " (" + (inventory.getResource(resourceName)) + ")"); // Update the buy button label
                            SFX.playSound("assets/SFX/coin3.wav");
                            
                        } else {
                            err_message.setText("Cannot buy item, no more gold.");
                        }
                    });
                    buyPanel.add(buyItemButton);
                }
        
            scrollPane.setViewportView(buyPanel);
            scrollPane.setPreferredSize(new Dimension(400, 400));

                // Add components to the main panel
                mainPanel.add(closeButtonPanel, BorderLayout.NORTH);
                mainPanel.add(goldLabel, BorderLayout.EAST);
                mainPanel.add(scrollPane, BorderLayout.CENTER);
                mainPanel.add(err_message, BorderLayout.WEST);
                mainPanel.add(buy_label, BorderLayout.SOUTH);
        
            // Add the scroll pane to the center of the Shop panel
            add(mainPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else {
            // Output error message that only one sell screen can be opened at a time
            JOptionPane.showMessageDialog(this, "Only one buy screen can be opened at a time.", "Buy Screen Error", JOptionPane.ERROR_MESSAGE);
        }
    });
        

        /*
         * 
         * Sell Screen
         * 
         * 
         */
        // Sell button to sell equipment or potions for gold
        sell.addActionListener(e -> {
            SFX.playSound("assets/SFX/interface1.wav");
            if (sellScreenOpen == false) { // Check if sell screen is not already open
                sellScreenOpen = true; // Set sell screen as open

            // Remove existing buttons
            // this.removeAll();
            // this.revalidate();
            // this.repaint();

            // Create a panel to hold the gold count label and the sell panel
            JPanel mainPanel = new JPanel(new BorderLayout());

            // Create a label to display the player's gold count
            JLabel goldLabel = new JLabel("Gold: " + inventory.getResource("Gold"));
            goldLabel.setFont(new Font("Serif", Font.BOLD, 28));
            goldLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Create a close button
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(closeEvent -> {
                // Remove the sell panel and the close button
                remove(mainPanel);
                sellScreenOpen = false; // Set sell screen as closed
                revalidate();
                repaint();
            });

            // Create sell label
            JLabel sell_label = new JLabel("Sell");
            sell_label.setFont(new Font("Serif", Font.BOLD, 28));

            // Create error message
            JLabel err_message = new JLabel("");
            err_message.setFont(new Font("Serif", Font.BOLD, 25));

            // Add the close button to the top right corner
            JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            closeButtonPanel.add(closeButton);
        
            // Create a scrolling menu for selling items
            JScrollPane scrollPane = new JScrollPane();
            JPanel sellPanel = new JPanel();
            sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.Y_AXIS));
        
            // Add items from the inventory to the sell panel
            for (String resourceName : inventory.getResources().keySet()) {
                // if the resource amount exceeds 1 from your inventory, add it to the sell menu as a possible item to sell.
                if (inventory.getResource(resourceName) > 0) {
                    JButton sellItemButton = new JButton("Sell " + resourceName + " (" + inventory.getResource(resourceName) + ")");
                    // format buttons
                    sellItemButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    
                    sellItemButton.addActionListener(sellEvent -> {

                        // If resource is greater than 0, it is able to sell, else output error message. 
                        if ((inventory.getResource(resourceName) > 0)) {
                            err_message.setText("");
                            inventory.setResource(resourceName, inventory.getResource(resourceName)  - 1); // minus the resource from inventory
                            int currentGold = inventory.getResource("Gold"); // update current resource amount
                            inventory.setResource("Gold", currentGold + 1); // increase gold 
                            inventory.updateResourceLabels(); // Update the labels
                            goldLabel.setText("Gold: " + inventory.getResource("Gold")); // Update the gold label
                            sellItemButton.setText("Sell " + resourceName + " (" + (inventory.getResource(resourceName)) + ")"); // Update the sell button label
                            SFX.playSound("assets/SFX/coin3.wav");
                        } else {
                            err_message.setText("Cannot sell item, no items left.");
                        }
                    });
                    sellPanel.add(sellItemButton);
                }
            }
        
            scrollPane.setViewportView(sellPanel);
            scrollPane.setPreferredSize(new Dimension(400, 400));

                // Add components to the main panel
                mainPanel.add(closeButtonPanel, BorderLayout.NORTH);
                mainPanel.add(goldLabel, BorderLayout.EAST);
                mainPanel.add(scrollPane, BorderLayout.CENTER);
                mainPanel.add(err_message, BorderLayout.WEST);
                mainPanel.add(sell_label, BorderLayout.SOUTH);
        
            // Add the scroll pane to the center of the Shop panel
            add(mainPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        } else {
            // Output error message that only one sell screen can be opened at a time
            JOptionPane.showMessageDialog(this, "Only one sell screen can be opened at a time.", "Sell Screen Error", JOptionPane.ERROR_MESSAGE);
        }
    });
            

        // Goes to inventory
        inventory1.addActionListener(e -> {
            try {
                SFX.playSound("assets/SFX/interface1.wav");
                Driver.changePanel("inventory");
                 // TODO: Add inventory implementation
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Leave button takes you back to the town panel
        leave.addActionListener(e -> {
            try {
                SFX.playSound("assets/SFX/interface1.wav");
                Driver.changePanel("town");
                MusicPlayer.playMusic("assets/Music/Village Consort.wav");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}