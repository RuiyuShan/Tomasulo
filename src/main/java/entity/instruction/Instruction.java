package entity.instruction;

import lombok.Data;

/**
 * Class represents entity.instruction
 */
@Data   // @Data provides Getter and Setter methods for this class.
public abstract class Instruction {
    /**
     * program counter
     */
    private Integer pc;

    /**
     * operation
     */
    private Operation op;

    /**
     * number of clock cycles needed to perform this entity.instruction
     */
    private Integer maxClockCycle;

    /**
     * phase of entity.instruction
     */
    private Phase phase;

    /**
     * the global clock cycle in which the instruction first issue.
     */
    private Integer globalClockCycleIssuedAt;

    public Instruction(Integer pc, Operation op, Integer maxClockCycle) {
        this.pc = pc;
        this.op = op;
        this.maxClockCycle = maxClockCycle;
        this.phase = Phase.NOT_ISSUED;
    }

}
