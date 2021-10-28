import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;


import entity.CDB;
import entity.Register;
import entity.instruction.Add;
import entity.instruction.Div;
import entity.instruction.Instruction;
import entity.instruction.InstructionWithTwoFields;
import entity.instruction.InstructionWithThreeFields;
import entity.instruction.Load;
import entity.instruction.Mul;
import entity.instruction.Operation;
import entity.instruction.Phase;
import entity.instruction.Store;
import entity.instruction.Sub;
import entity.rs.AdderRSs;
import entity.rs.LoadBuffers;
import entity.rs.MultDivRSs;
import entity.rs.StoreBuffers;
import static utils.Utils.*;

public class Tomasulo {
    Queue<Instruction> instructionQueue = new ArrayDeque<>();

    Register[] registers = new Register[50];

    CDB cdb = new CDB(registers);

    AdderRSs adderRSs = new AdderRSs();

    MultDivRSs multDivRSs = new MultDivRSs();

    LoadBuffers loadBuffers = new LoadBuffers();

    StoreBuffers storeBuffers = new StoreBuffers();

    Integer globalClockCycle = 0;

    /**
     * map of immediate value and simulated address.
     * e.g. (x1 3.0) represents the value of address 'x1' is 3.0
     */
    Map<String, Double> addressImmediateMap = new HashMap<>();

    public void schedule() throws Exception {
        while (true) {
            globalClockCycle++;
            cdb.send();
            issue();
            scheduleEachReservationStation();
        }
    }

    /**
     * if the corresponding reservationStationSet of the head instruction of instructionQueue, then issue the instruction.
     */
    private void issue() throws Exception {
        while(!instructionQueue.isEmpty() && checkReservationStationSetAvailability(instructionQueue.peek())) {
            Instruction instruction = instructionQueue.poll();
            switch (Objects.requireNonNull(instructionQueue.peek()).getOp()) {
                case LOAD -> loadBuffers.issue((Load) instruction);
                case STORE -> storeBuffers.issue((Store) instruction);
                case ADD -> adderRSs.issue((Add) instruction);
                case SUB -> adderRSs.issue((Sub) instruction);
                case MUL -> multDivRSs.issue((Mul) instruction);
                case DIV -> multDivRSs.issue((Div) instruction);
            }
        }
    }

    private boolean checkReservationStationSetAvailability(Instruction instruction) {
        return switch (instruction.getOp()) {
            case LOAD -> loadBuffers.available();
            case STORE -> storeBuffers.available();
            case ADD, SUB -> adderRSs.available();
            case MUL, DIV -> multDivRSs.available();
        };
    }

    private void scheduleEachReservationStation() {
        loadBuffers.schedule();
        storeBuffers.schedule();
        adderRSs.schedule();
        multDivRSs.schedule();
    }

    public void initialize() throws Exception {
        // load the values for an address to the addressImmediateMap
        loadAddressValues();
        // program counter
        int pc = 0;
        List<String> instructionStringList = readInstructionsFromFile();
        String instructionPackagePath = "entity.instruction.";
        for(String instructionStr : instructionStringList) {
            String[] instructionComponents = removeComma(instructionStr.split("\\s+"));
            String operation = instructionComponents[0];
            pc++;
            switch (operation) {
                case "Load" -> {
                    Register rs = getOrCreateRegisterByName(instructionComponents[1]);
                    Instruction load = new Load(pc, rs, instructionComponents[2]);
                    instructionQueue.offer(load);
                }
                case "Store" -> {
                    Register rs = getOrCreateRegisterByName(instructionComponents[1]);
                    Instruction store = new Store(pc, rs, instructionComponents[2]);
                    instructionQueue.offer(store);
                }
                case "Add", "Sub", "Mul", "Div" -> {
                    Class<? extends InstructionWithThreeFields> clazz = (Class<? extends InstructionWithThreeFields>) Class.forName(instructionPackagePath + operation);
                    Instruction instructionWithThreeFields = createInstructionWith3F(pc, instructionComponents, clazz);
                    instructionQueue.offer(instructionWithThreeFields);
                }
                default -> {
                }
            }
        }
    }

    /**
     * Create instruction with three computation fields. e.g. (instruction rd, rs, rt) or (instruction rd, rs, immediate)
     * For Add, Sub, Mul, Div.
     * @param pc program counter
     * @param instructionComponents instruction string array e.g. [instruction, rd, rs, rt]
     * @param clazz class of the target instruction
     * @param <T> return type
     * @return instruction of type T
     */
    public <T extends InstructionWithThreeFields> T createInstructionWith3F(Integer pc, String[] instructionComponents, Class<T> clazz)
            throws Exception {
        String str1 = instructionComponents[1];
        String str2 = instructionComponents[2];
        String str3 = instructionComponents[3];
        Register rd = getOrCreateRegisterByName(str1);

        // InstructionWithThreeRegisters(Integer pc, Register rd, Register rs, Register rt, Double immediate)
        Constructor<T> constructor = clazz.getDeclaredConstructor(Integer.class, Register.class, Register.class, Register.class, Double.class);

        if (isRegister(str2) && isRegister(str3)) { // instruction rd, rs, rt
            return constructor.newInstance(pc, rd, getOrCreateRegisterByName(str2), getOrCreateRegisterByName(str3), null);
        } else if (isRegister(str2) && !isRegister(str3)) { // instruction rd, rs, immediate
            return constructor.newInstance(pc, rd, getOrCreateRegisterByName(str2), null, Double.valueOf(str3));
        } else {
            throw new Exception("Wrong instruction style.");
        }
    }

    /**
     * get a register by its name, if the register is not in the global register array, then initialize it.
     * @param registerName
     * @return the register
     */
    private Register getOrCreateRegisterByName(String registerName) {
        int idx = getRegisterIndexByName(registerName);
        if(registers[idx] == null) {
            registers[idx] = new Register(idx, registerName);
        }
        return registers[idx];
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