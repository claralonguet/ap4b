import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Partie {
    private int idPartie;
    private String date;
    private List<Joueur> joueurs;
    private Enigme enigme;

    public Partie(int idPartie, String date, Enigme enigme) {
        this.idPartie = idPartie;
        this.date = date;
        this.joueurs = new ArrayList<>();
        this.enigme = enigme;
    }

    public void ajouterJoueur(Joueur joueur) {
        this.joueurs.add(joueur);
    }

    public void afficherDetails() {
        System.out.println("ID Partie: " + idPartie);
        System.out.println("Date: " + date);
        System.out.println("Joueurs: ");
        for (Joueur joueur : joueurs) {
            joueur.displayJoueur();
        }
    }

    public void jouer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue à la partie de Turing Machine!");

        System.out.println("Combien de joueurs?");
        int nombreJoueurs = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < nombreJoueurs; i++) {
            System.out.println("Entrez le nom du joueur " + (i + 1) + ": ");
            String nom = scanner.nextLine();
            Data data = new Data();
            Joueur joueur = new Joueur(nom, data);
            ajouterJoueur(joueur);
        }

        for (Joueur joueur : joueurs) {
            System.out.println("Tester des critères pour le joueur " + joueur.getNom());

            for (int i = 0; i < 3; i++) {
                System.out.println("Entrez un code pour tester le critère (format: bleu,jaune,violet): ");
                String[] codeStr = scanner.nextLine().split(",");
                int bleu = Integer.parseInt(codeStr[0]);
                int jaune = Integer.parseInt(codeStr[1]);
                int violet = Integer.parseInt(codeStr[2]);

                Code code = new Code(bleu, jaune, violet);
                boolean critereValide = enigme.testerCritere(code, i);
                joueur.maj("Critere" + i, Arrays.asList(bleu, jaune, violet));
                
                if (critereValide) {
                    System.out.println("Critère " + i + " validé!");
                } else {
                    System.out.println("Critère " + i + " non validé.");
                }
            }

            System.out.println("Voulez-vous tester un code pour trouver la solution? (oui/non)");
            String reponse = scanner.nextLine();
            if (reponse.equalsIgnoreCase("oui")) {
                System.out.println("Entrez un code pour tester (format: bleu,jaune,violet): ");
                String[] codeStr = scanner.nextLine().split(",");
                int bleu = Integer.parseInt(codeStr[0]);
                int jaune = Integer.parseInt(codeStr[1]);
                int violet = Integer.parseInt(codeStr[2]);

                Code code = new Code(bleu, jaune, violet);
                boolean codeValide = enigme.testerCode(code);
                
                joueur.maj("EssaiFinal", Arrays.asList(bleu, jaune, violet));

                if (codeValide) {
                    System.out.println("Le code testé est la solution!");
                } else {
                    System.out.println("Le code testé n'est pas la solution.");
                }
            }
        }

        System.out.println("Partie terminée! Détails de la partie:");
        afficherDetails();
    }

    public static void main(String[] args) {
        Enigme enigme = Enigme.creerEnigme("enigme.txt", 4);

        Partie partie = new Partie(1, "03/01/2025", enigme);
        partie.jouer();
    }
}
