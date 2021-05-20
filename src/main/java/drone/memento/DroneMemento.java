package drone.memento;

public class DroneMemento {

    boolean allowedAtNight;
    boolean antiTheftProtection;
    boolean enableHooter;

    public DroneMemento(boolean allowedAtNight, boolean antiTheftProtection, boolean enableHooter) {
        this.allowedAtNight = allowedAtNight;
        this.antiTheftProtection = antiTheftProtection;
        this.enableHooter = enableHooter;
    }

    public boolean getAllowedAtNight() {
        return allowedAtNight;
    }

    public void setAllowedAtNight(boolean allowedAtNight) {
        this.allowedAtNight = allowedAtNight;
    }

    public boolean getAntiTheftProtection() {
        return antiTheftProtection;
    }

    public void setAntiTheftProtection(boolean antiTheftProtection) {
        this.antiTheftProtection = antiTheftProtection;
    }

    public boolean getEnableHooter() {
        return enableHooter;
    }

    public void setEnableHooter(boolean enableHooter) {
        this.enableHooter = enableHooter;
    }
}
