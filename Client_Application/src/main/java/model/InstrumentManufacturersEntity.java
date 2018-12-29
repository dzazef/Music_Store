package model;

import javax.persistence.*;

@Entity
@Table(name = "instrument_manufacturers", schema = "music_store", catalog = "")
public class InstrumentManufacturersEntity {
    private int manufacturerId;
    private String name;

    @Id
    @Column(name = "manufacturer_id")
    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentManufacturersEntity that = (InstrumentManufacturersEntity) o;

        if (manufacturerId != that.manufacturerId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = manufacturerId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
