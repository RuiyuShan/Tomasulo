import java.util.Arrays;

public class Main {
    public static void printInitialize(Tomasulo tomasulo) {
        tomasulo.initialize();
        System.out.println("[addressImmediateMap] " + tomasulo.addressImmediateMap);
        System.out.println("[registersArray]" + Arrays.toString(tomasulo.registersArray));
        System.out.println("[reservationStationsArray]" + Arrays.toString(tomasulo.reservationStationsArray));
        System.out.println("[instructionQueue]" + tomasulo.instructionQueue);
    }

    public static void main(String[] args) {
        Tomasulo tomasulo = new Tomasulo();
        printInitialize(tomasulo);

    }
}
