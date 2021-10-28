package entity.instruction;

import entity.Register;

public class Add extends InstructionWithThreeFields {
    public Add(Integer pc, Register rd, Register rs,
            Register rt, Double immediate) {
        super(pc, Operation.ADD, rd, rs, rt, immediate);
    }

    @Override
    public String toString() {
        return "Add" + super.toString();
    }
}
