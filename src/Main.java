import java.util.Arrays;

public class Main {
    public static void printInitialize(Tomasulo tomasulo) throws Exception {
        tomasulo.initialize();
        System.out.println("[addressImmediateMap] " + tomasulo.addressImmediateMap);
        System.out.println("[registersArray]" + Arrays.toString(tomasulo.registers));
        System.out.println("[instructionQueue]" + tomasulo.instructionQueue);
    }

    public static void main(String[] args) throws Exception {
        Tomasulo tomasulo = new Tomasulo();
        printInitialize(tomasulo);
    }
}
