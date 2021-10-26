public class ReservationStation {
    /**
     * name
     */
    private String name;

    /**
     * instruction that occupying this reservation station
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
    private Double A;

    public ReservationStation(Instruction instruction, boolean busy, Operation operation, Double vj, Double vk, ReservationStation qj, ReservationStation qk, Double a) {
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

    public Double getA() {
        return A;
    }

    public void setA(Double a) {
        A = a;
    }

    @Override
    public String toString() {
        return "ReservationStation{" +
                "name='" + name + '\'' +
                ", instruction=" + instruction +
                ", busy=" + busy +
                ", operation=" + operation +
                ", vj=" + vj +
                ", vk=" + vk +
                ", qj=" + qj +
                ", qk=" + qk +
                ", A=" + A +
                '}';
    }
}
