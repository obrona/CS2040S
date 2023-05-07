public class Tester {
    public static void main(String[] args) {
        String[] argsInput = new String[3];
        argsInput[0] = "3";
        argsInput[1] = "15";
        argsInput[2] = "PS6Test.in";
        try {
            TextGenerator.setSeed(100);
            TextGenerator.main(argsInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
