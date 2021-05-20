package drone.boom;

public class MainBoom extends Boom{

    public MainBoom(String boomName) {
        super(boomName);
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
