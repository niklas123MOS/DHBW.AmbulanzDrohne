import city.City;

public class Application {

    public static void main(String[] args) {

        City mosbach = new City();

        int numberOfSimulations = 3;

        for (int i = 0; i < numberOfSimulations; i++) {
            mosbach.startEmergency();
        }

    }

}
