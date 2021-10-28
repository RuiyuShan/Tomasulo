package entity.instruction;

import entity.Register;

public class Load extends InstructionWithTwoFields {
    public Load(Integer pc, Register rs, String address) {
        super(pc, Operation.LOAD, rs, address);
    }

    @Override
    public String toString() {
        return "Load " + super.toString();
    }
}
