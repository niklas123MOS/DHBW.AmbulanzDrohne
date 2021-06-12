package test;

import Configuration.Configuration;
import Configuration.Algorithm;
import EmergencyCenter.EmergencyCenter;
import EmergencyCenter.UnsignedComponentException;
import city.*;
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


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AmbulanceTest {
    Drone drone;
    City mosbach;

    @BeforeAll
    public void setup() {
        drone = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
        mosbach = new City();
        mosbach.startEmergency();
    }

    @Test
    public void Test01() {
        //[01] Die Drohne wird vollständig durch den Builder erstellt
        //vier Hauptausleger
        Assertions.assertNotNull(drone.getMainBoom01());
        Assertions.assertNotNull(drone.getMainBoom02());
        Assertions.assertNotNull(drone.getMainBoom03());
        Assertions.assertNotNull(drone.getMainBoom04());
        // jeder Hauptausleger hat zwei Ausleger
        Assertions.assertEquals(2, drone.getMainBoom01().listUnits().size());
        Assertions.assertEquals(2, drone.getMainBoom02().listUnits().size());
        Assertions.assertEquals(2, drone.getMainBoom03().listUnits().size());
        Assertions.assertEquals(2, drone.getMainBoom04().listUnits().size());
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
    public void Test02() {
        //[02] Das Stadtgebiet City wird vollständig aufgebaut
        Citypart[][] mosbachCity = mosbach.getCityarea();
        //Matrixlänge = 1000
        Assertions.assertEquals(1000, mosbachCity.length);
        // Matrixbreite = 1000
        for (int k = 0; k < mosbachCity.length; k++) {
            Assertions.assertEquals(1000, mosbachCity[k].length);
        }
        // Anzahl der Zeichen
        int countR = 0;
        int countS = 0;
        int countD = 0;
        int countE = 0;
        int countH = 0;


        for (int i = 0; i < mosbachCity.length; i++) {
            for (int j = 0; j < mosbachCity[0].length; j++) {
                if (mosbachCity[i][j].getType() == 'R') {
                    countR++;
                } else if (mosbachCity[i][j].getType() == 'S') {
                    countS++;
                } else if (mosbachCity[i][j].getType() == 'D') {
                    countD++;
                } else if (mosbachCity[i][j].getType() == 'E') {
                    countE++;
                } else if (mosbachCity[i][j].getType() == 'H') {
                    countH++;
                }

            }
        }
        Assertions.assertEquals(32500, countR);
        Assertions.assertEquals(2500, countS);
        Assertions.assertEquals(4, countD);
        Assertions.assertEquals(1, countE);
        Assertions.assertEquals(2000, countH);

        // um H entweder Leerzeichen bzw. Unterstrich, oder anderes H
        boolean humanTest = false;
        int countH1 = 0;
        for (int i = 1; i < mosbachCity.length - 1; i++) {
            for (int j = 1; j < mosbachCity[i].length - 1; j++) {

                if (mosbachCity[i][j].equals("H")) {
                    Citypart humanLeft = mosbachCity[i][j - 1];
                    Citypart humanRight = mosbachCity[i][j + 1];
                    Citypart humanAbove = mosbachCity[i - 1][j];
                    Citypart humanBottom = mosbachCity[i + 1][j];

                    ArrayList<Citypart> neighbors = new ArrayList<>();
                    neighbors.add(humanLeft);
                    neighbors.add(humanRight);
                    neighbors.add(humanAbove);
                    neighbors.add(humanBottom);

                    for (Citypart neighbor : neighbors) {
                        if (neighbor.getType() == ('H')) {
                            countH1++;
                        }
                    }
                    if (countH1 == 1) {
                        humanTest = mosbachCity[i][j - 1].getType() == (' ') || mosbachCity[i][j - 1].getType() == ('H') &&
                                mosbachCity[i][j + 1].getType() == (' ') || mosbachCity[i][j + 1].getType() == ('H') &&
                                mosbachCity[i - 1][j].getType() == (' ') || mosbachCity[i - 1][j].getType() == ('H') &&
                                mosbachCity[i + 1][j].getType() == (' ') || mosbachCity[i + 1][j].getType() == ('H');
                    }
                    Assertions.assertTrue(humanTest);
                }
                countH1 = 0;
                humanTest = false;
            }
        }

    }

    @Test
    public void Test03() {
        //[03] Erleidet ein Mensch eines Paares einen Herzinfarkt wird ein Notruf an das EC abgesetzt

        int botRow = EmergencyCenter.instance.getBot().getEmergencyRow();
        int botCol = EmergencyCenter.instance.getBot().getEmergencyCol();
        char[][] face = EmergencyCenter.instance.getBot().getEmergencyFace();

        //initial BotRow equals -1, when its not -1, it received data from smartphone
        Assertions.assertNotEquals(-1, botRow);
        Assertions.assertNotEquals(-1, botCol);
        Assertions.assertNotNull(face);
        Assertions.assertEquals(25, face.length);
        Assertions.assertEquals(25, face[0].length);


    }

    @Test
    public void Test04() {
        // [04] Der Bot des EC empfängt den Notruf und die Positionsangabe des Anrufers
        int landingPlaceRow = mosbach.getEmergencyRow();
        int landingPlaceCol = mosbach.getEmergencyCol() - 1; //landing next to the human

        int botRow = EmergencyCenter.instance.getBot().getEmergencyRow();
        int botCol = EmergencyCenter.instance.getBot().getEmergencyCol();

        Assertions.assertEquals(landingPlaceRow, botRow);
        Assertions.assertEquals(landingPlaceCol, botCol);


    }

    @Test
    public void Test05() {
        //[05] Für die Pfadberechnung akzeptiert das EC nur digital signierte Komponenten

        City mosbach2 = new City();

        Configuration.instance.pathAlgorithm = Algorithm.AStarUnsigned;


        mosbach2.startEmergency();


    }

    @Test
    public void Test06() {
        //[06] Die Drohne mit der geringsten Entfernung zu der Position des Anrufers wird selektiert
        int row = mosbach.getEmergencyRow();
        int col = mosbach.getEmergencyCol();
        int correspondingDronePortId = -1;

        if (row <= 499 && col <= 499) {
            correspondingDronePortId = 0;
        } else if (row <= 499 && col >= 500) {
            correspondingDronePortId = 1;
        } else if (row >= 500 && col <= 499) {
            correspondingDronePortId = 2;
        } else if (row >= 500 && col >= 500) {
            correspondingDronePortId = 3;
        }

        Assertions.assertEquals(EmergencyParameters.instance.dronePortID, correspondingDronePortId);
    }


    @Test
    public void Test07() {
        //[07] Die eindeutige ID zu einem Gesicht wird korrekt ermittelt

        Smartphone smartphone = new Smartphone();
        Human human = new Human('H', 0, 0, mosbach);

        char[][] humanFace = human.getFace();
        String humanID = human.getId();

        smartphone.scanFaceAndID(human);

        char[][] scannedFace = smartphone.getScannedface();
        String scannesID = smartphone.getHumanID();

        Assertions.assertEquals(humanFace, scannedFace);
        Assertions.assertEquals(humanID, scannesID);

    }

    @Test
    public void Test08() {
        //[08] Die Zentraleinheit der Drohne empfängt ein Event mit vollständigen Informationen (ID, Route)

        DronePort port = mosbach.getDronePorts().get(EmergencyParameters.instance.dronePortID);

        Assertions.assertNotNull(port.getRoute());
        Assertions.assertEquals(mosbach.getHumanWithHeartAttack().getId(), port.getHumanID());


    }

    @Test
    public void Test09() {
        // [09] Die Zentraleinheit übersetzt die Informationen zur Route in korrekte Kommandos der Drohne

        Drone drone = mosbach.getDronePorts().get(EmergencyParameters.instance.dronePortID).getDrone();

        Assertions.assertNotNull(drone.getRoute());

        int landingPlaceRow = mosbach.getEmergencyRow();
        int landingPlaceCol = mosbach.getEmergencyCol() - 1; //landing next to the human

        Assertions.assertEquals(landingPlaceRow, drone.getEmergencyRow());
        Assertions.assertEquals(landingPlaceCol, drone.getEmergencyCol());


    }

    @Test
    public void Test10() {
        //[10] Die Drohne landet neben dem Menschen, welcher den Notruf abgesetzt hat
        Drone drone = mosbach.getDronePorts().get(EmergencyParameters.instance.dronePortID).getDrone();

        int landingPlaceRow = mosbach.getEmergencyRow();
        int landingPlaceCol = mosbach.getEmergencyCol() - 1; //landing next to the human

        Assertions.assertEquals(landingPlaceRow, drone.getEmergencyRow());
        Assertions.assertEquals(landingPlaceCol, drone.getEmergencyCol());

    }

    @Test
    public void Test11() {
        //[11] Nach Landung der Drohne wird der Mensch mit dem Herzinfarkt erfolgreich reanimiert

        Assertions.assertEquals(false, mosbach.getHumanWithHeartAttack().getHasHeartAttack());

    }

    @Test
    public void Test12() {
        // [12] Nach Reanimation bzw. dem Zurücklegen der Elektroden kehrt die Drohne zum Port zurück

        Drone drone = mosbach.getDronePorts().get(EmergencyParameters.instance.dronePortID).getDrone();
        DronePort dronePort = mosbach.getDronePorts().get(EmergencyParameters.instance.dronePortID);

        int droneRow = drone.getRow();
        int droneCol = drone.getCol();

        int portRow = dronePort.getRow();
        int portCol = dronePort.getCol();

        Assertions.assertEquals(portRow, droneRow);
        Assertions.assertEquals(portCol, droneCol);

    }
}
