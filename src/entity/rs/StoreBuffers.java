package entity.rs;

import entity.instruction.Phase;
import entity.instruction.Store;
import main.Tomasulo;

import java.util.Map;

public class StoreBuffers extends ReservationStationSet implements InstructionScheduler<Store>{

    Map<String, Double> addressImmediateMap;

    public StoreBuffers(Map<String, Double> addressImmediateMap) {
        super(5, "Store");
        this.addressImmediateMap = addressImmediateMap;
    }

    @Override
    public void issue(Store store) throws Exception {
        if (initializeReservationStation(store)) {
            ReservationStation station = getReservationStationByInstruction(store);
            station.setA(store.getAddress());
            station.increaseClockCycle();
            store.setPhase(Phase.ISSUE);
            station.addComment(Phase.ISSUE.getValue());
        }
    }

    @Override
    public void execute(ReservationStation storeBuffer) throws Exception {
        if (storeBuffer.getInstruction().getGlobalClockCycleIssuedAt().equals(Tomasulo.getGlobalClockCycle())){
            return;
        }
        if (storeBuffer.readyToExecute()) {
            Store store = (Store) storeBuffer.getInstruction();
            if (store.getPhase() == Phase.ISSUE) {
                store.setPhase(Phase.EXECUTING);
                storeBuffer.addComment(Phase.EXECUTION_START.getValue());
            }
            storeBuffer.increaseClockCycle();
            if (storeBuffer.isExecutionCompleted()) {
                storeBuffer.setResult(store.getRs().getValue());
                store.setPhase(Phase.EXECUTION_COMPLETE);
                storeBuffer.addComment(Phase.EXECUTION_COMPLETE.getValue());
            }
        }
    }

    @Override
    public void writeResult(ReservationStation reservationStation) throws Exception {
        addressImmediateMap.put(reservationStation.getA(), reservationStation.getResult());
        reservationStation.updateStatusOfResult();
    }

    @Override
    public void schedule() throws Exception {
        for (ReservationStation station : getReservationStations()) {
            if (station.isBusy()) {
                Store store = (Store) station.getInstruction();
                switch (store.getPhase()) {
                    case ISSUE -> execute(station);
                    case EXECUTING -> execute(station);
                    case EXECUTION_COMPLETE -> writeResult(station);
                    default -> {}
                }
            }
        }
    }

}
