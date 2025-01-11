package game;

import java.util.ArrayList;
import java.util.List;

import errors.InvalidDataException;

// Classe Data
public class Data {
    private List<List<Boolean>> critere;
    private List<List<Boolean>> numbers;
    private List<Integer> code;

    public Data() {
        this.critere = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Boolean> lb = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                lb.add(false);
            }
            critere.add(lb);
        }

        this.numbers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Boolean> lb = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                lb.add(false);
            }
            numbers.add(lb);
        }

        this.code = new ArrayList<>();
    }

    // Getter pour la liste 'critere'
    public List<List<Boolean>> getCritere() {
        return critere;
    }

    // Getter pour la liste 'code'
    public List<Integer> getCode() {
        return code;
    }

    public List<List<Boolean>> getNumbers(){
        return numbers;
    }

    // Setter pour la liste 'critere'
    public void setCritere(List<List<Boolean>> critere) throws InvalidDataException {
        if (critere == null) {
            throw new InvalidDataException("La liste de critère ne peut pas être nulle");
        }
        this.critere = critere;
    }

    // Setter pour la liste 'code'
    public void setCode(List<Integer> code) throws InvalidDataException {
        if (code == null) {
            throw new InvalidDataException("La liste de code ne peut pas être nulle");
        }
        this.code = code;
    }

    // Méthode pour ajouter un critère
    public void addCritere(boolean hasCriterionBeenTested, int tour, int col) throws InvalidDataException {
        if (!hasCriterionBeenTested) {
            throw new InvalidDataException("Le critère ne peut pas être nul");
        }
        critere.get(col).set(tour, hasCriterionBeenTested);
    }

    // Méthode pour ajouter un code
    public void addCode(Integer codeEntry, int tour) throws InvalidDataException {
        if (codeEntry == null) {
            throw new InvalidDataException("La valeur du code ne peut pas être nulle");
        }
        this.code.add(codeEntry);
    }

    public void addNumber(boolean hasNumberBeenChecked, int number, int col){
        this.numbers.get(col).set(number, hasNumberBeenChecked);
    }

    // Méthode pour afficher les données
    public void displayData() {
        System.out.println("Critères : " + critere);
        System.out.println("Codes : " + code);
    }
}