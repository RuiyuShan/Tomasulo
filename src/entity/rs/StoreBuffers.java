package entity.rs;

import java.util.Map;

import entity.instruction.Instruction;
import entity.instruction.Store;

public class StoreBuffers extends ReservationStationSet implements InstructionScheduler<Store>{

    Map<String, Double> addressImmediateMap;

    public StoreBuffers(Map<String, Double> addressImmediateMap) {
        super(5, "Store");
        this.addressImmediateMap = addressImmediateMap;
    }

    @Override
    public void issue(Store instruction) throws Exception {

    }

    @Override
    public void execute(ReservationStation reservationStation) throws Exception {

    }

    @Override
    public void writeResult(ReservationStation reservationStation) throws Exception {

    }

    @Override
    public void schedule() {

    }
}
