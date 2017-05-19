package com.antje.app;

import java.util.Observable;

/**
 * Klasse, die ein Tic-Tac-Toe Spielfeld erzeugt mit 3x3 Feldern
 * <p>
 * Created by antje on 20.11.16.
 */
public class Spielfeld extends Observable {

    //region Variablen

    /**
     * Spielfeld mit 3 Reihen à 3 Zellen
     * Zellenzustand 0 ( ): noch nicht belegt
     * Zellenzustand 1 (X): Spiele 1 platziert
     * Zellenzustand 2 (O): Spiele 2 platziert
     */
    private final char[][] spielfeld;
    /**
     * Anzahl der erfolgreich gespielten Runden (max. 9)
     */
    private int rundenanzahl = 0;

    //endregion
    //region Konstanten

    /**
     * Textzeichen für Zelle von Spieler 1
     */
    private final char ZEICHENSPIELER1 = 'X';
    /**
     * Textzeichen für Zelle von Spieler 2
     */
    private final char ZEICHENSPIELER2 = 'O';
    /**
     * Leerzeichen Char
     */
    private final char LEERZEICHENCHAR = 32;

    //endregion

    /**
     * Standartkonstruktor erzeigt Spielfeld-Multi-Array mit Leerzeichen für Consolen-Konvertierung
     */
    public Spielfeld() {
        spielfeld = new char[][]{{LEERZEICHENCHAR, LEERZEICHENCHAR, LEERZEICHENCHAR}, {LEERZEICHENCHAR, LEERZEICHENCHAR, LEERZEICHENCHAR}, {LEERZEICHENCHAR, LEERZEICHENCHAR, LEERZEICHENCHAR}};
        ausgabeSpielfeldInKonsole();
    }

    /**
     * Ausgabe des Spielfeldes in Console
     */
    private void ausgabeSpielfeldInKonsole() {
        //Zuweisung der Zellenvariablen für formatierte Ausgabe
        final String A1 = String.valueOf(this.spielfeld[0][0]);
        final String A2 = String.valueOf(this.spielfeld[0][1]);
        final String A3 = String.valueOf(this.spielfeld[0][2]);
        final String B1 = String.valueOf(this.spielfeld[1][0]);
        final String B2 = String.valueOf(this.spielfeld[1][1]);
        final String B3 = String.valueOf(this.spielfeld[1][2]);
        final String C1 = String.valueOf(this.spielfeld[2][0]);
        final String C2 = String.valueOf(this.spielfeld[2][1]);
        final String C3 = String.valueOf(this.spielfeld[2][2]);

        System.out.print(String.format("  - A - . - B - . - C -\n|       .       .       |\n1   %s   .   %s   .   %s   1\n|       .       .       |\n...............\n|       .       .       |\n2   %s   .   %s   .   %s   2\n|       .       .       |\n...............\n|       .       .       |\n3   %s   .   %s   .   %s   3\n|       .       .       |\n  - A - . - B - . - C -\n\n", A1, A2, A3, B1, B2, B3, C1, C2, C3));
    }


    /**
     * Zelle in Spielfeld belegen
     *
     * @param spieler Akuteller Spieler (1 oder 2)
     * @param reihe   Aktuelle Reihe (1,2 oder 3)
     * @param zelle   Aktuelle Zelle (1,2 oder 3)
     * @return true: Aktualisierung erfolgreich; false: Feld belegt
     */
    public boolean aktualisiereSpielfeld(int spieler, int reihe, int zelle) {
        if (istGueltigerSpieler(spieler) && istGueltigeReihe(reihe) && istGueltigeZelle(zelle)) { //Falls gültiger Spieler
            if (istFeldBelegt(reihe, zelle)) {  //und Feld nicht belegt
                return false; //Aktualisierung Fehlgeschlagen -> Feld belegt
            }
            setzZeichen(spieler, reihe, zelle); //Feld belegen
            ausgabeSpielfeldInKonsole();    //Feld in Konsole ausgeben
            ueberpruefeObGewonnen();    //Überprüfen ob bereits wer gewonnen hat
            rundenanzahl++;         //Rundenanzahl erhöhen
            return true;    //Aktualisierung erfolgreich
        } else {
            throw new IllegalArgumentException("Ungültiger Wert für ${argument}: ${value}");
        }
    }

    /**
     * Prüft ob Zelle im Spielfeld vorhanden ist
     *
     * @param zelle zu überprüfende Zelle als int
     * @return true: Zelle-Wert 1,2 oder 3
     */
    private boolean istGueltigeZelle(int zelle) {
        return zelle > 0 && zelle <= 3;
    }

