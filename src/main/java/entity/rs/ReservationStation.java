package entity.rs;

import de.vandermeer.asciitable.AsciiTable;
import entity.instruction.Instruction;
import entity.instruction.Operation;
import entity.instruction.Phase;
import entity.instruction.Store;
import lombok.Data;
import main.Tomasulo;

@Data   // @Data provides Getter and Setter methods for this class.
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

    /**
     * If this rs successfully write result.
     */
    private boolean writeResultSuccess;

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

    public void increaseClockCycle(){
        currentClockCycle++;
    }

    public boolean isExecutionCompleted(){
        return currentClockCycle.equals(instruction.getMaxClockCycle());
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

    public void updateStatusOfResult() throws Exception {
        if(!writeResultSuccess) {
            getInstruction().setPhase(Phase.WRITE_RESULT);
            setBusy(false);
            addComment(Phase.WRITE_RESULT.getValue());
            switch (getOperation()) {
                case LOAD -> LoadBuffers.decreaseSize();
                case STORE -> StoreBuffers.decreaseSize();
                case ADD, SUB -> AdderRSs.decreaseSize();
                case MUL, DIV -> MultDivRSs.decreaseSize();
            }
            writeResultSuccess = true;
        } else {
            throw new Exception("The result has been written");
        }
    }

}
