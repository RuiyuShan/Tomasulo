package entity.instruction;

public enum Operation {
    /**
     * load
     */
    LOAD(3),

    /**
     * store
     */
    STORE(2),

    /**
     * add
     */
    ADD(2),

    /**
     * subtract
     */
    SUB(3),

    /**
     * multiple
     */
    MUL(10),

    /**
     * divide
     */
    DIV(40);

    /**
     * the clock cycles needed to complete execution of the type of instruction.
     */
    private final Integer clockCycle;

    public Integer getClockCycle() {
        return clockCycle;
    }

    Operation(Integer clockCycle) {
        this.clockCycle = clockCycle;
    }
}
