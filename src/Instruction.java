/**
 * Class represents instruction
 */
public class Instruction {
    /**
     * program counter
     */
    private Integer pc;

    /**
     * operation
     */
    private Operation op;

    /**
     * destination register
     */
    private Register rd;

    /**
     * first source operand
     */
    private Register rs;

    /**
     * second source operand
     */
    private Register rt;

    /**
     * number of clock cycles needed to perform this instruction
     */
    private Integer clockCycle;

    /**
     * phase of instruction
     */
    private Phase phase;

    /**
     * immediate value
     */
    private Double immediate;

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Register getRd() {
        return rd;
    }

    public void setRd(Register rd) {
        this.rd = rd;
    }

    public Register getRs() {
        return rs;
    }

    public void setRs(Register rs) {
        this.rs = rs;
    }

    public Register getRt() {
        return rt;
    }

    public void setRt(Register rt) {
        this.rt = rt;
    }

    public Integer getClockCycle() {
        return clockCycle;
    }

    public void setClockCycle(Integer clockCycle) {
        this.clockCycle = clockCycle;
    }

    public Double getImmediate() {
        return immediate;
    }

    public void setImmediate(Double immediate) {
        this.immediate = immediate;
    }

    public Instruction(Integer pc, Operation op, Integer clockCycle) {
        this.pc = pc;
        this.op = op;
        this.clockCycle = clockCycle;
    }

    public Instruction(Integer pc, Operation op, Register rd, Integer clockCycle, Phase phase, Double immediate) {
        this.pc = pc;
        this.op = op;
        this.rd = rd;
        this.clockCycle = clockCycle;
        this.phase = phase;
        this.immediate = immediate;
    }

    public Instruction(Integer pc, Operation op, Register rd, Register rs, Register rt, Integer clockCycle, Phase phase) {
        this.pc = pc;
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.clockCycle = clockCycle;
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "pc=" + pc +
                ", op=" + op +
                ", rd=" + rd +
                ", rs=" + rs +
                ", rt=" + rt +
                ", clockCycle=" + clockCycle +
                ", phase=" + phase +
                ", immediate=" + immediate +
                '}';
    }
}
