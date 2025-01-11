package game;

import java.util.ArrayList;

import errors.WrongSizeException;

public class Code {
    private int bleu;
    private int jaune;
    private int violet;

    public Code(int bleu, int jaune, int violet) {
        this.bleu = Math.max(0, Math.min(6, bleu));
        this.jaune = Math.max(0, Math.min(6, jaune));
        this.violet = Math.max(0, Math.min(6, violet));
    }

    public Code(ArrayList<Integer> c) throws WrongSizeException{
        if(c.size() != 3){
            throw new WrongSizeException("ArrayList is wrong-sized for the code.");
        } else {
            this.bleu = c.get(0);
            this.jaune = c.get(1);
            this.violet = c.get(2);
        }
    }

    public int getBleu() {
        return bleu;
    }

    public int getJaune() {
        return jaune;
    }

    public int getViolet() {
        return violet;
    }

    @Override
    public String toString() {
        return String.format("Bleu: %d, Jaune: %d, Violet: %d", bleu, jaune, violet);
    }

    public Integer toInt(){
        String s = Integer.toString(bleu) + Integer.toString(jaune) + Integer.toString(violet);
        return Integer.parseInt(s);
    }

    public Boolean equals(Code code){
        return bleu == code.getBleu() && jaune == code.getJaune() && violet == code.getViolet();
    }
}
