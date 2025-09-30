package UI;

import UI.BackgroundPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;



public class RailWayBuilderGame extends javax.swing.JFrame {
    
    private ArrayList<String> Difficulties;
    private ArrayList<String> Characters;
    private ArrayList<Integer> imageIndices;
    private int index = 0;
    private String selectedCharacterImage;
    private static final String IMAGE_PATH = "Images/";
    
    private int n = 0;
    public RailWayBuilderGame() {

        Difficulties = new ArrayList<>();
        Difficulties.add("Easy");
        Difficulties.add("Medium");
        Difficulties.add("Coming soon");
        
        Characters = new ArrayList<>();
        Characters.add("Builder_I.png");
        Characters.add("Builder_II.png");
        Characters.add("Builder_III.png");
        
       
        initComponents();
        
        
        BackgroundPanel backgroundPanel = new BackgroundPanel("Images/backGroundImage.jpg");
        setContentPane(backgroundPanel);
        
        JLayeredPane layeredPane = new JLayeredPane();
        
        backgroundPanel.setBounds(0, 0, 800, 600);  
        layeredPane.add(backgroundPanel, Integer.valueOf(0));
        
        layeredPane.add(NewGame, Integer.valueOf(1));
        layeredPane.add(Difficult, Integer.valueOf(1));
        layeredPane.add(DifficultPlus, Integer.valueOf(1));
        layeredPane.add(DifficultMinus, Integer.valueOf(1));
        
        setContentPane(layeredPane);
        
        
        setTitle("Railway Builder Game");
        setSize(800, 600); 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NewGame = new javax.swing.JButton();
        Difficult = new java.awt.Label();
        DifficultPlus = new javax.swing.JButton();
        DifficultMinus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        NewGame.setText("New Game");
        NewGame.setEnabled(false);
        NewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameActionPerformed(evt);
            }
        });

        Difficult.setAlignment(java.awt.Label.CENTER);
        Difficult.setForeground(new java.awt.Color(255, 51, 51));
        Difficult.setText("Difficult");

        DifficultPlus.setText(">");
        DifficultPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DifficultPlusActionPerformed(evt);
            }
        });

        DifficultMinus.setText("<");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(279, Short.MAX_VALUE)
                .addComponent(DifficultMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NewGame, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(Difficult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DifficultPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DifficultPlus)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Difficult, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(DifficultMinus))
                .addContainerGap(204, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameActionPerformed
        createSelectCharachter();

    }//GEN-LAST:event_NewGameActionPerformed

    
    public void createSelectCharachter(){
        
    getContentPane().removeAll();

    getContentPane().setLayout(new BorderLayout());

    JLabel selectCharacter = new JLabel("Select Character");
    selectCharacter.setHorizontalAlignment(JLabel.CENTER);
    getContentPane().add(selectCharacter, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel(new BorderLayout()); 
    getContentPane().add(centerPanel, BorderLayout.CENTER);

    JButton newGameButton = new JButton("<");
    centerPanel.add(newGameButton, BorderLayout.WEST);

    BackgroundPanel characterPanel = new BackgroundPanel(IMAGE_PATH + Characters.get(0));
    characterPanel.setPreferredSize(new Dimension(10, 200));
    centerPanel.add(characterPanel, BorderLayout.CENTER);

    JButton newGameButton2 = new JButton(">");
    centerPanel.add(newGameButton2, BorderLayout.EAST); 

    JButton newGameButton3 = new JButton("Start");
    centerPanel.add(newGameButton3, BorderLayout.SOUTH);

    
    
    newGameButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index == 0) {
                index = Characters.size() - 1;
            } else {
                index--;
            }
            characterPanel.loadImage(IMAGE_PATH + Characters.get(index));
            characterPanel.repaint();
        }
    });

    newGameButton2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index < Characters.size() - 1) {
                index++;
            } else {
                index = 0;
            }
            characterPanel.loadImage(IMAGE_PATH + Characters.get(index));
            characterPanel.repaint();
        }
    });
    
        newGameButton3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            getContentPane().removeAll();
            repaint();
            setSize(300, 300); 
            setLocationRelativeTo(null);
            revalidate(); 
            selectedCharacterImage = Characters.get(index);

            getContentPane().removeAll();
            getContentPane().setLayout(new BorderLayout());
            setSize(800, 600); 
             setLocationRelativeTo(null);

            Board gameBoard = new Board(selectedCharacterImage,n);
            getContentPane().add(gameBoard, BorderLayout.CENTER);

            revalidate();
            repaint();
            }
        });

        revalidate();
        repaint();
        setSize(300, 300);
        setLocationRelativeTo(null);
        }
    
    public String getImagePath(int index) {
        return Characters.get(imageIndices.get(index));
    }
    

    
    private void DifficultPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DifficultPlusActionPerformed
  
        if (n < Difficulties.size()) {
            Difficult.setText(Difficulties.get(n));
            n++;
        } else {
            Difficult.setText(Difficulties.get(0));
            n = 1; 
        }
        NewGame.setEnabled(true);
    }//GEN-LAST:event_DifficultPlusActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RailWayBuilderGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RailWayBuilderGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RailWayBuilderGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RailWayBuilderGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RailWayBuilderGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label Difficult;
    private javax.swing.JButton DifficultMinus;
    private javax.swing.JButton DifficultPlus;
    private javax.swing.JButton NewGame;
    // End of variables declaration//GEN-END:variables
}
