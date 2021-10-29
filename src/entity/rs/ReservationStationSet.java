package entity.rs;

import entity.instruction.Instruction;
import entity.instruction.InstructionForFP;
import entity.instruction.InstructionForLoadStore;

/**
 * @author shanruiyu <shanruiyu@kuaishou.com>
 * Created on 2021-10-27
 */
public abstract class ReservationStationSet {
    final String type;

    final int capacity;

    int nameCount;

    int size;

    ReservationStation[] reservationStations;

    public ReservationStationSet(int capacity, String type) {
        this.capacity = capacity;
        this.type = type;
        reservationStations = new ReservationStation[capacity];
        for (int i = 0; i < capacity; i++) {
            reservationStations[i] = new ReservationStation();
        }
    }

    public boolean offer(Instruction instruction) {
        boolean success = false;
        if(available()) {
            size++;
            for(ReservationStation station : reservationStations) {
                if(!station.isBusy()) {
                    station = new ReservationStation(); // clear reservation station status
                    station.setInstruction(instruction);
                    station.setName(type + (++nameCount));
                    station.setBusy(true);
                    station.setOperation(instruction.getOp());
                    if (instruction instanceof InstructionForLoadStore) {
                        ((InstructionForLoadStore) instruction).getRs().setQi(station);
                    } else {
                        ((InstructionForFP) instruction).getRd().setQi(station);
                    }
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
}
