import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Tomasulo {
    Queue<Instruction> instructionQueue;

    Register[] registers;

    public static void schedule() {


    }

    public void load() {
        Integer pc = 0;
        Integer addCount = 0;
        Integer mulCount = 0;
        instructionQueue = new ArrayDeque<>();
        List<String> instructionStringList = readInstructionsFromFile();
        for(String instructionStr : instructionStringList) {
            String operation = instructionStr.split("\\s*")[1];
            String[] registers = instructionStr.split("\\s*")[2].split("\\s*,\\s*");
            switch (operation) {
                case "Load":

            }
        }
    }

    /**
     * read the instruction text file
     *
     * @return instruction string list
     */
    private List<String> readInstructionsFromFile() {
        List<String> res = new ArrayList<>();
        try {
            File file = new File("instructions.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    res.add(lineTxt);
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}