package entity.instruction;

public enum Phase {
    /**
     * the instruction is waiting in the entity.instruction queue and not issued
     */
    NOT_ISSUED("not issued"),

    /**
     * the instruction is issued
     */
    ISSUE("issued"),

    /**
     * the instruction starts to execution
     */
    EXECUTION_START("execution start"),

    /**
     * the instruction is executing
     */
    EXECUTING("executing"),

    /**
     * the instruction completes the execution
     */
    EXECUTION_COMPLETE("execution complete"),

    /**
     * the instruction wrote the result to entity.CDB
     */
    WRITE_RESULT("write result");

    private final String value;

    Phase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
