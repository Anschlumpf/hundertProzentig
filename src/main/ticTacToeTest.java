/**
 * Test zur Überprüfung ob Spielfeld funktioniert
 * Created by antje on 21.11.16.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ticTacToeTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    /**
     * Test der ein Komplettes Spiel auf Spielfeld ausführt
     */
    public void firstGame() {
        Spielfeld spielfeld = new Spielfeld();
        String runde0 = "  - A - . - B - . - C -\n|       .       .       |\n1       .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2       .       .       2\n|       .       .       |\n...............\n|       .       .       |\n3       .       .       3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        assertEquals(runde0, outContent.toString());
        String runde1 = "  - A - . - B - . - C -\n|       .       .       |\n1   X   .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2       .       .       2\n|       .       .       |\n...............\n|       .       .       |\n3       .       .       3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        spielfeld.aktualisiereSpielfeld(1,1,1); //Spieler 1 Feld A1
        assertEquals(runde0+runde1, outContent.toString());
        String runde2 = "  - A - . - B - . - C -\n|       .       .       |\n1   X   .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2       .       .       2\n|       .       .       |\n...............\n|       .       .       |\n3       .       .   O   3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        spielfeld.aktualisiereSpielfeld(2,3,3); //Spieler 2 Feld C3
        assertEquals(runde0+runde1+runde2, outContent.toString());
        String runde3 = "  - A - . - B - . - C -\n|       .       .       |\n1   X   .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2   X   .       .       2\n|       .       .       |\n...............\n|       .       .       |\n3       .       .   O   3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        spielfeld.aktualisiereSpielfeld(1,2,1); //Spieler 1 Feld B1
        assertEquals(runde0+runde1+runde2+runde3, outContent.toString());
        String runde4 = "  - A - . - B - . - C -\n|       .       .       |\n1   X   .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2   X   .       .       2\n|       .       .       |\n...............\n|       .       .       |\n3   O   .       .   O   3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        spielfeld.aktualisiereSpielfeld(2,3,1); //Spieler 2 Feld C1
        assertEquals(runde0+runde1+runde2+runde3+runde4, outContent.toString());
        String runde5 = "  - A - . - B - . - C -\n|       .       .       |\n1   X   .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2   X   .   X   .       2\n|       .       .       |\n...............\n|       .       .       |\n3   O   .       .   O   3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        spielfeld.aktualisiereSpielfeld(1,2,2); //Spieler 1 Feld B2
        assertEquals(runde0+runde1+runde2+runde3+runde4+runde5, outContent.toString());
        String runde6 = "  - A - . - B - . - C -\n|       .       .       |\n1   X   .       .       1\n|       .       .       |\n...............\n|       .       .       |\n2   X   .   X   .       2\n|       .       .       |\n...............\n|       .       .       |\n3   O   .   O   .   O   3\n|       .       .       |\n  - A - . - B - . - C -\n\n";
        spielfeld.aktualisiereSpielfeld(2,3,2); //Spieler 2 Feld C2
        assertEquals(runde0+runde1+runde2+runde3+runde4+runde5+runde6+"Glueckwunsche. Spieler 2 hat gewonnen!\n", outContent.toString());
    }
}
