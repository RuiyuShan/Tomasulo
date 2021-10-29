package entity;

import entity.rs.ReservationStation;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ReservationStation getQi() {
        return qi;
    }

    public void setQi(ReservationStation qi) {
        this.qi = qi;
    }

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
        return "entity.Register {" +
                "name='" + name + '\'' +
                ", value=" + value +
                "}";
    }
}
