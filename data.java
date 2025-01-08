import java.util.ArrayList;
import java.util.List;

// Classe Data
public class Data {
    private List<Integer> critere;
    private List<List<Integer>> code;

    public Data() {
        this.critere = new ArrayList<>();
        this.code = new ArrayList<>();
    }

    // Getter pour la liste 'critere'
    public List<Integer> getCritere() {
        return critere;
    }

    // Getter pour la liste 'code'
    public List<List<Integer>> getCode() {
        return code;
    }

    // Setter pour les listes 'critere' et 'code'
    public void setCritereAndCode(List<Integer> critere, List<List<Integer>> code) throws InvalidDataException {
        if (critere == null || code == null) {
            throw new InvalidDataException("Les listes de critère et de code ne peuvent pas être nulles");
        }
        if (critere.size() != code.size()) {
            throw new InvalidDataException("Les listes de critère et de code doivent avoir la même taille");
        }
        this.critere = critere;
        this.code = code;
    }

    // Méthode pour ajouter un critère
    public void addCritere(Integer critere) throws InvalidDataException {
        if (critere == null) {
            throw new InvalidDataException("Le critère ne peut pas être nul");
        }
        this.critere.add(critere);
        this.code.add(new ArrayList<>());  // Ajout d'une liste vide pour maintenir la correspondance des tailles
    }

    // Méthode pour ajouter un code
    public void addCode(List<Integer> codeEntry) throws InvalidDataException {
        if (codeEntry == null || codeEntry.isEmpty()) {
            throw new InvalidDataException("La valeur du code ne peut pas être nulle ou vide");
        }
        if (this.critere.size() <= this.code.size()) {
            throw new InvalidDataException("Aucun critère correspondant n'est disponible pour ce code");
        }
        this.code.set(this.code.size() - 1, codeEntry);  // Remplacer la liste vide par la liste de code
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
