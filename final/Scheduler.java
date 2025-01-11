import screens.*;

import java.util.ArrayList;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.util.concurrent.CountDownLatch;

import errors.WrongSizeException;
import game.*;


public class Scheduler {
    private int w; 
    private int h;

    private ArrayList<Joueur> players;
    private ArrayList<Boolean> pronostics; 
    private ArrayList<Code> codes; 

    public Scheduler(){
        this.w = 1000;
        this.h = 800;

        this.players = new ArrayList<>();
        this.pronostics = new ArrayList<>();
        this.codes = new ArrayList<>();
    }

    public void initializeArrays(){
        this.pronostics.clear();
        this.codes.clear();
    }

    public Enigme createEnigme(String filename, int n){
        return Enigme.creerEnigme(filename, n);
    }

    private class ScreenListener extends WindowAdapter {
        private CountDownLatch latch;
    
        public ScreenListener(CountDownLatch latch) {
            this.latch = latch;
        }
    
        @Override
        public void windowClosed(WindowEvent e) {
            // Libère le latch si fourni
            if (latch != null) {
                latch.countDown();
            }
        }
    }
  
    public void launchScreenInstructions() {
        CountDownLatch latch = new CountDownLatch(1);
    
        LaunchScreen launchScreen = new LaunchScreen("Turing Machine", 1300, h, null);
        launchScreen.setVisible(true);

        launchScreen.addWindowListener(new ScreenListener(latch));
    
        try {
            latch.await();
            players = launchScreen.getPlayers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void mainScreenInstructions(Enigme enigma) {
        for (Joueur player : players) {
            CountDownLatch latch = new CountDownLatch(1);
    
            MainScreen mainScreen = new MainScreen("Turing Machine", 1300, h, player, enigma);
            mainScreen.setVisible(true);

            mainScreen.addWindowListener(new ScreenListener(latch));

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pronosticScreenInstructions(){
        for (Joueur player : players) {
            CountDownLatch latch = new CountDownLatch(1);
    
            PronosticScreen pronosticScreen = new PronosticScreen("Pronostics", w, h, player);
            pronosticScreen.setVisible(true);

            pronosticScreen.addWindowListener(new ScreenListener(latch));

            try {
                latch.await();
                pronostics.add(pronosticScreen.getPronostic());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void codeGuessScreenInstructions(){
        for (int i = 0; i < players.size(); i++) {
            //Si le joueur a pronostiqué connaître le code, on lui montre l'écran pour deviner le code
            if(pronostics.get(i)){
                CountDownLatch latch = new CountDownLatch(1);
        
                CodeGuessScreen codeGuessScreen = new CodeGuessScreen("Turing Machine", w, h, players.get(i));
                codeGuessScreen.setVisible(true);

                codeGuessScreen.addWindowListener(new ScreenListener(latch));

                try {
                    latch.await();
                    //Récupère le code
                    try {
                        Code c = new Code(codeGuessScreen.getCode());
                        codes.add(c);
                    } catch (WrongSizeException ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //Ajoute un code fantôme pour indiquer que le joueur n'a pas pronostiqué avoir trouvé
                Code c = new Code(0,0,0);
                codes.add(c);
            }
        }
    }

    public void endScreenInstructions(Joueur joueur, Enigme enigma, String type) {
        CountDownLatch latch = new CountDownLatch(1);

        String title = "Bravo !";
        if(type == "defeat"){
            title = "Perdu...";
        }

        EndScreen defeatScreen = new EndScreen(title, w, h, joueur, type, enigma.getSolution());
        defeatScreen.setVisible(true);

        defeatScreen.addWindowListener(new ScreenListener(latch));

        try {
            latch.await(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scheduler s = new Scheduler();

        boolean gameRunning = true;
        ArrayList<Joueur> winners = new ArrayList<>();
        ArrayList<Joueur> eliminated = new ArrayList<>();

        Joueur lessCriteriaPlayer = new Joueur();

        //LaunchScreen
        s.launchScreenInstructions();

        //Génère une énigme pour la partie
        Enigme enigma = s.createEnigme("enigme.txt", 4);

        //Tant que personne ne pronostique avoir trouvé la solution, alors on continue
        while (gameRunning) {
            s.initializeArrays();

            //MainScreen
            s.mainScreenInstructions(enigma);

            //PronosticScreen
            s.pronosticScreenInstructions();
            
            //CodeGuessScreen
            s.codeGuessScreenInstructions();

            //Vérifie si quelqu'un a gagné
            for (int i = 0; i < s.pronostics.size(); i++) {
                if(s.pronostics.get(i)){
                    if(s.codes.get(i).equals(enigma.getSolution())){
                        winners.add(s.players.get(i));
                    } else {
                        eliminated.add(s.players.get(i));
                    }
                }
            }
            if(winners.size() > 0){
                //Signifie qu'au moins un des joueurs connaît la réponse
                for (Joueur player : s.players) {
                    if(!winners.contains(player)){
                        eliminated.add(player);
                    }
                }
                gameRunning = false;
            }
            
            //Elimine les joueurs perdants
            if(eliminated.size() != 0){
                for (Joueur joueur : eliminated) {
                    s.players.remove(joueur);
                }
            }
        }

        if(winners.size() > 1){
            int minCrit = Integer.MAX_VALUE;
            for (Joueur j : winners) {
                if(j.getTrials() < minCrit){
                    lessCriteriaPlayer = j;
                    minCrit = j.getTrials();
                }
            }
        }

        for (Joueur j : winners) {
            if (!j.equals(lessCriteriaPlayer)) {
                eliminated.add(j);
            }
        }

        //Montre l'écran de défaite au(x) perdant(s)
        for (Joueur looser : eliminated) {
            s.endScreenInstructions(looser, enigma, "defeat");
        }
        if(!lessCriteriaPlayer.isNull()) {
            s.endScreenInstructions(lessCriteriaPlayer, enigma, "victory");
        }
        
        System.exit(0);
    }
}
