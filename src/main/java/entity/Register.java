package entity;

import entity.rs.ReservationStation;
import lombok.Data;

@Data   // @Data provides Getter and Setter methods for this class.
public class Register {
    /**
     * id of register
     */
    Integer id;

    /**
     * name of register
     */
    String name;

    /**
     * value of register
     */
    Double value;

    /**
     * The reference of the reservation station that contains the operation whose result should be stored into this register.
     */
    ReservationStation qi;

    public Register(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Register(Integer id, String name, Double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "" + name + '\'' +
                ", " + value +
                "}";
    }
}
