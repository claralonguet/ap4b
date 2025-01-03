import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;


public class TuringMachine {
    public static void main(String[] args) {
        try {
            // Créer une énigme à partir d'un fichier
            Enigme enigme = Enigme.creerEnigme("enigme.txt", 4);

            // Afficher la solution de l'énigme
            System.out.println("Solution de l'enigme : " + enigme.getSolution());

            // Créer un code pour tester
            Code codeTest = new Code(1,3,4);
            System.out.println("\nCode teste : " + codeTest);

            // Tester l'ensemble des critères
            boolean tousCriteresValides = true;
            for (int i = 0; i < enigme.getCriteres().size(); i++) {
                boolean critereValide = enigme.testerCritere(codeTest, i);
                System.out.println("Critere " + i + " valide : " + critereValide);
                if (!critereValide) {
                    tousCriteresValides = false;
                }
            }


            // Vérifier si le code est la solution
            if (tousCriteresValides && enigme.testerCode(codeTest)) {
                System.out.println("Le code teste est la solution !");
            } else {
                System.out.println("Le code teste n'est pas la solution.");
            }


            codeTest = new Code(2,3,3);
            System.out.println("\nCode teste : " + codeTest);

            for (int i = 0; i < enigme.getCriteres().size(); i++) {
                boolean critereValide = enigme.testerCritere(codeTest, i);
                System.out.println("Critere " + i + " valide : " + critereValide);
                if (!critereValide) {
                    tousCriteresValides = false;
                }
            }

            // Vérifier si le code est la solution
            if (tousCriteresValides && enigme.testerCode(codeTest)) {
                System.out.println("Le code teste est la solution !");
            } else {
                System.out.println("Le code teste n'est pas la solution.");
            }
        } catch (Exception e) {
            System.err.println("Erreur dans le programme : " + e.getMessage());
        }
    }
}
