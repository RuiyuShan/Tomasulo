package entity.rs;

import entity.instruction.Instruction;
import entity.instruction.InstructionWithThreeFields;
import entity.instruction.Mul;

public class MultDivRSs extends ReservationStationSet implements InstructionScheduler<InstructionWithThreeFields>{

    public MultDivRSs() {
        super(2, "Mul");
    }


    @Override
    public void issue(InstructionWithThreeFields instruction) throws Exception {

    }

    @Override
    public void schedule() {

    }
}
