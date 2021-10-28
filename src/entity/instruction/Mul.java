package entity.instruction;

import entity.Register;

public class Mul extends InstructionWithThreeFields {

    public Mul(Integer pc, Register rd, Register rs, Register rt,
            Double immediate) {
        super(pc, Operation.MUL, rd, rs, rt, immediate);
    }

    @Override
    public String toString() {
        return "Mul " + super.toString();
    }
}
