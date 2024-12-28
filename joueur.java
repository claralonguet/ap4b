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

    // Méthode pour mettre à jour le joueur (ex: ajouter un nouveau code testé et sa réponse)
    public void maj(String key, List<Integer> value) {
        System.out.println("Mise à jour des données pour le joueur : " + nom);
        data.addCode(key, value);
    }

 ///afficher les informations du joueur
    public void displayJoueur() {
        System.out.println("Nom du joueur : " + nom);
        data.displayData();
    }
}
