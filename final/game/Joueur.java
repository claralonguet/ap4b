package game;

import errors.InvalidDataException;

public class Joueur {
    private String nom;
    private String skin;
    private Data data;
    private int testedCriteria; 

    public Joueur(String nom, String skin) {
        this.nom = nom;
        this.skin = skin; 
        this.data = new Data();
        this.testedCriteria = 0;
    }

    public Joueur(){
        this.nom = null; 
        this.skin = null;
        this.data = null;
    }

    public boolean isNull(){
        return nom == null && skin == null && data == null;
    }

    // Getter pour nom
    public String getNom() {
        return nom;
    }

    public String getSkin(){
        return skin;
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
    public void majCritere(boolean criterion, int tour, int col) {
        //System.out.println("Mise à jour des critères pour le joueur : " + nom);
        try {
            data.addCritere(criterion, tour, col); 
        } catch (InvalidDataException e) {
            System.out.println("Erreur lors de la mise à jour des critères : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un code
    public void majCode(Integer codeEntry, int tour) {
        //System.out.println("Mise à jour des codes pour le joueur : " + nom);
        try {
            data.addCode(codeEntry, tour);
        } catch (InvalidDataException e) {
            System.out.println("Erreur lors de la mise à jour des codes : " + e.getMessage());
        }
    }

    public void majNumber(boolean isChecked, int number, int col){
        data.addNumber(isChecked, number, col); 
    }

    public void majTrials(int n){
        testedCriteria += n;
    }

    public int getTrials(){
        return testedCriteria;
    }

 ///afficher les informations du joueur
    public void displayJoueur() {
        System.out.println("Nom du joueur : " + nom);
        data.displayData();
    }
}
