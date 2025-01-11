package generals;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class CodeInput extends JPanel{
    private ArrayList<Integer> code; 
    private ArrayList<FormattedComboBox<Integer>> boxes;

    private final ArrayList<String> shapes = new ArrayList<>();
    private final ArrayList<Color> colors = new ArrayList<>();

    public CodeInput(){

        shapes.add("triangle"); shapes.add("square"); shapes.add("circle");
        colors.add(Color.CYAN); colors.add(Color.YELLOW); colors.add(Color.PINK);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        boxes = new ArrayList<>();
        code = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            TexturedPanel p = new TexturedPanel();
            p.setLayout(new GridBagLayout());
            GridBagConstraints pGBC = new GridBagConstraints();
            pGBC.gridx = 0; pGBC.gridy = 0; pGBC.anchor = GridBagConstraints.CENTER;
            pGBC.fill = GridBagConstraints.NONE;
            try {
                GameTexture shape = new GameTexture(shapes.get(i), false, "png");
                shape = shape.rescaleToFit(new Dimension(100,100));
                p.setTexture(shape);
            } catch (Exception e) {
                System.out.println("Couldn't load shape texture.");
            }

            gbc.gridx = i;
            Integer[] numbers = {1, 2, 3, 4, 5};
            FormattedComboBox<Integer> numChoice = new FormattedComboBox<>(numbers, Color.BLACK, colors.get(i), Color.BLACK);
            numChoice.addItemListener(new CodeInputListener());

            boxes.add(numChoice);
            code.add(1);

            p.add(numChoice);

            add(p, gbc); 
        }
    }

    public void setInputBlocking(){
        for (FormattedComboBox<Integer> box : boxes) {
            box.setEnabled(false);
        }
    }

    public ArrayList<Integer> getCode(){
        return code;
    }

    private void updateCode(int boxIndex){
        int newNumber = Integer.parseInt(boxes.get(boxIndex).getSelectedItem().toString());
        code.set(boxIndex, newNumber);
    }

    public void printCode(){
        String s = ""; 
        for(Integer c : code){
            s += Integer.toString(c);
        }
        System.out.println(s);
    }

    private class CodeInputListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange() == ItemEvent.SELECTED){
                //Cherche la box qui a été modifiée
                int idx = 0;
                for (int i = 0; i < 3; i++) {
                    if(e.getSource().equals(boxes.get(i))){
                        idx = i; 
                    }
                }
                
                //Une fois la source trouvée, update le code
                updateCode(idx);
            }
        }
    }
}
