package entity.rs;

import entity.instruction.Instruction;
import entity.instruction.Operation;
import entity.instruction.Store;
import main.Tomasulo;

public class ReservationStation {
    /**
     * name
     */
    private String name;

    /**
     * entity.instruction that occupying this reservation station
     */
    private Instruction instruction;

    /**
     * Indicates that this reservation station and its accompanying functional unit are occupied.
     */
    private boolean busy;

    /**
     * The operation to perform on source operands.
     */
    private Operation operation;

    /**
     * The value of the source operand.
     */
    private Double vj;

    /**
     * The value of the source operand.
     */
    private Double vk;

    /**
     * The reservation station that will produce the corresponding source operand.
     */
    private ReservationStation qj;

    /**
     * The reservation station that will produce the corresponding source operand.
     */
    private ReservationStation qk;

    /**
     * Used to hold information for the memory address calculation for a load or store.
     */
    private String A;

    /**
     * current clock cycle.
     */
    private Integer currentClockCycle = 0;

    /**
     * operation result
     */
    private Double result;

    public ReservationStation(){
    }

    public ReservationStation(String name, Instruction instruction, boolean busy, Operation operation, Double vj, Double vk, ReservationStation qj, ReservationStation qk, String a) {
        this.name = name;
        this.instruction = instruction;
        this.busy = busy;
        this.operation = operation;
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
        A = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Double getVj() {
        return vj;
    }

    public void setVj(Double vj) {
        this.vj = vj;
    }

    public Double getVk() {
        return vk;
    }

    public void setVk(Double vk) {
        this.vk = vk;
    }

    public ReservationStation getQj() {
        return qj;
    }

    public void setQj(ReservationStation qj) {
        this.qj = qj;
    }

    public ReservationStation getQk() {
        return qk;
    }

    public void setQk(ReservationStation qk) {
        this.qk = qk;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Integer getCurrentClockCycle() {
        return currentClockCycle;
    }

    public void incrementClockCycle(){
        currentClockCycle++;
    }

    public boolean isExecutionCompleted(){
        return currentClockCycle == instruction.getMaxClockCycle();
    }

    public void stall(){
        addComment("stall");
    }

    public void addComment(String comment) {
        Tomasulo.addComment(getName() + ":" + comment);
    }

    public boolean readyToExecute() {
        switch (instruction.getOp()) {
            case LOAD -> {
                return true;
            }
            case ADD, SUB, MUL, DIV -> {
                return vj != null && vk != null;
            }
            case STORE -> {
                Store store = (Store) instruction;
                if (store.getRs().getValue() != null) {
                    return true;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + instruction.getOp());
        }
        return false;
    }

    @Override
    public String toString() {
        return "\nentity.rs.ReservationStation{" +
                "name='" + name + '\'' +
                ", entity.instruction=" + instruction +
                ", busy=" + busy +
                ", operation=" + operation +
                ", vj=" + vj +
                ", vk=" + vk +
                ", qj=" + qj +
                ", qk=" + qk +
                ", A=" + A +
                "}";
    }
}
