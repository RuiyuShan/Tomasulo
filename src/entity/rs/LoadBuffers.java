package entity.rs;

import entity.Register;
import entity.instruction.Instruction;
import entity.instruction.Load;
import entity.instruction.Phase;

public class LoadBuffers extends ReservationStationSet implements InstructionScheduler<Load> {

    public LoadBuffers() {
        super(5, "Load");
    }


    @Override
    public void issue(Load instruction) throws Exception {
        if (offer(instruction)) {
            ReservationStation station = getReservationStationByInstruction(instruction);
            Register rs = instruction.getRs();
            instruction.setPhase(Phase.ISSUE);

        } else {
            throw new Exception("Load buffer is full");
        }
    }

    @Override
    public void schedule() {
        for (ReservationStation station : reservationStations) {
            Load load = (Load) station.getInstruction();
            if(load != null && load.getPhase() == Phase.ISSUE) {

            }
        }
    }
}
