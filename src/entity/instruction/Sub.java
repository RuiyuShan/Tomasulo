package entity.instruction;

import entity.Register;

public class Sub extends InstructionForFP {

    public Sub(Integer pc, Register rd, Register rs, Register rt,
            Double immediate) {
        super(pc, Operation.SUB, rd, rs, rt, immediate);
    }

    @Override
    public String toString() {
        return "Sub " + super.toString();
    }
}
