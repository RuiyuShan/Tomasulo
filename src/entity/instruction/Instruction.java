package entity.instruction;

import entity.Register;

/**
 * Class represents entity.instruction
 */
public abstract class Instruction {
    /**
     * program counter
     */
    private final Integer pc;

    /**
     * operation
     */
    private final Operation op;

    /**
     * number of clock cycles needed to perform this entity.instruction
     */
    private final Integer maxClockCycle;

    /**
     * current clock cycle.
     */
    private Integer currentClockCycle = 0;

    /**
     * phase of entity.instruction
     */
    private Phase phase = Phase.NOT_ISSUED;

    public Integer getPc() {
        return pc;
    }

    public Operation getOp() {
        return op;
    }

    public Integer getMaxClockCycle() {
        return maxClockCycle;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void incrementClockCycle() {
        this.currentClockCycle++;
    }

    /**
     * whether the instruction completed its execution.
     * @return res
     */
    public boolean completed() {
        return currentClockCycle == maxClockCycle;
    }

    public Instruction(Integer pc, Operation op, Integer maxClockCycle) {
        this.pc = pc;
        this.op = op;
        this.maxClockCycle = maxClockCycle;
    }

    @Override
    public String toString() {
        return "{" +
                "pc=" + pc +
                ", op=" + op +
                ", maxClockCycle=" + maxClockCycle +
                ", phase=" + phase +
                '}';
    }
}
