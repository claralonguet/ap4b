public class Code {
    private int bleu;
    private int jaune;
    private int violet;

    public Code(int bleu, int jaune, int violet) {
        this.bleu = Math.max(0, Math.min(6, bleu));
        this.jaune = Math.max(0, Math.min(6, jaune));
        this.violet = Math.max(0, Math.min(6, violet));
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
}
