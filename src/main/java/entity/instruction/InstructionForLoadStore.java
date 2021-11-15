package entity.instruction;

import entity.Register;
import lombok.Getter;

@Getter
public abstract class InstructionForLoadStore extends Instruction {
    /**
     * destination register for Load or source register for Store
     */
    private final Register rs;

    /**
     * address of immediate value
     */
    private final String address;

    public InstructionForLoadStore(Integer pc, Operation op, Register rs, String address) {
        super(pc, op, op.getClockCycle());
        this.rs = rs;
        this.address = address;
    }
}
