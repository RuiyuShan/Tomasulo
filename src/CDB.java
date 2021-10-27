/**
 * @author shanruiyu <shanruiyu@kuaishou.com>
 * Created on 2021-10-27
 */
public class CDB {
    private boolean available;

    private String destinationRegister;

    private Double value;

    Register[] registers;

    public CDB(Register[] registers) {
        this.registers = registers;
    }

    public boolean available() {
        return available;
    }

    public boolean receive(String register, Double value) {
        boolean success = available;
        if(available) {
            this.destinationRegister = register;
            this.value = value;
            available = false;
        }
        return success;
    }

    public boolean send() {

    }
}
