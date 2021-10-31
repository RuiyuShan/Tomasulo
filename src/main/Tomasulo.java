package main;

import entity.CDB;
import entity.Register;
import entity.instruction.*;
import entity.rs.AdderRSs;
import entity.rs.LoadBuffers;
import entity.rs.MultDivRSs;
import entity.rs.StoreBuffers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.*;

import static utils.Utils.*;

public class Tomasulo {
    /**
     * map of immediate value and simulated address.
     * e.g. (x1 3.0) represents the value of address 'x1' is 3.0
     */
    static Map<String, Double> addressImmediateMap;

    /**
     * the instruction queue
     */
    static Queue<Instruction> instructionQueue;

    /**
     * register set
     */
    static Register[] registers;

    /**
     * reservation stations for Add and Sub operation
     */
    static AdderRSs adderRSs;

    /**
     * reservation stations for Mul and Div operation
     */
    static MultDivRSs multDivRSs;

    /**
     * the load buffer, for Load operation
     */
    static LoadBuffers loadBuffers;

    /**
     * the store buffer, for Store operation
     */
    static StoreBuffers storeBuffers;

    /**
     * the CDB
     */
    static CDB cdb;

    /**
     * the global clock cycle
     */
    static Integer globalClockCycle;

    /**
     * comment corresponds to the global clock cycle.
     */
    static List<String> comments;

    public Tomasulo() {
        addressImmediateMap = new HashMap<>();
        instructionQueue = new ArrayDeque<>();
        registers = new Register[20];
        adderRSs = new AdderRSs();
        multDivRSs = new MultDivRSs();
        loadBuffers = new LoadBuffers(addressImmediateMap);
        storeBuffers = new StoreBuffers(addressImmediateMap);
        cdb = new CDB(registers, loadBuffers, storeBuffers, adderRSs, multDivRSs);
        globalClockCycle = 0;
        comments = new ArrayList<>();
    }

    public void schedule() throws Exception {
        while (!done()) {
            increaseGlobalClockCycle();
            issue();
            scheduleEachReservationStation();
            cdb.broadcastAndWriteResult();
            System.out.println(comments.get(getGlobalClockCycle()) + "\n" + Arrays.toString(registers) + "\n");
            Thread.sleep(300);
        }
    }

    /**
     * if the corresponding reservationStationSet of the head instruction of instructionQueue is available, then issue the instruction.
     */
    private void issue() throws Exception {
        if(!instructionQueue.isEmpty() && checkReservationStationSetAvailability(instructionQueue.peek())) {
            Instruction instruction = instructionQueue.poll();
            switch (Objects.requireNonNull(Objects.requireNonNull(instruction).getOp())) {
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

    private void scheduleEachReservationStation() throws Exception {
        loadBuffers.schedule();
        storeBuffers.schedule();
        adderRSs.schedule();
        multDivRSs.schedule();
    }

    public void initialize() throws Exception {
        // load the values for an address to the addressImmediateMap
        loadAddressValues();
        loadInstructions();
        comments.add(globalClockCycle.toString());
    }

    /**
     * read instructions from .txt file, create instruction objects and load them into the instruction queue.
     */
    private void loadInstructions() throws Exception {
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
                    Class<? extends InstructionForFP> clazz = (Class<? extends InstructionForFP>) Class.forName(instructionPackagePath + operation);
                    Instruction instructionWithThreeFields = createFPInstruction(pc, instructionComponents, clazz);
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
    private  <T extends InstructionForFP> T createFPInstruction(Integer pc, String[] instructionComponents, Class<T> clazz)
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

    /**
     * Check if the program has completed its work.
     * @return true if instruction completed.
     */
    private boolean done() {
        return instructionQueue.isEmpty()
                && loadBuffers.isEmpty() && storeBuffers.isEmpty()
                && adderRSs.isEmpty() && multDivRSs.isEmpty();
    }

    public static Integer getGlobalClockCycle() {
        return globalClockCycle;
    }

    private void increaseGlobalClockCycle() {
        globalClockCycle++;
        comments.add(globalClockCycle.toString());
    }

    public static void addComment(String comment) {
        String currentComment = comments.get(globalClockCycle);
        comments.set(globalClockCycle, currentComment + "\t" + comment);
    }

    public static CDB getCdb() {
        return cdb;
    }
}