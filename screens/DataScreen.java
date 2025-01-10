package screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import game.*;

import java.util.ArrayList;

import generals.*;

public class DataScreen extends GameScreen{
    
    private JPanel globalPanel;
    private JPanel leftPanel; 
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel checkBoxesPanel;
    private JPanel testedCodesPanel;
    private JPanel testedCriteriaPanel;

    private FormattedLabel codeLabel;
    private FormattedLabel criteriaLabel; 
    private FormattedLabel checkBoxLabel;

    private ArrayList<ArrayList<JCheckBox>> checkBoxesArray; 
    private ArrayList<ArrayList<JCheckBox>> criteriaCheckBoxesArray;
    
    private ArrayList<ArrayList<Integer>> codesArray;

    final int codesNumber = 9;

    public DataScreen(String title, int w, int h, Joueur joueur){
        super(title, w, h, joueur);

        this.joueur = joueur;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Global
        globalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints globalPanelGBC = generateGBC(0.3, 1.0);


        //Codes testés
        leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints leftPanelGBC = generateGBC(1.0, 0.1);

        testedCodesPanel = new JPanel();
        testedCodesPanel.setLayout(new BoxLayout(testedCodesPanel, BoxLayout.X_AXIS));

        codeLabel = new FormattedLabel("Codes testés", Color.black, "typewritter", 32);
        leftPanel.add(codeLabel, leftPanelGBC);
        leftPanelGBC.fill = GridBagConstraints.CENTER;    

        leftPanelGBC = modifyGBC(leftPanelGBC, 0, 1, 1.0, 0.9);
        leftPanelGBC.fill = GridBagConstraints.VERTICAL;

        //Initialise la liste des codes déjà testés
        initializeCodes();
        
        //Ajoute les panels de code déjà joués
        testedCodesPanel.add(Box.createRigidArea(new Dimension(50,0)));
        for (int i = 0; i < 3; i++) {
            testedCodesPanel.add(createNumberColumn(i));
            testedCodesPanel.add(Box.createRigidArea(new Dimension(50,0)));
        }
        
        leftPanel.add(testedCodesPanel, leftPanelGBC);
        globalPanel.add(leftPanel, globalPanelGBC); 
        globalPanelGBC = modifyGBC(globalPanelGBC, 1, 0, 0.4, 1.0);


        //Critères déjà joués
        centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints centerPanelGBC = generateGBC(1.0, 0.1);

        criteriaLabel = new FormattedLabel("Critères joués", Color.black, "typewritter", 32);
        centerPanel.add(criteriaLabel, centerPanelGBC); 
        centerPanelGBC = modifyGBC(centerPanelGBC, 0, 1, 1.0, 0.9);

        testedCriteriaPanel = new JPanel();
        testedCriteriaPanel.setLayout(new BoxLayout(testedCriteriaPanel, BoxLayout.X_AXIS));
        createCriteriaBoxesArray();
        for (int index = 0; index < 6; index++) {
            testedCriteriaPanel.add(createCriteriaColumn(index));
        }
        
        centerPanel.add(testedCriteriaPanel, centerPanelGBC);
        globalPanel.add(centerPanel, globalPanelGBC); 
        globalPanelGBC = modifyGBC(globalPanelGBC, 2, 0, 0.3, 1.0);


        //Chiffres à barrer
        rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rightPanelGBC = generateGBC(1.0, 0.1);

        checkBoxesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints checkBoxesPanelGBC = generateGBC(0.333, 1.0);

        checkBoxLabel = new FormattedLabel("Chiffres déduits", Color.BLACK, "typewritter", 32);
        rightPanel.add(checkBoxLabel, rightPanelGBC);
        rightPanelGBC = modifyGBC(rightPanelGBC, 0, 1, 1.0, 0.9);

        //Initialisation du tableau de checkBox
        createBoxesArray();

        for(int i = 0; i < 3; i++){
            checkBoxesPanelGBC.gridx = i;

            String[] shapes = {"triangle", "square", "circle"};

            JPanel column = new JPanel(new GridBagLayout());
            GridBagConstraints columnGBC = new GridBagConstraints();
            columnGBC.gridy = 0; columnGBC.weighty = 0.16667; columnGBC.weightx = 1.0;

            try {
                GameTexture shapeTexture = new GameTexture(shapes[i], false,"png");
                shapeTexture = shapeTexture.rescaleToFit(new Dimension(50,50));
                TexturedPanel shapePanel = new TexturedPanel(shapeTexture);
                column.add(shapePanel, columnGBC); 
            } catch (Exception e) {
                FormattedLabel l = new FormattedLabel("Error", Color.RED, "cloneMachine", 25);
                column.add(l, columnGBC);
            }

            for (int j = 0; j < checkBoxesArray.get(i).size(); j++) {
                columnGBC = modifyGBC(columnGBC, 0, j + 1, 1.0, 0.16667);
                column.add(checkBoxesArray.get(i).get(j), columnGBC);
            }
            checkBoxesPanel.add(column, checkBoxesPanelGBC);
        }
        rightPanel.add(checkBoxesPanel, rightPanelGBC); 
        globalPanel.add(rightPanel, globalPanelGBC);

        add(globalPanel);
    }

