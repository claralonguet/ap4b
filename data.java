import java.util.ArrayList;
import java.util.List;

// Classe Data
public class Data {
    private List<String> critere;
    private List<List<Integer>> code;

    public Data() {
        this.critere = new ArrayList<>();
        this.code = new ArrayList<>();
    }

    // Getter pour la liste 'critere'
    public List<String> getCritere() {
        return critere;
    }

    // Getter pour la liste 'code'
    public List<List<Integer>> getCode() {
        return code;
    }

    // Setter pour les listes 'critere' et 'code'
    public void setCritereAndCode(List<String> critere, List<List<Integer>> code) throws InvalidDataException {
        if (critere == null || code == null) {
            throw new InvalidDataException("Les listes de critère et de code ne peuvent pas être nulles");
        }
        if (critere.size() != code.size()) {
            throw new InvalidDataException("Les listes de critère et de code doivent avoir la même taille");
        }
        this.critere = critere;
        this.code = code;
    }

    // Méthode pour ajouter une nouvelle entrée aux listes
    public void addData(String criterion, List<Integer> codeEntry) throws InvalidDataException {
        if (criterion == null || criterion.isEmpty()) {
            throw new InvalidDataException("Le critère ne peut pas être nul ou vide");
        }
        if (codeEntry == null || codeEntry.isEmpty()) {
            throw new InvalidDataException("La valeur du code ne peut pas être nulle ou vide");
        }
        this.critere.add(criterion);
        this.code.add(codeEntry);
    }

    // Méthode pour afficher les données
    public void displayData() {
        for (int i = 0; i < critere.size(); i++) {
            System.out.println("Critère : " + critere.get(i) + " - Réponse : " + code.get(i));
        }
    }
}

// Classe InvalidDataException (Exception Personnalisée)
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
