package entity.rs;

import entity.instruction.Add;
import entity.instruction.Instruction;
import entity.instruction.InstructionWithThreeFields;

public class AdderRSs extends ReservationStationSet implements InstructionScheduler<InstructionWithThreeFields> {

    public AdderRSs() {
        super(3, "Add");
    }


    @Override
    public void issue(InstructionWithThreeFields instruction) throws Exception {

    }

    @Override
    public void schedule() {

    }
}
