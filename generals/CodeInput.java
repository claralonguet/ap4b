package generals;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class CodeInput extends JPanel{
    private ArrayList<Integer> code; 
    private ArrayList<FormattedComboBox<Integer>> boxes;

    public CodeInput(){

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        boxes = new ArrayList<>();
        code = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            gbc.gridx = i;
            Integer[] numbers = {1, 2, 3, 4, 5};
            FormattedComboBox<Integer> numChoice = new FormattedComboBox<>(numbers, Color.BLACK, Color.WHITE, Color.BLACK);
            numChoice.addItemListener(new CodeInputListener());

            boxes.add(numChoice);
            code.add(1);

            add(numChoice, gbc); 
        }
        printCode();
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
        printCode();
    }

    private void printCode(){
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
