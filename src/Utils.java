import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shanruiyu <shanruiyu@kuaishou.com>
 * Created on 2021-10-27
 */
public class Utils {
    /**
     * get index of a register.
     * e.g. register name is f0 then return 0.
     * @param registerName
     * @return index the of register
     */
    public static int getRegisterIndexByName(String registerName) {
        char[] c = registerName.toCharArray();
        int res = 0;
        for (char ch : c) {
            if(Character.isDigit(ch)){
                res = res * 10 + (ch - '0');
            }
        }
        return res;
    }

    /**
     * read the instruction text file
     *
     * @return instruction string list
     */
    public static List<String> readInstructionsFromFile() {
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

    /**
     * remove the ',' for every part of an instruction string.
     * @param instructionComponents
     * @return removed instruction parts.
     */
    public static String[] removeComma(String[] instructionComponents) {
        for (int i = 0; i < instructionComponents.length; i++) {
            int tail = instructionComponents[i].length() - 1;
            while (!Character.isLetterOrDigit(instructionComponents[i].charAt(tail))) {
                tail--;
            }
            instructionComponents[i] = instructionComponents[i].substring(0, tail + 1);
        }
        return instructionComponents;
    }

}
