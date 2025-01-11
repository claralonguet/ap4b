package screens;
import java.awt.*;
import javax.swing.*;

import errors.*;
import generals.*;
import game.*;

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

    private Enigme enigma; 

    public MainScreen(String title, int w, int h, Joueur joueur, Enigme enigma){
        super(title, w, h, joueur);

        this.enigma = enigma;
        this.joueur = joueur;

        String pseudo = joueur.getNom();
        String skin = joueur.getSkin();
        
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

        int criteriaNumber = enigma.getCriteres().size();
        int numberOnTop = criteriaNumber / 2 + criteriaNumber % 2;

        JPanel topLayer = new JPanel(new GridBagLayout());
        JPanel botLayer = new JPanel(new GridBagLayout());
        GridBagConstraints partGBC = generateGBC(0.0, 0.0);

        criterions = new ArrayList<>();

        for(int i = 0; i < criteriaNumber; i++){
            String[] refs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

            JPanel critPanel = new JPanel(new GridBagLayout());

            FormattedLabel letter = new FormattedLabel(refs[i], Color.BLACK, "cloneMachine", 25);
            critPanel.add(letter);

            FormattedButton crit = createCriteriaToDisplay(enigma.getCriteres().get(i).getNumeroCritere());
            crit.addActionListener(new TestCriteriaListener());
            critPanel.add(crit);

            if(numberOnTop == 0){
                partGBC = modifyGBC(partGBC, i - numberOnTop, 0, 1 / (criteriaNumber - numberOnTop), 1.0);
                botLayer.add(critPanel, partGBC); 
            } else {
                partGBC = modifyGBC(partGBC, i, 0, 1 / numberOnTop, 1.0);
                partGBC.insets = new Insets(10, 10, 10, 10);
                
                topLayer.add(critPanel, partGBC);
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
            if(pseudo.equals("Dinnerbone")){
                for (int i = 0; i < idles.length; i++) {
                    idlesTextures[i] = idlesTextures[i].rotate(-90);
                    idles[i].setTexture(idlesTextures[i]);
                }
            }

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
        dataButton.addActionListener(new DataButtonListener());
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
        String name = "critere";
        FormattedButton crit = new FormattedButton(name + Integer.toString(critNumber), new Dimension(231,150), true);
        crit.setContentAreaFilled(false);
        crit.setBorderPainted(false);
        crit.setBlocked(false);
        return crit; 
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Enigme e = Enigme.creerEnigme("enigme.txt", 4);
            MainScreen mainScreen = new MainScreen("Turing Machine", 1200,900,new Joueur("Dinnerbone", "joshua"),e); 
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

            //Easter Egg
            if(code.get(0) == 3 && code.get(1) == 1 && code.get(2) == 4){
                try {
                    GameTexture pi = new GameTexture("pi", false, "png");
                    Dimension d = codeValidationButton.getSize();
                    pi = pi.rescaleToFit(d);
                    codeValidationButton.setIcon(pi.convertToImageIcon());
                } catch (Exception exception) {
                    System.out.println("No pie for you!");
                }
            }

            try {
                Code c = new Code(code);
                joueur.majCode(c.toInt(), joueur.getData().getCode().size());
            } catch (WrongSizeException exception) {
                System.out.println("Error: Array is wrong-sized.");
            }
        }
    }

    private class TestCriteriaListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            trialsNumber++;

            //Vérification de la véracité du critère
            FormattedButton button = (FormattedButton) e.getSource();
        
            int a = 0; 
            for (int i = 0; i < criterions.size(); i++) {
                if(button.equals(criterions.get(i))){
                    a = i;
                }
            }
            try {
                button.setVerification(enigma.testerCritere(new Code(code), a));
            } catch (WrongSizeException exception) {
                System.out.println(exception.getMessage());
            }
            joueur.majCritere(true, joueur.getData().getCode().size() - 1, a);


            if(trialsNumber == 3){
                for(FormattedButton b : criterions){
                    b.setBlocked(false);
                }
            }
        }
    }

    private class EndButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            for(FormattedButton b : criterions){
                b.setBlocked(false);
            }

            joueur.majTrials(trialsNumber);

            kill();
        } 
    }

    private class DataButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            DataScreen dataScreen = new DataScreen("Informations de " + joueur.getNom(), 1000, 800, joueur);
            dataScreen.setVisible(true);
        }
    }
}
