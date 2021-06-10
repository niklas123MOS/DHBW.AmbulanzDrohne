package EmergencyCenter;

    public class Bot {

        private EmergencyCenter center;
        private int emergencyRow;
        private int emergencyCol;
        private char[][] emergencyFace;

        public Bot(EmergencyCenter center) {

            this.center = center;
        }

        public int getEmergencyRow() {
            return emergencyRow;
        }

        public int getEmergencyCol() {
            return emergencyCol;
        }

        public char[][] getEmergencyFace() {
            return emergencyFace;
        }

        public void acceptEmergencyCall(int row, int col, char[][] face, String humanID) {
            emergencyRow =row;
            emergencyCol =col;
            emergencyFace =face;
            center.callDronePort(row, col, face, humanID);
        }



}
