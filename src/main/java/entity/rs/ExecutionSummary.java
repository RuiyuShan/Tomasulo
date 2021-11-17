package entity.rs;

import java.util.ArrayList;
import java.util.List;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import utils.Utils;

public class ExecutionSummary {
    static List<ReservationStation> reservationStations = new ArrayList<>(5);


    public static String TableString() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Name", "Busy", "Phase", "Op", "Vj", "Vk", "Qj", "Qk", "A");
        for (ReservationStation station : reservationStations) {
            if (station == null) {
                at.addRule();
                at.addRow("", "false", "", "", "", "", "", "", "", "");
                continue;
            }
            String name = station.getName() == null ? "" : station.getName();
            String busy = station.isBusy() ? "true" : "false";
            String phase = station.getInstruction() == null ? "" : station.getInstruction().getPhase().name();
            String op = station.getOperation() == null ? "" : station.getOperation().name();
            String vj = station.getVj() == null ? "" : Utils.keepFourDecimalPlaces(station.getVj());
            String vk = station.getVk() == null ? "" : Utils.keepFourDecimalPlaces(station.getVk());
            String qj = station.getQj() == null ? "" : station.getQj().getName();
            String qk = station.getQk() == null ? "" : station.getQk().getName();
            String a = station.getA() == null ? "" : station.getA();
            at.addRule();
            at.addRow(name, busy, phase, op, vj, vk, qj, qk, a);
        }
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        return "\tSummary" + "\n" +  at.render(120) + "\n";
    }
}
