package entity.rs;

import entity.instruction.Instruction;

public interface InstructionScheduler<T extends Instruction> {
    void issue(T instruction) throws Exception;

    void execute(ReservationStation reservationStation) throws Exception;

    void writeResult(ReservationStation reservationStation) throws Exception;

    void schedule() throws Exception;
}
