package entity.rs;

import entity.instruction.Instruction;
import main.Tomasulo;

public abstract class ReservationStationSet {
    private final String type;

    private final int capacity;

    private int nameCount;

    private static int size;

    private ReservationStation[] reservationStations;

    public ReservationStationSet(int capacity, String type) {
        this.capacity = capacity;
        this.type = type;
        reservationStations = new ReservationStation[capacity];
        for (int i = 0; i < capacity; i++) {
            reservationStations[i] = new ReservationStation();
        }
    }

    public boolean initializeReservationStation(Instruction instruction) {
        boolean success = false;
        if(available()) {
            size++;
            for (int i = 0; i < reservationStations.length; i++) {
                if(!reservationStations[i].isBusy()) {
                    // clear reservation station status
                    reservationStations[i] = new ReservationStation();
                    // link the instruction to the rs
                    reservationStations[i].setInstruction(instruction);
                    // set rs name
                    reservationStations[i].setName(type + (++nameCount));
                    reservationStations[i].setBusy(true);
                    reservationStations[i].setOperation(instruction.getOp());
                    instruction.setGlobalClockCycleIssuedAt(Tomasulo.getGlobalClockCycle());
                    success = true;
                    break;
                }
            }
        }
        return success;
    }

    public ReservationStation getReservationStationByInstruction(Instruction instruction) {
        ReservationStation res = null;
        for (ReservationStation station : reservationStations) {
            if (station.getInstruction() == instruction) {
                res = station;
                break;
            }
        }
        return res;
    }

    public boolean available() {
        return size != capacity;
    }

    public void receiveBroadCastResult(ReservationStation source){
        for (ReservationStation station : reservationStations) {
            if (station.getQj() == source) {
                station.setVj(source.getResult());
                station.setQj(null);
            }
            if (station.getQk() == source) {
                station.setVk(source.getResult());
                station.setQk(null);
            }
        }
    }

    public ReservationStation[] getReservationStations() {
        return reservationStations;
    }

    public static void decreaseSize() throws Exception{
        if (size > 0) {
            size--;
        } else {
            throw new Exception("size cannot decrease because size == 0.");
        }
    }

    public boolean isEmpty(){
        boolean res = true;
        for (ReservationStation station : reservationStations) {
            if (station.isBusy()) {
                return false;
            }
        }
        return res;
    }

}
