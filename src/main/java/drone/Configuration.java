package drone;

public enum Configuration {
    instance;

    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");

    public String fileDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    public String filePath = fileDirectory + "droneParameters.json";
}
