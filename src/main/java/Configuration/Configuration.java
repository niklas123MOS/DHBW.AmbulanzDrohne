package Configuration;

public enum Configuration {
    instance;

    public Algorithm pathAlgorithm = Algorithm.AStar;


    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");

    public String fileDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    public String filePath = fileDirectory + "droneParameters.json";

    public String getComponentPath() {
        String path = userDirectory + fileSeparator + "search-algorithms" + fileSeparator;
        switch (pathAlgorithm) {
            case AStar:
                path += "a_star" + fileSeparator + "a_star.jar";
                break;
            case DStar:
                path += "d_star" + fileSeparator + "d_star.jar";
                break;
        }
        return path;
    }


}
