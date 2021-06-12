package drone.boom;

import drone.Motor.Motor;

public class SubBoom extends Boom {

    Motor motorTop;
    Motor motorBottom;


    public SubBoom(String boomName) {
        super(boomName);
        this.motorTop = new Motor(this);
        this.motorBottom = new Motor(this);
    }

    public Motor getMotorTop() {
        return motorTop;
    }

    public Motor getMotorBottom() {
        return motorBottom;
    }

    public void printStaffingInformation() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Boom name: ").append(this.boomName).append("\n");
        stringBuilder.append("Superior boom: ").append(getSuperiorBoom());

        for (Boom boom : booms)
            boom.printStaffingInformation();

        System.out.println(stringBuilder.toString());
    }
}
