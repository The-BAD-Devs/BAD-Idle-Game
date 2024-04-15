/*
 * Settings Class for REALLY (kinda) BAD IDLE GAME (Just Kidding) The Prequel II
 * Muhammed Abushamma, et al., Mar. 2024
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

/* 
 * Implementation for the "Settings" panel which can be accessed fron the start screen. 
 * Houses the buttons and sliders used to mute music volume, adjust music volume, adjust sfx volume, and to access the credits panel. 
 */

class Settings extends JPanel {
    /* Fields */
    final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static boolean isMute = true;
    final int buttonWidth = screenSize.width / 6;
    final Dimension BUTTON_SIZE = new Dimension(buttonWidth, screenSize.height / 20);
    final Font BUTTON_FONT = new Font("Serif", Font.BOLD, screenSize.width / 80);
    final Dimension BUTTON_GAP = new Dimension(0, 20);
    final Dimension SLIDER_SIZE = new Dimension(buttonWidth, screenSize.height / 40);
    final Color customColorBeige = new Color(253, 236, 166);
    final Color customColorBrown = new Color(102, 72, 54);
    final Border BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

    /* Constructor */
    public Settings() throws IOException{
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        
        // slider ranges from -70 to 5, starts at -4. The max allowable float value of the master volume is 6.0206, but set to 5 here just to ensure there are no distortions generated. Having it a 6 and increasing and decreasing the floatcontrol repeatedly gives the opportunity for distortative spikes in audio output to occur. 
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -70, 5, -4); 
        
        slider.setBackground(customColorBrown); // Set the background color

        JSlider sliderSFX = new JSlider(JSlider.HORIZONTAL, -70, 5, -5); // range from -70 to 6, starts at -5
        sliderSFX.setBackground(customColorBrown); // Set the background color

        JButton mute = new JButton("Mute Music Volume");
        mute.setBorder(BORDER);
        buttons.add(mute);
        JButton adjust = new JButton("Adjust Music Volume");
        adjust.setBorder(BORDER);
        buttons.add(adjust);   
        JButton sfx = new JButton("Adjust SFX Volume");
        sfx.setBorder(BORDER);
        buttons.add(sfx);   
        JButton leave = new JButton("Back to Main Menu");
        leave.setBorder(BORDER);
        buttons.add(leave);   
        JButton credits = new JButton("Credits");
        credits.setBorder(BORDER);
        buttons.add(credits); 

        // Adding the buttons to the start panel and controlling layout
        add(Box.createVerticalGlue());
        add(mute);
        add(Box.createRigidArea(BUTTON_GAP));
        add(adjust);
        add(slider);
        add(Box.createRigidArea(BUTTON_GAP));
        add(sfx);
        add(sliderSFX);
        add(Box.createRigidArea(BUTTON_GAP));
        add(credits);
        add(Box.createRigidArea(BUTTON_GAP));
        add(leave);
        add(Box.createVerticalGlue());



        //For loop that formats all the buttons
        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).setAlignmentX(CENTER_ALIGNMENT);
            buttons.get(i).setPreferredSize(BUTTON_SIZE);
            buttons.get(i).setMaximumSize(BUTTON_SIZE);
            buttons.get(i).setBackground(customColorBrown);
            buttons.get(i).setForeground(customColorBeige);
            buttons.get(i).setFont(BUTTON_FONT);
        }

        this.setAlignmentX(CENTER_ALIGNMENT);

        //Format music volume slider
        slider.setMaximumSize(SLIDER_SIZE);

        //Format sfx volume slider
        sliderSFX.setMaximumSize(SLIDER_SIZE);

        
        /* Methods */

        // Volume button mutes or unmutes master volume.
        mute.addActionListener(e -> {
            try {
                SFX.playSound("assets/SFX/interface1.wav");
                MusicPlayer.toggleMute(); 
                // SFX.toggleMuteSFX();
                if (isMute) {
                    mute.setText("Unmute Volume");
                    isMute = false; 
                } 
                  else  {
                    mute.setText("Mute Volume");
                    isMute = true;
                }
            }           
            catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        credits.addActionListener(e -> {
            try {
                SFX.playSound("assets/SFX/interface1.wav");
                Credits.yPos = ((int)screenSize.height); // set rolling text box in correct position;
                long startTime = System.currentTimeMillis();
                long waitTime = 75; // Wait for 75 milliseconds (0.075 seconds), so that credits text is properly set to correct location without previous text's location being seen.
                
                while (System.currentTimeMillis() - startTime < waitTime) {
                    // Busy-wait loop
                    // This loop repeatedly checks the current time until the desired wait time has passed
                }
        
                MusicPlayer.playMusic("assets/Music/3-Song-of-the-North.wav");
                Driver.changePanel("credits");
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        // "Back to main menu button" takes you back to the start screen
        leave.addActionListener(e -> {
            try {
                SFX.playSound("assets/SFX/interface1.wav");
                Driver.changePanel("start");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Slider Implementation. Adjusts music volume based on slider value. Adds change listener and method statsChanged sets volume of MusicPlayer.  
        slider.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    float volume = (float)source.getValue();
                    MusicPlayer.setVolume(volume);
                }
            }
        });

        // Slider Implementation. Adjusts SFX volume based on slider value. Adds change listener and method statsChanged sets volume of SFX.java.  
        sliderSFX.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider sourceSFX = (JSlider)e.getSource();
                    if (!sourceSFX.getValueIsAdjusting()) {
                        float volumeSFX = (float)sourceSFX.getValue();
                        SFX.setcurrentVolumeSFX(volumeSFX);
                    }
                }
            });
    }

    // draw image to backgroud
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
            try {
                g.drawImage(ImageIO.read(new File("assets/images/World5.1.png")), 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                //Auto-generated catch block
                e.printStackTrace();
            }
    }
    }
