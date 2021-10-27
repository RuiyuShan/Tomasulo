import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Tomasulo {
    Queue<Instruction> instructionQueue = new ArrayDeque<>();

    Register[] registers = new Register[50];

    AdderRSs adderRSs = new AdderRSs();

    MultDivRSs multDivRSs = new MultDivRSs();

    LoadBuffers loadBuffers = new LoadBuffers();

    StoreBuffers storeBuffers = new StoreBuffers();

    /**
     * map of immediate value and simulated address.
     * e.g. (x1 3.0) represents the value of address 'x1' is 3.0
     */
    Map<String, Double> addressImmediateMap = new HashMap<>();

    private int addCount = 0;
    private int mulCount = 0;
    private int loadCount = 0;
    private int storeCount = 0;

    public void schedule() {
        while (!instructionQueue.isEmpty()) {
            Instruction cur = instructionQueue.poll();
            ReservationStation station = getOrCreateReservationStationByInstruction(cur);
            switch (cur.getOp()) {
                case STORE -> {
                    if(station.get)
                }
            }
        }
    }



    public void initialize() {
        // load the values for an address to the addressImmediateMap
        loadAddressValues();
        // program counter
        int pc = 0;
        List<String> instructionStringList = Utils.readInstructionsFromFile();
        for(String instructionStr : instructionStringList) {
            String[] instructionComponents = Utils.removeComma(instructionStr.split("\\s+"));
            String operation = instructionComponents[0];
            pc++;
            switch (operation) {
                case "Load" -> {
                    Register loadRs = getOrCreateRegisterByName(instructionComponents[1]);
                    Instruction load = new Instruction(pc, Operation.LOAD, loadRs, Operation.LOAD.getClockCycle(),
                            Phase.NOT_ISSUED, instructionComponents[2]);
                    instructionQueue.offer(load);
                    getOrCreateReservationStationByInstruction(load);
                }
                case "Store" -> {
                    Register storeRs = getOrCreateRegisterByName(instructionComponents[1]);
                    Instruction store = new Instruction(pc, Operation.STORE, storeRs, Operation.STORE.getClockCycle(),
                            Phase.NOT_ISSUED, instructionComponents[2]);
                    instructionQueue.offer(store);
                    getOrCreateReservationStationByInstruction(store);
                }
                case "Add" -> {
                    Register addRd = getOrCreateRegisterByName(instructionComponents[1]);
                    Register addRs = getOrCreateRegisterByName(instructionComponents[2]);
                    Register addRt = getOrCreateRegisterByName(instructionComponents[3]);
                    Instruction add =
                            new Instruction(pc, Operation.ADD, addRd, addRs, addRt, Operation.ADD.getClockCycle(),
                                    Phase.NOT_ISSUED);
                    instructionQueue.offer(add);
                    getOrCreateReservationStationByInstruction(add);
                }
                case "Sub" -> {
                    Register subRd = getOrCreateRegisterByName(instructionComponents[1]);
                    Register subRs = getOrCreateRegisterByName(instructionComponents[2]);
                    Register subRt = getOrCreateRegisterByName(instructionComponents[3]);
                    Instruction sub =
                            new Instruction(pc, Operation.SUB, subRd, subRs, subRt, Operation.SUB.getClockCycle(),
                                    Phase.NOT_ISSUED);
                    instructionQueue.offer(sub);
                    getOrCreateReservationStationByInstruction(sub);
                }
                case "Mul" -> {
                    Register mulRd = getOrCreateRegisterByName(instructionComponents[1]);
                    Register mulRs = getOrCreateRegisterByName(instructionComponents[2]);
                    Register mulRt = getOrCreateRegisterByName(instructionComponents[3]);
                    Instruction mul =
                            new Instruction(pc, Operation.MUL, mulRd, mulRs, mulRt, Operation.MUL.getClockCycle(),
                                    Phase.NOT_ISSUED);
                    instructionQueue.offer(mul);
                    getOrCreateReservationStationByInstruction(mul);
                }
                case "Div" -> {
                    Register divRd = getOrCreateRegisterByName(instructionComponents[1]);
                    Register divRs = getOrCreateRegisterByName(instructionComponents[2]);
                    Register divRt = getOrCreateRegisterByName(instructionComponents[3]);
                    Instruction div =
                            new Instruction(pc, Operation.DIV, divRd, divRs, divRt, Operation.DIV.getClockCycle(),
                                    Phase.NOT_ISSUED);
                    instructionQueue.offer(div);
                    getOrCreateReservationStationByInstruction(div);
                }
                default -> {
                }
            }
        }
    }

    /**
     * get a register by its name, if the register is not in the global register array, then initialize it.
     * @param registerName
     * @return the register
     */
    private Register getOrCreateRegisterByName(String registerName) {
        int idx = Utils.getRegisterIndexByName(registerName);
        if(CDB[idx] == null) {
            CDB[idx] = new Register(idx, registerName);
        }
        return CDB[idx];
    }

    /**
     * get or create a reservation station by an instruction
     * @param instruction
     * @return reservation station
     */
    private ReservationStation getOrCreateReservationStationByInstruction(Instruction instruction) {
        if(reservationStationsArray[instruction.getPc() - 1] == null) {
            String name = switch (instruction.getOp()) {
                case LOAD -> "Load" + (++loadCount);
                case STORE -> "Store" + (++storeCount);
                case ADD, SUB -> "Add" + (++addCount);
                case MUL, DIV -> "Mul" + (++mulCount);
            };
            ReservationStation reservationStation = new ReservationStation(name, instruction, false, instruction.getOp(), null, null, null, null, instruction.getAddress());
            reservationStationsArray[instruction.getPc() - 1] = reservationStation;
        }
        return reservationStationsArray[instruction.getPc() - 1];
    }

    /**
     * load values of an address unit from .txt file
     */
    private void loadAddressValues() {
        try {
            File file = new File("immediateValues.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String address = line.split("\\s+")[0];
                    Double value = Double.valueOf(line.split("\\s+")[1]);
                    addressImmediateMap.put(address, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}