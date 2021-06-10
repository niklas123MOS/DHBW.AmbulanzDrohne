package test;

import city.City;
import city.Citypart;
import city.Human;
import drone.Drone;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

/* --- test cases in order 01 to 12 ---
    [01] Die Drohne wird vollständig durch den Builder erstellt
    [02] Das Stadtgebiet City wird vollständig aufgebaut
    [03] Erleidet ein Mensch eines Paares einen Herzinfarktt wird ein Notruf an das EC abgesetzt
    [04] Der Bot des EC empfängt den Notruf und die Positionsangabe des Anrufers
    [05] Für die Pfadberechnung akzeptiert das EC nur digital signierte Komponenten
    [06] Die Drohne mit der geringsten Entfernung zu der Position des Anrufers wird selektiert
    [07] Die eindeutige ID zu einem Gesicht wird korrekt ermittelt
    [08] Die Zentraleinheit der Drohne empfängt ein Event mit vollständigen Informationen (ID, Route)
    [09] Die Zentraleinheit übersetzt die Informationen zur Route in korrekte Kommandos der Drohne
    [10] Die Drohne landet neben dem Menschen, welcher den Notruf abgesetzt hat
    [11] Nach Landung der Drohne wird der Mensch mit dem Herzinfarkt erfolgreich reanimiert
    [12] Nach Reanimation bzw. dem Zurücklegen der Elektroden kehrt die Drohne zum Port zurück
*/


//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test1 {
    Drone drone = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
    City mosbach = new City();

    @BeforeAll
    public void setup() {

    }

    @Test
    //@Order(1)
    public void Test01() {
        //[01] Die Drohne wird vollständig durch den Builder erstellt
        //vier Hauptausleger
        Assertions.assertNotNull(drone.getMainBoom01());
        Assertions.assertNotNull(drone.getMainBoom02());
        Assertions.assertNotNull(drone.getMainBoom03());
        Assertions.assertNotNull(drone.getMainBoom04());
        // jeder Hauptausleger hat zwei Ausleger
        Assertions.assertEquals(2,drone.getMainBoom01().listUnits().size());
        Assertions.assertEquals(2,drone.getMainBoom02().listUnits().size());
        Assertions.assertEquals(2,drone.getMainBoom03().listUnits().size());
        Assertions.assertEquals(2,drone.getMainBoom04().listUnits().size());
        // Drohne besitzt zentrale Steuereinheit, LiDAR, Kamera, GPS, zwei Scheinwerfer, eine Box
        Assertions.assertNotNull(drone.getCentralUnit());
        Assertions.assertNotNull(drone.getLidar());
        Assertions.assertNotNull(drone.getCamera());
        Assertions.assertNotNull(drone.getGps());
        Assertions.assertNotNull(drone.getLight1());
        Assertions.assertNotNull(drone.getLight2());
        Assertions.assertNotNull(drone.getBox());

    }

    @Test
    //@Order(2)
    public void Test02() {
        //[02] Das Stadtgebiet City wird vollständig aufgebaut
        Citypart[][] mosbachCity = mosbach.getCityarea();
        //Matrixlänge = 1000
        Assertions.assertEquals(1000, mosbachCity.length);
        // Matrixbreite = 1000
        for (int k = 0; k < mosbachCity.length ; k++) {
            Assertions.assertEquals(1000, mosbachCity[k].length);
        }
        // Anzahl der Zeichen
        int countR = 0;
        int countS = 0;
        int countD = 0;
        int countE = 0;
        int countH = 0;


        for (int i = 0; i < mosbachCity.length ; i++) {
            for (int j = 0; j < mosbachCity[0].length ; j++) {
                if(mosbachCity[i][j].getType()=='R') {
                    countR++;
                } else if (mosbachCity[i][j].getType()=='S') {
                    countS++;
                } else if (mosbachCity[i][j].getType()=='D') {
                    countD++;
                } else if (mosbachCity[i][j].getType()=='E') {
                    countE++;
                } else if (mosbachCity[i][j].getType()=='H') {
                    countH++;
                }

            }
        }
        Assertions.assertEquals(32500,countR);
        Assertions.assertEquals(2500,countS);
        Assertions.assertEquals(4,countD);
        Assertions.assertEquals(1,countE);
        Assertions.assertEquals(2000,countH);

        // um H entweder Leerzeichen bzw. Unterstrich, oder anderes H
        boolean humanTest = false;
        int countH1 = 0;
        for (int i = 1; i < mosbachCity.length-1 ; i++) {
            for (int j = 1; j < mosbachCity[i].length-1 ; j++) {

                 if (mosbachCity[i][j].equals("H")) {
                    Citypart humanLeft = mosbachCity[i][j-1];
                    Citypart humanRight = mosbachCity[i][j+1];
                    Citypart humanAbove = mosbachCity[i-1][j];
                    Citypart humanBottom = mosbachCity[i+1][j];

                   ArrayList<Citypart> neighbors = new ArrayList<>();
                   neighbors.add(humanLeft);
                   neighbors.add(humanRight);
                   neighbors.add(humanAbove);
                   neighbors.add(humanBottom);

                     for (Citypart neighbor: neighbors) {
                         if (neighbor.getType()==('H')) {
                             countH1++;
                         }
                     }
                    if (countH1 == 1) {
                        humanTest = mosbachCity[i][j - 1].getType()==(' ') || mosbachCity[i][j - 1].getType()==('H') &&
                                mosbachCity[i][j + 1].getType()==(' ') || mosbachCity[i][j + 1].getType()==('H') &&
                                mosbachCity[i - 1][j].getType()==(' ') || mosbachCity[i - 1][j].getType()==('H') &&
                                mosbachCity[i + 1][j].getType()==(' ') || mosbachCity[i + 1][j].getType()==('H');
                    }
                     Assertions.assertTrue(humanTest);
                }
                 countH1 = 0;
                 humanTest = false;
            }
        }

    }

    @Test
    //@Order(3)
    public void Test03() {
        //[03] Erleidet ein Mensch eines Paares einen Herzinfarkt wird ein Notruf an das EC abgesetzt

    }

    @Test
    //@Order(4)
    public void Test04() {
        // [04] Der Bot des EC empfängt den Notruf und die Positionsangabe des Anrufers

    }

    @Test
    //@Order(5)
    public void Test05() {
        //[05] Für die Pfadberechnung akzeptiert das EC nur digital signierte Komponenten

    }

    @Test
    //@Order(6)
    public void Test06() {
        //[06] Die Drohne mit der geringsten Entfernung zu der Position des Anrufers wird selektiert

    }

    @Test
    //@Order(7)
    public void Test07() {
        //[07] Die eindeutige ID zu einem Gesicht wird korrekt ermittelt

    }

    @Test
    //@Order(8)
    public void Test08() {
        //[08] Die Zentraleinheit der Drohne empfängt ein Event mit vollständigen Informationen (ID, Route)

    }

    @Test
    //@Order(9)
    public void Test09() {
        // [09] Die Zentraleinheit übersetzt die Informationen zur Route in korrekte Kommandos der Drohne

    }

    @Test
    //@Order(10)
    public void Test10() {
        //[10] Die Drohne landet neben dem Menschen, welcher den Notruf abgesetzt hat

    }

    @Test
    //@Order(11)
    public void Test11() {
        //[11] Nach Landung der Drohne wird der Mensch mit dem Herzinfarkt erfolgreich reanimiert

    }

    @Test
    //@Order(12)
    public void Test12() {
        // [12] Nach Reanimation bzw. dem Zurücklegen der Elektroden kehrt die Drohne zum Port zurück

    }
}
