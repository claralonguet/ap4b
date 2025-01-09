import java.util.List;

public class Joueur {
    private String nom;
    private Data data;

    public Joueur(String nom, Data data) {
        this.nom = nom;
        this.data = data;
    }

    // Getter pour nom
    public String getNom() {
        return nom;
    }

    // Setter pour nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour data
    public Data getData() {
        return data;
    }

    // Setter pour data
    public void setData(Data data) {
        this.data = data;
    }

    // Méthode pour mettre à jour un critère
    public void majCritere(Integer criterion) {
        System.out.println("Mise à jour des critères pour le joueur : " + nom);
        try {
            data.addCritere(criterion);
        } catch (InvalidDataException e) {
            System.out.println("Erreur lors de la mise à jour des critères : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un code
    public void majCode(Integer codeEntry) {
        System.out.println("Mise à jour des codes pour le joueur : " + nom);
        try {
            data.addCode(codeEntry);
        } catch (InvalidDataException e) {
            System.out.println("Erreur lors de la mise à jour des codes : " + e.getMessage());
        }
    }

    // Afficher les informations du joueur
    public void displayJoueur() {
        System.out.println("Nom du joueur : " + nom);
        data.displayData();
    }
}
