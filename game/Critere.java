package game;

import java.util.*;
import java.io.*;

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