    /**
     * Prüft ob Reihe im Spielfeld vorhanden ist
     *
     * @param reihe zu überprüfende Reihe als int
     * @return true: Reihe-Wert 1,2 oder 3
     */
    private boolean istGueltigeReihe(int reihe) {
        return reihe > 0 && reihe <= 3;
    }

    /**
     * Prüft ob Spieler gülitig
     *
     * @param spieler zu überprüfende Spieler als int
     * @return true: Spieler 1 oder 2
     */
    private boolean istGueltigerSpieler(int spieler) {
        return spieler > 0 && spieler <= 2;
    }

    /**
     * Überprüft ob das Feld bereits von einem Spieler belegt wurde
     *
     * @param reihe zu überprüfende Reihe (1,2,3)
     * @param zelle zu überprüfende Zelle (1,2,3)
     * @return true: Feld frei, false; Feld belegt
     */
    private boolean istFeldBelegt(int reihe, int zelle) {
        return (spielfeld[reihe - 1][zelle - 1] != LEERZEICHENCHAR);
    }

    /**
     * Feld von Spieler belegen
     *
     * @param spieler aktueller Spieler (1,2)
     * @param reihe   zu belegende Reihe (1,2,3)
     * @param zelle   zu belegendes Feld (1,2,3)
     */
    private void setzZeichen(int spieler, int reihe, int zelle) {
        if (spieler == 1) { //Feld für Spieler 1 belegen
            spielfeld[reihe - 1][zelle - 1] = ZEICHENSPIELER1;
        } else {    //sonst Feld für Spieler 2 belegen
            spielfeld[reihe - 1][zelle - 1] = ZEICHENSPIELER2;
        }
    }

    /**
     * Überprüfung ob ein Spieler bereits gewonnen hat
     *
     * @return true: Gewonnen oder 9. Runden gespielt, false: noch kein Gewinner
     */
    private void ueberpruefeObGewonnen() {
        if (rundenanzahl > 3) { //Vor Runde 3 kann keiner gewinnen.
            if (reiheGewonnen()) return;
            if (spalteGewonnen()) return;
            if (diagonalGewonnen()) return;
            if (nachRundenGewonnen()) return;
        }
    }

    /**
     * Überprüft ob Rundenanzahl gleich neun ist
     *
     * @return true: rundenanzahl == 8
     */
    private boolean nachRundenGewonnen() {
        if (rundenanzahl == 8) {
            System.out.println("Glückwunsch. Beide Spieler haben gewonnen!");
            notifyObservers(); //Spiel beendet
            return true;
        }
        return false;
    }

    /**
     * Überprüft ob ein Spieler eine Schräge belegt hat
     *
     * @return true: z.B. A1==B2==C3
     */
    private boolean diagonalGewonnen() {
        if (spielfeld[1][1] != LEERZEICHENCHAR && ((spielfeld[0][0] != LEERZEICHENCHAR && spielfeld[0][0] == spielfeld[1][1] && spielfeld[1][1] == spielfeld[2][2])) || ((spielfeld[0][2] != LEERZEICHENCHAR && spielfeld[0][2] == spielfeld[1][1] && spielfeld[1][1] == spielfeld[2][0]))) {
            gratulieren(spielfeld[1][1]);
            return true;
        }
        return false;
    }

    /**
     * Überprüft ob eine Spalte komplett von einem Spieler belegt ist.
     *
     * @return true: z.B. C1==C2==C3
     */
    private boolean spalteGewonnen() {
        for (int i = 0; i <= 2; i++) {
            if (spielfeld[i][0] != LEERZEICHENCHAR && spielfeld[i][0] == spielfeld[i][1] && spielfeld[i][1] == spielfeld[i][2]) {
                gratulieren(spielfeld[i][0]);
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft ob eine Reihe komplett belegt von einem Spieler
     *
     * @return true: z.B. A1==B1==C1
     */
    private boolean reiheGewonnen() {
        for (char[] row : spielfeld) {
            if (row[0] != LEERZEICHENCHAR && row[0] == row[1] && row[1] == row[2]) {
                gratulieren(row[0]);
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt Glückwunsch in Konsole aus und
     * Sendet Nachricht an Observer-Klasse
     *
     * @param zellenWert ein Wert der zu gewonnen Reihe o.Ä
     */
    private void gratulieren(char zellenWert) {
        if (zellenWert == ZEICHENSPIELER1) {
            System.out.println("Glueckwunsche. Spieler 1 hat gewonnen!");
        } else if (zellenWert == ZEICHENSPIELER2) {
            System.out.println("Glueckwunsche. Spieler 2 hat gewonnen!");
        }
        setChanged(); //Observer-Klasse benachrightigen - Spiel beendet
        notifyObservers(); //Spiel beendet
    }
}
