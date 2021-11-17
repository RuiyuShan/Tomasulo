package entity;

import entity.instruction.InstructionForFP;
import entity.instruction.Load;
import entity.rs.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class CDB {
    /**
     * In each cycle the cdb receives results of execution completed reservation stations, and broadcast the result of
     * instruction whose pc is smallest.
     */
    private Queue<ReservationStation> queue = new PriorityQueue<>(Comparator.comparingInt(r -> r.getInstruction().getPc()));

    Register[] registers;

    LoadBuffers loadBuffers;

    StoreBuffers storeBuffers;

    AdderRSs adderRSs;

    MultDivRSs multDivRSs;

    public CDB(Register[] registers, LoadBuffers loadBuffers, StoreBuffers storeBuffers, AdderRSs adderRSs, MultDivRSs multDivRSs) {
        this.registers = registers;
        this.loadBuffers = loadBuffers;
        this.storeBuffers = storeBuffers;
        this.adderRSs = adderRSs;
        this.multDivRSs = multDivRSs;
    }

    public void offer(ReservationStation station) {
        queue.offer(station);
    }


    public void broadcastAndWriteResult() throws Exception {
        if (!queue.isEmpty()) {
            ReservationStation station = queue.poll();
            loadBuffers.receiveBroadCastResult(station);
            storeBuffers.receiveBroadCastResult(station);
            adderRSs.receiveBroadCastResult(station);
            multDivRSs.receiveBroadCastResult(station);
            // Mark the target reservation station write result success.
            station.updateStatusOfResult(loadBuffers, storeBuffers, adderRSs, multDivRSs);
            // Removes all the elements from this collection
            queue.clear();
            // Update register value.
            Double result = station.getResult();
            switch (station.getOperation()) {
                case LOAD -> {
                    Load load = (Load) station.getInstruction();
                    load.getRs().setValue(result);
                }
                case ADD, SUB, MUL, DIV -> {
                    InstructionForFP instructionForFP = (InstructionForFP) station.getInstruction();
                    instructionForFP.getRd().setValue(result);
                }
                default -> {}
            }
        }
    }
}
