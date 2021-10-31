package entity.instruction;

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
     * phase of entity.instruction
     */
    private Phase phase = Phase.NOT_ISSUED;

    /**
     * the global clock cycle in which the instruction first issue.
     */
    private Integer globalClockCycleIssuedAt;

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

    public Integer getGlobalClockCycleIssuedAt() {
        return globalClockCycleIssuedAt;
    }

    public void setGlobalClockCycleIssuedAt(Integer globalClockCycleIssuedAt) {
        this.globalClockCycleIssuedAt = globalClockCycleIssuedAt;
    }

    public Instruction(Integer pc, Operation op, Integer maxClockCycle) {
        this.pc = pc;
        this.op = op;
        this.maxClockCycle = maxClockCycle;
    }

}
