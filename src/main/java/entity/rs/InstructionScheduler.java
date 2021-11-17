package entity.rs;

import entity.instruction.Instruction;

public interface InstructionScheduler<T extends Instruction> {
    /**
     * Issue the instruction, load the instruction into the reservation station.
     * @param instruction
     */
    void issue(T instruction) throws Exception;

    /**
     * The reservation station execute its instruction.
     * @param reservationStation
     */
    void execute(ReservationStation reservationStation) throws Exception;

    /**
     * Send result to CDB.
     * @param reservationStation
     */
    void writeResult(ReservationStation reservationStation) throws Exception;

    /**
     * Schedule the reservation station's operation according to the Phase of instruction which is corresponding to it.
     */
    void schedule() throws Exception;
}
