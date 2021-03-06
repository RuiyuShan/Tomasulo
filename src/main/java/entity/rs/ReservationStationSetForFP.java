package entity.rs;

import de.vandermeer.asciitable.AsciiTable;
import entity.instruction.Instruction;
import entity.instruction.InstructionForFP;
import entity.instruction.Operation;
import entity.instruction.Phase;
import main.Tomasulo;
import utils.Utils;

public abstract class ReservationStationSetForFP extends ReservationStationSet implements InstructionScheduler<InstructionForFP> {

    public ReservationStationSetForFP(String name, int capacity, String type) {
        super(name, capacity, type);
    }

    @Override
    public void issue(InstructionForFP instruction) throws Exception {
        if (initializeReservationStation(instruction)) {
            ReservationStation station = getReservationStationByInstruction(instruction);
            // set Vj and Qj
            if (instruction.getRs().getQi() == null || instruction.getRs().getQi().getResult() != null) {
                station.setVj(instruction.getRs().getValue());
            } else {
                station.setQj(instruction.getRs().getQi());
            }
            // set Vk and Qk
            if(instruction.getImmediate() == null) {
                if (instruction.getRt().getQi() == null || instruction.getRt().getQi().getResult() != null) {
                    station.setVk(instruction.getRt().getValue());
                } else {
                    station.setQk(instruction.getRt().getQi());
                }
            } else {
                station.setVk(instruction.getImmediate());
            }
            instruction.getRd().setQi(station);
            station.addComment(Phase.ISSUE.getValue());
            instruction.setPhase(Phase.ISSUE);
        } else {
            throw new Exception("No available reservation station.");
        }
    }

    @Override
    public void execute(ReservationStation reservationStation) throws Exception {
        if (reservationStation.getInstruction().getGlobalClockCycleIssuedAt().equals(Tomasulo.getGlobalClockCycle())){
            return;
        }
        if (reservationStation.readyToExecute()) {
            InstructionForFP instruction = (InstructionForFP) reservationStation.getInstruction();
            if (instruction.getPhase() == Phase.ISSUE) {
                instruction.setPhase(Phase.EXECUTING);
                reservationStation.addComment(Phase.EXECUTION_START.getValue());
            }
            reservationStation.increaseClockCycle();
            if (reservationStation.isExecutionCompleted()) {
                Double result = 0D;
                if (instruction.getOp() == Operation.DIV && reservationStation.getVk() == 0D) {
                    result = 0D;
                } else {
                    result = switch (instruction.getOp()) {
                        case ADD -> reservationStation.getVj() + reservationStation.getVk();
                        case SUB -> reservationStation.getVj() - reservationStation.getVk();
                        case MUL -> reservationStation.getVj() * reservationStation.getVk();
                        case DIV -> reservationStation.getVj() / reservationStation.getVk();
                        default -> throw new Exception("Instruction type error.");
                    };
                }
                reservationStation.setResult(result);
                instruction.setPhase(Phase.EXECUTION_COMPLETE);
                reservationStation.addComment(Phase.EXECUTION_COMPLETE.getValue());
            }
        } else {
            reservationStation.stall();
        }
    }

    @Override
    public void writeResult(ReservationStation reservationStation) throws Exception {
        Tomasulo.getCdb().offer(reservationStation);
    }

    @Override
    public void schedule() throws Exception {
        for (ReservationStation station : getReservationStations()) {
            if (station.isBusy()) {
                if (station.isBusy()) {
                    Instruction instruction = station.getInstruction();
                    switch (instruction.getPhase()) {
                        case ISSUE -> execute(station);
                        case EXECUTING -> execute(station);
                        case EXECUTION_COMPLETE -> writeResult(station);
                        default -> {}
                    }
                }
            }
        }
    }
}
