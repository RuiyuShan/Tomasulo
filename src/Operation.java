public enum Operation {
    /**
     * load
     */
    LOAD(2),

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
    SUB(2),

    /**
     * multiple
     */
    MUL(3),

    /**
     * divide
     */
    DIV(3);

    /**
     * the clock cycles needed to perform the type of operation.
     */
    private Integer clockCycle;

    public Integer getClockCycle() {
        return clockCycle;
    }

    public void setClockCycle(Integer clockCycle) {
        this.clockCycle = clockCycle;
    }

    Operation(Integer clockCycle) {
        this.clockCycle = clockCycle;
    }
}
