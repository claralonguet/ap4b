import java.awt.*;
import javax.swing.*;

import errors.*;
import generals.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainScreen extends GameScreen{
    private JPanel globalPanel;
    private JPanel topPanel;
    private JPanel criteriaAndCodePanel;  
    private JPanel buttonPanel; 

    private JPanel criteriaPanel;
    private JPanel codeZonePanel;
    private JPanel pseudoAndSkinPanel; 

    private FormattedLabel pseudoLabel; 
    private FormattedLabel enterCodeLabel; 
    
    private FormattedButton codeValidationButton; 
    private FormattedButton dataButton; 
    private FormattedButton endButton; 

    private CodeInput codeInputPanel;

    private ArrayList<Integer> code;

    private ArrayList<FormattedButton> criterions; 

    private int trialsNumber = 0; 

    public MainScreen(String title, int w, int h, String pseudo, String skin){
        super(title, w, h, pseudo, skin);
        
        //## GLOBAL
        globalPanel = new JPanel(new GridBagLayout()); 
        GridBagConstraints globalPanelGBC = generateGBC(1.0, 0.9);


        //# PARTIE SUPERIEURE 
        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints topPanelGBC = generateGBC(0.5, 1.0);
        topPanelGBC.insets = new Insets(10, 10, 10, 5);
        
        //Critère et code
        criteriaAndCodePanel = new JPanel(new GridBagLayout());
        GridBagConstraints criteriaAndCodePanelGBC = generateGBC(1.0, 0.7);
        criteriaAndCodePanelGBC.fill = GridBagConstraints.NONE; 

        //Critères
        criteriaPanel = new JPanel(new GridLayout(2,1));

        int criteriaNumber = 6;
        int numberOnTop = criteriaNumber / 2 + criteriaNumber % 2;

        JPanel topLayer = new JPanel(new GridBagLayout());
        JPanel botLayer = new JPanel(new GridBagLayout());
        GridBagConstraints partGBC = generateGBC(0.0, 0.0);

        criterions = new ArrayList<>();

        for(int i = 0; i < criteriaNumber; i++){
            FormattedButton crit = createCriteriaToDisplay(1);
            crit.addActionListener(new TestCriteriaListener());
            if(numberOnTop == 0){
                partGBC = modifyGBC(partGBC, i - numberOnTop, 0, 1 / (criteriaNumber - numberOnTop), 1.0);
                botLayer.add(crit, partGBC); 
            } else {
                partGBC = modifyGBC(partGBC, i, 0, 1 / numberOnTop, 1.0);
                partGBC.insets = new Insets(10, 10, 10, 10);
                
                topLayer.add(crit, partGBC);
                numberOnTop--; 
            }
            criterions.add(crit); 
        }
        criteriaPanel.add(topLayer); 
        criteriaPanel.add(botLayer);

        //Code
        codeZonePanel = new JPanel(new GridBagLayout());
        GridBagConstraints codeZonePanelGBC = generateGBC(0.4,1.0);
        codeZonePanelGBC.insets = new Insets(10, 50, 20, 5);

        enterCodeLabel = new FormattedLabel("Entrez un code : ", Color.BLACK, "typewritter", 23); 
        codeZonePanel.add(enterCodeLabel, codeZonePanelGBC); 

        codeZonePanelGBC = modifyGBC(codeZonePanelGBC, 1, 0, 0.4, 1.0);
        codeZonePanelGBC.insets = new Insets(10, 5, 20, 5);
        codeInputPanel = new CodeInput(); 
        codeZonePanel.add(codeInputPanel, codeZonePanelGBC); 

        Dimension buttonDim = new Dimension(50,50);
        codeValidationButton = new FormattedButton("validate",buttonDim, false);
        codeValidationButton.addActionListener(new BlockingInputButtonListener());
        codeValidationButton.setContentAreaFilled(false);
        codeValidationButton.setBorderPainted(false);

        codeZonePanelGBC = modifyGBC(codeZonePanelGBC, 2, 0, 0.2, 1.0);
        codeZonePanelGBC.fill = GridBagConstraints.NONE; 
        codeZonePanelGBC.insets = new Insets(10, 5, 20, 50);
        codeZonePanel.add(codeValidationButton, codeZonePanelGBC);

        //AJOUTER LES ELEMENTS
        criteriaAndCodePanel.add(criteriaPanel, criteriaAndCodePanelGBC);
        criteriaAndCodePanelGBC = modifyGBC(criteriaAndCodePanelGBC, 0, 1, 1.0, 0.3);
        criteriaAndCodePanel.add(codeZonePanel, criteriaAndCodePanelGBC); 

        topPanel.add(criteriaAndCodePanel, topPanelGBC);

        topPanelGBC = modifyGBC(topPanelGBC,1,0,0.5,1.0);
        topPanelGBC.insets = new Insets(10, 5, 0, 10);



        //Pseudo et skin
        pseudoAndSkinPanel = new JPanel(new GridBagLayout());
        GridBagConstraints pseudoAndSkinPanelGBC = generateGBC(1.0, 0.1);
        
        pseudoLabel = new FormattedLabel(pseudo,Color.BLACK,"typewritter",20); 
        pseudoLabel.setText(pseudo);
        pseudoAndSkinPanel.add(pseudoLabel, pseudoAndSkinPanelGBC);

        pseudoAndSkinPanelGBC = modifyGBC(pseudoAndSkinPanelGBC, 0, 1, 1.0, 0.9);

        //Skin
        JPanel skinPanel = new JPanel(new GridLayout(1,1));

        try{
            GameTexture[] idlesTextures = {new GameTexture(skin, "idle1"), new GameTexture(skin, "idle2")};
            TexturedPanel[] idles = {new TexturedPanel(idlesTextures[0].rotate(90)), new TexturedPanel(idlesTextures[1].rotate(90))}; 

            int currentIndex[] = {0}; 
            
            skinPanel.add(idles[currentIndex[0]]); 
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mise à jour de l'indice
                    currentIndex[0] = (currentIndex[0] + 1) % 2;
            
                    // Retirer l'ancien composant
                    skinPanel.removeAll();
            
                    // Ajouter le nouveau composant
                    skinPanel.add(idles[currentIndex[0]]);
            
                    // Forcer le rafraîchissement de l'affichage
                    skinPanel.revalidate();
                    skinPanel.repaint();
                }
            });
            timer.start(); // Démarrer le Timer
        } catch (IOException | WrongSkinNameException | WrongTextureTypeException e){
            System.out.println("Could not load textures. - " + e.getMessage());
            skinPanel.add(new FormattedLabel("Could not load images.", Color.RED, "cloneMachine", 25));
        }

        pseudoAndSkinPanel.add(skinPanel, pseudoAndSkinPanelGBC); 

        topPanel.add(pseudoAndSkinPanel, topPanelGBC);

        globalPanel.add(topPanel, globalPanelGBC); 

        globalPanelGBC = modifyGBC(globalPanelGBC,0,1,1.0,0.1);


        //Boutons du bas
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        dataButton = new FormattedButton("Informations", "cloneMachine", 25); 
        endButton = new FormattedButton("Fin", "cloneMachine",25);
        endButton.addActionListener(new EndButtonListener());
        
        Dimension rigidAreaDim = new Dimension(100,(int)(getHeight() * 0.1) - 10); 
        buttonPanel.add(Box.createRigidArea(rigidAreaDim));
        buttonPanel.add(dataButton);
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(endButton);
        buttonPanel.add(Box.createRigidArea(rigidAreaDim));

        globalPanel.add(buttonPanel, globalPanelGBC); 

        add(globalPanel);
    }

    private FormattedButton createCriteriaToDisplay(int critNumber){
        String name = "crit_";
        FormattedButton crit = new FormattedButton(name + Integer.toString(critNumber), new Dimension(200,100), true);
        crit.setContentAreaFilled(false);
        crit.setBorderPainted(false);
        crit.setBlocked();
        return crit; 
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen("Turing Machine", 1000,800,"Frank Getcher","clara"); 
            mainScreen.setVisible(true);
        });
    }

    private class BlockingInputButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            codeInputPanel.setInputBlocking();
            code = codeInputPanel.getCode();
            for(FormattedButton b : criterions){
                b.setUnblocked(); 
            }
        }
    }

    private class TestCriteriaListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            trialsNumber++;
            if(trialsNumber == 3){
                for(FormattedButton b : criterions){
                    b.setBlocked();
                }
                printCode();
            }
        }
    }

    private class EndButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            for(FormattedButton b : criterions){
                b.setBlocked();
            }
            printCode();
        } 
    }

    private void printCode(){
        for(int n : code){
            System.out.println(n);
        }
    }
}
