import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;


public class TuringMachine {
    public static void main(String[] args) {
        try {
            // Créer une énigme à partir d'un fichier
            Enigme enigme = Enigme.creerEnigme("enigme.txt", 4);

            // Afficher la solution de l'énigme
            System.out.println("Solution de l'enigme : " + enigme.getSolution());

            // Créer un code pour tester
            Code codeTest = new Code(1,3,4);
            System.out.println("\nCode teste : " + codeTest);

            // Tester l'ensemble des critères
            boolean tousCriteresValides = true;
            for (int i = 0; i < enigme.getCriteres().size(); i++) {
                boolean critereValide = enigme.testerCritere(codeTest, i);
                System.out.println("Critere " + i + " valide : " + critereValide);
                if (!critereValide) {
                    tousCriteresValides = false;
                }
            }


            // Vérifier si le code est la solution
            if (tousCriteresValides && enigme.testerCode(codeTest)) {
                System.out.println("Le code teste est la solution !");
            } else {
                System.out.println("Le code teste n'est pas la solution.");
            }


            codeTest = new Code(2,3,3);
            System.out.println("\nCode teste : " + codeTest);

            for (int i = 0; i < enigme.getCriteres().size(); i++) {
                boolean critereValide = enigme.testerCritere(codeTest, i);
                System.out.println("Critere " + i + " valide : " + critereValide);
                if (!critereValide) {
                    tousCriteresValides = false;
                }
            }

            // Vérifier si le code est la solution
            if (tousCriteresValides && enigme.testerCode(codeTest)) {
                System.out.println("Le code teste est la solution !");
            } else {
                System.out.println("Le code teste n'est pas la solution.");
            }
        } catch (Exception e) {
            System.err.println("Erreur dans le programme : " + e.getMessage());
        }
    }
}



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


public class Regle {
    private int id;
    private Predicate<Code> condition;

    public Regle(int id, String expression) {
        this.id = id;
        this.condition = creerPredicate(expression);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean testerRegle(Code code) {
        return condition.test(code);
    }

    private Predicate<Code> creerPredicate(String expression) {
        return code -> {
            int b = code.getBleu();
            int j = code.getJaune();
            int v = code.getViolet();

            try {
                if (expression.contains("<")) {
                    String[] parts = expression.split("<");
                    return getValeur(parts[0], b, j, v) <
                           getValeur(parts[1], b, j, v);
                } else if (expression.contains(">")) {
                    String[] parts = expression.split(">");
                    return getValeur(parts[0], b, j, v) >
                           getValeur(parts[1], b, j, v);
                } else if (expression.contains("=")) {
                    String[] parts = expression.split("=");
                    return getValeur(parts[0], b, j, v) ==
                           getValeur(parts[1], b, j, v);
                } else if (expression.contains("%")) {
                    String[] parts = expression.split("%");
                    return getValeur(parts[0], b, j, v) %
                           Integer.parseInt(parts[1]) == 0;
                } else if (expression.contains("&")) {
                    String[] parts = expression.split("&");
                    return creerPredicate(parts[0]).test(code) &&
                           creerPredicate(parts[1]).test(code);
                } else if (expression.contains("!")) {
                    String[] parts = expression.split("!");
                    return !creerPredicate(parts[1]).test(code);
                }
            } catch (Exception e) {
                System.err.println("Erreur dans l'expression : " + expression);
            }
            return false;
        };
    }

    private int getValeur(String token, int b, int j, int v) {
        switch (token.trim().toLowerCase()) {
            case "b": return b;
            case "j": return j;
            case "v": return v;
            case "p": return (b % 2 == 0 ? 1 : 0) + (j % 2 == 0 ? 1 : 0) + (v % 2 == 0 ? 1 : 0);
            case "i": return (b % 2 != 0 ? 1 : 0) + (j % 2 != 0 ? 1 : 0) + (v % 2 != 0 ? 1 : 0);
            default:
                if (token.matches("\\d+")) return Integer.parseInt(token);
                throw new IllegalArgumentException("Valeur inconnue : " + token);
        }
    }
}


public class Critere {
    private List<Regle> regles;
    private int indiceCorrect;
    private int numeroCritere;

    public Critere(List<Regle> regles, int indiceCorrect, int numeroCritere) {
        this.regles = regles;
        this.indiceCorrect = indiceCorrect;
        this.numeroCritere = numeroCritere;
    }

    public boolean testerCritere(Code code) {
        return regles.get(indiceCorrect).testerRegle(code);
    }

    public Regle getBonneRegle() {
        return regles.get(indiceCorrect);
    }

    public Regle getRegle(int indice) {
        return regles.get(indice);
    }

    public int getNumeroCritere(){
        return numeroCritere;
    }

    public int lenRegles(){
        return regles.size();
    }

    public static Critere creerCritere(String cheminFichier, int idCritere, int indiceCorrect) {
        List<Regle> regles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split(";");
                int id = Integer.parseInt(parties[0]);
                if (id == idCritere) {
                    for (int i = 1; i < parties.length; i++) {
                        regles.add(new Regle(i - 1, parties[i]));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return new Critere(regles, indiceCorrect, idCritere);
    }
}


public class Enigme {
    private List<Critere> criteres;
    private Code solution;

    public Enigme(List<Critere> criteres, Code solution) {
        this.criteres = criteres;
        this.solution = solution;
    }

    public Critere getCritere(int indice){
        return criteres.get(indice);
    }

    public List<Critere> getCriteres(){
        return criteres;
    }

    public Code getSolution(){
        return solution;
    }

    public boolean testerCode(Code code) {
        return code.toString().equals(solution.toString());
    }

    public boolean testerCritere(Code code, int indiceCritere) {
        return criteres.get(indiceCritere).testerCritere(code);
    }

    public static Enigme creerEnigme(String cheminFichier, int numeroEnigme) {
        List<Critere> criteres = new ArrayList<>();
        Code solution = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.split("=");
                if (Integer.parseInt(parties[0]) == numeroEnigme) {
                    String[] codeParts = parties[1].split("");
                    solution = new Code(Integer.parseInt(codeParts[0]), Integer.parseInt(codeParts[1]), Integer.parseInt(codeParts[2]));
                    String[] criteresParts = parties[2].split("_");
                    for (String critere : criteresParts) {
                        String[] critereParts = critere.split("-");
                        int idCritere = Integer.parseInt(critereParts[0]);
                        int indiceCorrect = Integer.parseInt(critereParts[1]);
                        criteres.add(Critere.creerCritere("critere.txt", idCritere, indiceCorrect));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return new Enigme(criteres, solution);
    }
}
