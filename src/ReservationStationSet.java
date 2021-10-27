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

    public void offer(Instruction instruction) {
        if(!isFull()) {
            for(ReservationStation station : reservationStations) {
                if(!station.isBusy()) {
                    station.setInstruction(instruction);
                    station.setName(type + (++nameCount));
                    station.setBusy(true);
                    station.setOperation(instruction.getOp());
                }
            }
        }
    }

    public boolean isFull() {
        return size == capacity;
    }
}
