package entity.instruction;

import entity.Register;

public abstract class InstructionWithTwoFields extends Instruction {
    /**
     * destination register for Load or source register for Store
     */
    private Register rs;

    /**
     * address of immediate value
     */
    private String address;

    public Register getRs() {
        return rs;
    }

    public void setRs(Register rs) {
        this.rs = rs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InstructionWithTwoFields(Integer pc, Operation op, Register rs, String address) {
        super(pc, op, op.getClockCycle());
        this.rs = rs;
        this.address = address;
    }
}
