package entity;

import entity.rs.ReservationStation;
import utils.Utils;

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
        boolean success = !available;
        if(!available && destinationRegister != null && value != null) {
            int idx = Utils.getRegisterIndexByName(destinationRegister);
            registers[idx].setValue(value);
            available = true;
            destinationRegister = null;
            value = null;
        }
        return success;
    }

    public void broadcast(ReservationStation station){}
}
