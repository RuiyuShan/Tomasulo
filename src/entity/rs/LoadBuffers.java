package entity.rs;

import entity.instruction.Load;
import entity.instruction.Phase;
import main.Tomasulo;

import java.util.Map;

public class LoadBuffers extends ReservationStationSet implements InstructionScheduler<Load> {

    Map<String, Double> addressImmediateMap;

    public LoadBuffers(Map<String, Double> addressImmediateMap) {
        super(5, "Load");
        this.addressImmediateMap = addressImmediateMap;
    }


    @Override
    public void issue(Load load) throws Exception {
        if (initializeReservationStation(load)) {
            ReservationStation station = getReservationStationByInstruction(load);
            load.getRs().setQi(station);
            station.setA(load.getAddress());
            station.increaseClockCycle();
            load.setPhase(Phase.ISSUE);
            station.addComment(Phase.ISSUE.getValue());
        } else {
            throw new Exception("Load buffer is full");
        }
    }

    @Override
    public void execute(ReservationStation loadBuffer) throws Exception {
        if (loadBuffer.getInstruction().getGlobalClockCycleIssuedAt().equals(Tomasulo.getGlobalClockCycle())){
            return;
        }
        if (loadBuffer.readyToExecute()) {
            Load load = (Load) loadBuffer.getInstruction();
            if (load.getPhase() == Phase.ISSUE) {
                load.setPhase(Phase.EXECUTING);
                loadBuffer.addComment(Phase.EXECUTION_START.getValue());
            }
            loadBuffer.increaseClockCycle();
            if (loadBuffer.isExecutionCompleted()) {
                loadBuffer.setResult(addressImmediateMap.get(loadBuffer.getA()));
                load.setPhase(Phase.EXECUTION_COMPLETE);
                loadBuffer.addComment(Phase.EXECUTION_COMPLETE.getValue());
            }
        }
    }

    @Override
    public void writeResult(ReservationStation reservationStation) throws Exception {
        Tomasulo.getCdb().offer(reservationStation);
    }

    @Override
    public void schedule() throws Exception {
        for (ReservationStation station : getReservationStations()) {
            if (station.isBusy()) {
                Load load = (Load) station.getInstruction();
                switch (load.getPhase()) {
                    case ISSUE -> execute(station);
                    case EXECUTING -> execute(station);
                    case EXECUTION_COMPLETE -> writeResult(station);
                    default -> {}
                }
            }
        }
    }

}
