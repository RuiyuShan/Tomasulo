package entity.instruction;

public enum Operation {
    /**
     * load
     */
    LOAD(3),

    /**
     * store
     */
    STORE(3),

    /**
     * add
     */
    ADD(2),

    /**
     * subtract
     */
    SUB(2),

    /**
     * multiple
     */
    MUL(10),

    /**
     * divide
     */
    DIV(40);

    /**
     * the clock cycles needed to perform the type of operation.
     */
    private final Integer clockCycle;

    public Integer getClockCycle() {
        return clockCycle;
    }

    Operation(Integer clockCycle) {
        this.clockCycle = clockCycle;
    }
}
