package entity.rs;

import entity.instruction.Instruction;

public interface InstructionScheduler<T extends Instruction> {
    void issue(T instruction) throws Exception;

    void schedule();
}
