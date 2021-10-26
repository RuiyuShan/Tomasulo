public enum Phase {
    /**
     * the instruction is waiting in the instruction queue and not issued
     */
    NOT_ISSUED,

    /**
     * the instruction is issued
     */
    ISSUE,

    /**
     * the instruction starts to execution
     */
    EXECUTION_START,

    /**
     * the instruction is executing
     */
    EXECUTING,

    /**
     * the instruction completes the execution
     */
    EXECUTION_COMPLETE,

    /**
     * the instruction wrote the result to CDB
     */
    WRITE_RESULT
}
