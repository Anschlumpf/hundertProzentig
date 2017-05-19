import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Helfer für Benutzer Interaktion durch Konsole
 * Created by antje on 21.11.16.
 */
public class BenutzerInteractionsHelfer {

    /**
     * Spieler der an der Reihe ist. Start Spieler 1
     * True: Player 1, False: Player 2
     */
    private static boolean aktuellerSpieler;
    /**
     * aktuelle Runde (Wert zwischen 1 und 9)
     */
    private static int aktuelleRunde;
    /**
     * aktuelles Spielfeld übernommen vom Start
     */
    private static Spielfeld spielfeld;
    /**
     * Variable ob Spiel noch aktiv
     */
    private static boolean aktiv;

    /**
     * Key für Key-Value-Map bei Abfrage der Reihe
     */
    private static final String KEYREIHE = "KeyRow";
    /**
     * Key für Key-Value-Map bei Abfrage der Zelle
     */
    private static final String KEYZELLE = "KeyCell";


    /**
     * Standard-Konstruktor, übernimmt Spielfeld und setzt aktuellen Spieler und Runde
     * @param spielfeld
     */
    public BenutzerInteractionsHelfer(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
        aktuellerSpieler = true;
        aktuelleRunde =1;
        aktiv = true;

    }

    /**
     * Startet Scanner für Benutzereingabe
     * rekusiv -> ruft für jede Runde sich selbst auf bis Spiel beendet.
     */
    public static void starteScanner(){
        if (aktiv) {
            System.out.println(String.format("Runde %d, Player %d: ", aktuelleRunde, getSpielerInt()));
            Scanner scanner = new Scanner(System.in);

            if (scanner.hasNextLine()) {
                String consolenValue = scanner.nextLine();  //Eingabe in Variable speichern
                if (wertUeberpruefen(consolenValue)) {     //Wert prüfen ob passt
                    if (!rundeAbschliessen(consolenValue)) {  //Runde abschließen
                        System.out.println("Feld bereits belegt");  //Wenn Runde nicht abgeschlossen werden kann Feld belegt
                        starteScanner(); //Erneute Eingabe fordern
                    }
                } else {
                    System.out.println("Kein gültiger Wert! Gültige Wert: a1 A1 2a 2B ..."); //Ungültiger Wert
                    starteScanner(); //Erneute Eingabe fordern
                }
            } else {
                System.out.println("Kein Wert eingegeben!");    //Nur Enter gedrückt
                starteScanner(); //Erneute Eingabe fordern
            }
        }
    }

    /**
     * Runde abschließen indem Spieler auf Spielfeld setzt und Scanner erneut starten
     * @param zellString
     * @return true: Runde erfolgreich abgeschlossen; false: Feld belegt
     */
    private static boolean rundeAbschliessen(String zellString ){
        Map<String, Integer> reiheZelleMap= konvertiereZuZellenReihe(zellString);
        int aktuelleReihe = reiheZelleMap.get(KEYREIHE);
        int aktuelleZelle = reiheZelleMap.get(KEYZELLE);

        boolean rueckgabewert = spielfeld.aktualisiereSpielfeld(getSpielerInt(), aktuelleReihe, aktuelleZelle);

        if (rueckgabewert) {
            aktuellerSpieler = !aktuellerSpieler;
            aktuelleRunde++;
            starteScanner();
        }

        return rueckgabewert;
    }

    /**
     * Aktueller Spieler
     * @return 1: Spieler 1, 2: Spieler 2
     */
    private static int getSpielerInt(){
        return (aktuellerSpieler)?1:2;
    }

    /**
     * Überprüft ob Eingabe a,b,c,A,B,C und 1,2,3 enthält
     * @param wert Feldwert aus Konsoleneingabe
     * @return true: Feldwert möglich
     */
    private static boolean wertUeberpruefen(String wert) {
        if (!wert.isEmpty() && wert.length()==2){
            if (((wert.contains("a"))||(wert.contains("A"))||(wert.contains("b"))||(wert.contains("B"))||(wert.contains("c"))||(wert.contains("C"))) && ((wert.contains("1"))||(wert.contains("2"))||(wert.contains("3")))){
                return true;
            }
        }
        return false;
    }

    /**
     * Verarbeitet geprüften Feldwert aus Konsole zu Key-Value Paaren mit KEYZELLE und KEYREIHE als Schlüßel in Map
     * @param feldWert aus Konsolen Eingabe z.B. 2b oder A1
     * @return Map mit [KEYZELLE:2][KEYREIHE:2] für 2b
     */
    private static HashMap<String, Integer> konvertiereZuZellenReihe(String feldWert){
        HashMap<String, Integer> map = new HashMap<>();
        if (feldWert.contains("a")||(feldWert.contains("A"))){
            map.put(KEYZELLE,1);
        } else if (feldWert.contains("b")||(feldWert.contains("B"))){
            map.put(KEYZELLE,2);
        } else if (feldWert.contains("c")||(feldWert.contains("C"))){
            map.put(KEYZELLE,3);
        }

        if (feldWert.contains("1")){
            map.put(KEYREIHE,1);
        } else if (feldWert.contains("2")){
            map.put(KEYREIHE,2);
        } else if (feldWert.contains("3")){
            map.put(KEYREIHE,3);
        }
        return map;
    }

    /**
     * Spiel wurde nicht erneut gestartet
     */
    public void spielStoppen(){
        aktiv = false;
    }
}
