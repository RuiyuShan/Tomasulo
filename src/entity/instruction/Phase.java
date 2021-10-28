package entity.instruction;

public enum Phase {
    /**
     * the entity.instruction is waiting in the entity.instruction queue and not issued
     */
    NOT_ISSUED,

    /**
     * the entity.instruction is issued
     */
    ISSUE,

    /**
     * the entity.instruction starts to execution
     */
    EXECUTION_START,

    /**
     * the entity.instruction is executing
     */
    EXECUTING,

    /**
     * the entity.instruction completes the execution
     */
    EXECUTION_COMPLETE,

    /**
     * the entity.instruction wrote the result to entity.CDB
     */
    WRITE_RESULT
}
