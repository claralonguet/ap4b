import java.util.HashMap;
import java.util.List;

// Classe Data
public class Data {
    private HashMap<String, List<Integer>> code;

    public Data() {
        this.code = new HashMap<>();
    }

    // Getter pour le dictionnaire 'code'
    public HashMap<String, List<Integer>> getCode() {
        return code;
    }

    // Setter pour le dictionnaire 'code'
    public void setCode(HashMap<String, List<Integer>> code) throws InvalidDataException {
        if (code == null) {
            throw new InvalidDataException("Le dictionnaire de code ne peut pas être nul");
        }
        this.code = code;
    }

    // Méthode pour ajouter une nouvelle entrée au dictionnaire
    public void addCode(String key, List<Integer> value) throws InvalidDataException {
        if (key == null || key.isEmpty()) {
            throw new InvalidDataException("La clé ne peut pas être nulle ou vide");
        }
        if (value == null || value.isEmpty()) {
            throw new InvalidDataException("La valeur ne peut pas être nulle ou vide");
        }
        this.code.put(key, value);
    }

    // Méthode pour afficher les données
    public void displayData() {
        code.forEach((key, value) -> {
            System.out.println("Code testé : " + key + " - Réponse : " + value);
        });
    }
}

// Classe InvalidDataException (Exception Personnalisée)
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
