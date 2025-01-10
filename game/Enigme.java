package game;

import java.util.List;
import java.util.ArrayList;

import java.io.*;

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
