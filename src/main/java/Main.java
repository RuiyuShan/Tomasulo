import java.util.HashMap;

import entity.rs.AdderRSs;
import entity.rs.LoadBuffers;
import main.Tomasulo;

public class Main {
    public static void printInitialize(Tomasulo tomasulo) throws Exception {
        tomasulo.initialize();
        //        System.out.println("[addressImmediateMap] " + tomasulo.addressImmediateMap);
        //        System.out.println("[registersArray]" + Arrays.toString(tomasulo.registers));
        //        System.out.println("[instructionQueue]" + tomasulo.instructionQueue);
    }

    public static void main(String[] args) throws Exception {
        Tomasulo tomasulo = new Tomasulo();
        tomasulo.initialize();
        tomasulo.schedule();
    }

//    public static void main(String[] args) {
//        LoadBuffers loadBuffers = new LoadBuffers(new HashMap<>());
//        System.out.println(loadBuffers.TableString());
//    }
}
