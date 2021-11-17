package entity.instruction;

import entity.Register;
import lombok.Getter;

@Getter     // @Getter provides Getter methods for this class.
public abstract class InstructionForFP extends Instruction{
    /**
     * destination register
     */
    private final Register rd;

    /**
     * first source operand
     */
    private final Register rs;

    /**
     * second source operand
     */
    private final Register rt;

    /**
     * immediate value
     */
    private final Double immediate;



    public InstructionForFP(Integer pc, Operation op, Register rd, Register rs,
            Register rt, Double immediate) {
        super(pc, op, op.getClockCycle());
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
    }
}
