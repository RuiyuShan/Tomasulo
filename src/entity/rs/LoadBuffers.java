package entity.rs;

import java.util.Map;

import entity.Register;
import entity.instruction.Instruction;
import entity.instruction.Load;
import entity.instruction.Phase;
import main.Tomasulo;

public class LoadBuffers extends ReservationStationSet implements InstructionScheduler<Load> {

    Map<String, Double> addressImmediateMap;

    public LoadBuffers(Map<String, Double> addressImmediateMap) {
        super(5, "Load");
        this.addressImmediateMap = addressImmediateMap;
    }


    @Override
    public void issue(Load load) throws Exception {
        if (offer(load)) {
            ReservationStation station = getReservationStationByInstruction(load);
            station.setA(load.getAddress());
            station.incrementClockCycle();
            load.setPhase(Phase.ISSUE);
            station.addComment(Phase.ISSUE.getValue());
        } else {
            throw new Exception("Load buffer is full");
        }
    }

    @Override
    public void execute(ReservationStation loadBuffer) throws Exception {
        Load load = (Load) loadBuffer.getInstruction();
        if (load.getPhase() == Phase.ISSUE) {
            load.setPhase(Phase.EXECUTING);
            loadBuffer.addComment(Phase.EXECUTION_START.getValue());
        }
        loadBuffer.incrementClockCycle();
        if (loadBuffer.isExecutionCompleted()) {
            loadBuffer.setResult(addressImmediateMap.get(loadBuffer.getA()));
            load.setPhase(Phase.EXECUTION_COMPLETE);
            loadBuffer.addComment(Phase.EXECUTION_COMPLETE.getValue());
        }
    }

    @Override
    public void writeResult(ReservationStation reservationStation) throws Exception {
        Load load = (Load) reservationStation.getInstruction();
        Register rs = load.getRs();
        Tomasulo.getCdb().receive(rs.getName(), reservationStation.getResult());
        Tomasulo.getCdb().send();
        load.setPhase(Phase.WRITE_RESULT);
        reservationStation.setBusy(false);
        reservationStation.addComment(Phase.WRITE_RESULT.getValue());
    }

    @Override
    public void schedule() throws Exception {
        for (ReservationStation station : reservationStations) {
            Load load = (Load) station.getInstruction();
            switch (load.getPhase()) {
                case ISSUE -> execute(station);
                case EXECUTING -> execute(station);
                case EXECUTION_COMPLETE -> writeResult(station);
            }
        }
    }
}
