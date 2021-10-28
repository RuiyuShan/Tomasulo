package entity.rs;

import entity.instruction.Instruction;
import entity.instruction.Store;

public class StoreBuffers extends ReservationStationSet implements InstructionScheduler<Store>{
    public StoreBuffers() {
        super(5, "Store");
    }

    @Override
    public void issue(Store instruction) throws Exception {

    }

    @Override
    public void schedule() {

    }
}
