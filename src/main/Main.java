import java.util.*;

/**
 * Main-Klasse bzw. Start Tic Tac Tow Spiel
 * Created by antje on 19.11.16.
 */
public class Main {

    public static void main(String args[]){
        start();
    }

    /**
     * Variabel für Kommunikarion mit Konseole
     */
    private static BenutzerInteractionsHelfer benutzerInteraction;

    /**
     * Konfigurierunge für Start
     */
    private static void setup(){
        Spielfeld spielfeld = new Spielfeld();
        benutzerInteraction = new BenutzerInteractionsHelfer(spielfeld);

        spielfeld.addObserver(new GewonnenListener());
    }

    /**
     * Setup durchführen und Einlesen aus Konsole starten
     */
    private static void start(){
        setup();
        benutzerInteraction.starteScanner();
    }

    /**
     * Spiel ist gewonnen und wie geht es weiter?
     */
    private static void startFinishScanner() {
        System.out.print("Spiel beendet.\nErneut spielen? (Y/n)\n");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String consolenValue = scanner.nextLine();
            if (consolenValue.equals("Y")){ //Ja Spiel erneut starten.
                start();
            } else if(consolenValue.equals("n")){    //Nein BenutzerInteractionsHelfer mitteilen, dass Spiel beendet -> Prozess beendet
                benutzerInteraction.spielStoppen();
            } else {
                System.out.println("Falsche Eingabe");  //Nicht ja oder nein -> erneut fragen
                startFinishScanner();
            }
        }
    }

    /**
     * Wird aufgerufen wenn Spielfeld erkennt dass ein oder zwei Spieler gewonnen haben.
     */
    private static class GewonnenListener implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            startFinishScanner();
        }
    }
}
