package entity.instruction;

import entity.Register;

public class Store extends InstructionWithTwoFields {

    public Store(Integer pc, Register rs, String address) {
        super(pc, Operation.STORE, rs, address);
    }

    @Override
    public String toString() {
        return "Store " + super.toString();
    }
}
