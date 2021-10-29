package entity.instruction;

import entity.Register;

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

    public Register getRd() {
        return rd;
    }

    public Register getRs() {
        return rs;
    }

    public Register getRt() {
        return rt;
    }

    public Double getImmediate() {
        return immediate;
    }



    public InstructionForFP(Integer pc, Operation op, Register rd, Register rs,
            Register rt, Double immediate) {
        super(pc, op, op.getClockCycle());
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
    }
}