    private void createBoxesArray(){
        this.checkBoxesArray = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            ArrayList<JCheckBox> col = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                GameFont font = new GameFont("cloneMachine");
                font = font.resize(25);
                JCheckBox cb = new JCheckBox(Integer.toString(5 - j));
                cb.addActionListener(new CheckBoxListener());
                cb.setFont(font.getFont());
                col.add(cb); 
            }
            checkBoxesArray.add(col);
        }
    }

    private void createCriteriaBoxesArray(){
        this.criteriaCheckBoxesArray = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ArrayList<JCheckBox> arr = new ArrayList<>();

            //Ajoute les CheckBox
            for (int j = 0; j < codesNumber; j++) {
                JCheckBox box = new JCheckBox();
                box.setEnabled(false);
                arr.add(box);
                box.setSelected(joueur.getData().getCritere().get(i).get(j));
            }
            this.criteriaCheckBoxesArray.add(arr);
        }
    }

    private void initializeCodes(){
        codesArray = new ArrayList<>();
        for(int code : joueur.getData().getCode()){
            int b = code / 100;
            int j = (code - b * 100) / 10;
            int v = (code - b * 100 - j * 10);
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(b); arr.add(j); arr.add(v);
            codesArray.add(arr);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            DataScreen d = new DataScreen("Turing Machine", 1200, 800, new Joueur("ye", "joshua"));
            d.setVisible(true);
        });
    }

    private class CheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //Change l'affichage des box
            JCheckBox s = (JCheckBox) e.getSource();

            GameFont font = new GameFont("cloneMachine");
            font = font.resize(25);

            if(s.isSelected()){
                font.setStrikeThrough();
                s.setFont(font.getFont());
                s.setForeground(Color.RED);
            } else {
                s.setFont(font.getFont());
                s.setForeground(Color.black);
            }

            s.repaint();
            s.revalidate();

            //Met à jour les données du joueur
            
        }        
    }

    private FormattedLabel createNumber(int number, int column){
        if(number != 0){
            Color color = Color.ORANGE;
            if(column == 0){
                color = Color.CYAN;
            } else if (column == 2){
                color = Color.PINK;
            }
            return new FormattedLabel(Integer.toString(number), color, "cloneMachine", 50);
        }
        return new FormattedLabel("-", Color.black, "cloneMachine", 50);
    }

    private JPanel createNumberColumn(int column){
        String[] shapes = {"triangle", "square", "circle"};

        JPanel col = new JPanel(new GridLayout(10,1));
        
        try {
            GameTexture shapeTexture = new GameTexture(shapes[column], false, "png");
            TexturedPanel shapePanel = new TexturedPanel(shapeTexture);
            col.add(shapePanel);
        } catch (Exception e) {
            FormattedLabel l = new FormattedLabel("Error", Color.red, "cloneMachine", 25);
            col.add(l);
        }

        for (int i = 0; i < codesArray.size(); i++) {
            col.add(createNumber(codesArray.get(i).get(column), column));
        }
        for (int i = 0; i < codesNumber - codesArray.size(); i++) {
            col.add(createNumber(0, column));
        }

        return col; 
    }

    private JPanel createCriteriaColumn(int column){
        JPanel col = new JPanel(new GridLayout(codesNumber + 1,1));

        //Ajoute la lettre au dessus de la colonne
        String[] refs = {"A", "B", "C", "D", "E", "F"};
        FormattedLabel letter = new FormattedLabel(refs[column], Color.BLACK, "cloneMachine", 32);
        col.add(letter);

        for (int i = 0; i < codesNumber; i++) {
            col.add(criteriaCheckBoxesArray.get(column).get(i));
        }

        return col;
    }
}