package entity.instruction;

import entity.Register;

public class Div extends InstructionWithThreeFields {

    public Div(Integer pc, Register rd, Register rs, Register rt,
            Double immediate) {
        super(pc, Operation.DIV, rd, rs, rt, immediate);
    }

    @Override
    public String toString() {
        return "Div " + super.toString();
    }
}
