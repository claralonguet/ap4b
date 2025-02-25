import java.util.ArrayList;
import java.util.List;

// Classe Data
public class Data {
    private List<Integer> critere;
    private List<Integer> code;

    public Data() {
        this.critere = new ArrayList<>();
        this.code = new ArrayList<>();
    }

    // Getter pour la liste 'critere'
    public List<Integer> getCritere() {
        return critere;
    }

    // Getter pour la liste 'code'
    public List<Integer> getCode() {
        return code;
    }

    // Setter pour la liste 'critere'
    public void setCritere(List<Integer> critere) throws InvalidDataException {
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
    public void addCritere(Integer critere) throws InvalidDataException {
        if (criterion == null) {
            throw new InvalidDataException("Le critère ne peut pas être nul");
        }
        this.critere.add(critere);
    }

    // Méthode pour ajouter un code
    public void addCode(Integer codeEntry) throws InvalidDataException {
        if (codeEntry == null) {
            throw new InvalidDataException("La valeur du code ne peut pas être nulle");
        }
        this.code.add(codeEntry);
    }

    // Méthode pour afficher les données
    public void displayData() {
        System.out.println("Critères : " + critere);
        System.out.println("Codes : " + code);
    }
}

// Classe InvalidDataException (Exception Personnalisée)
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
