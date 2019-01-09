package model.entities;



import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "instrument_view", schema = "music_store", catalog = "")
public class InstrumentViewEntity {
    private int productId;
    private String category;
    private double price;
    private int manufacturerId;
    private String manufacturerName;
    private String instrumentName;
    private String type;

    @Id
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "category", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "manufacturer_id", nullable = false)
    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Basic
    @Column(name = "manufacturer_name", nullable = false, length = 50)
    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Basic
    @Column(name = "instrument_name", nullable = false, length = 50)
    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 80)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentViewEntity that = (InstrumentViewEntity) o;

        if (productId != that.productId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (manufacturerId != that.manufacturerId) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (manufacturerName != null ? !manufacturerName.equals(that.manufacturerName) : that.manufacturerName != null)
            return false;
        if (instrumentName != null ? !instrumentName.equals(that.instrumentName) : that.instrumentName != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = productId;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + manufacturerId;
        result = 31 * result + (manufacturerName != null ? manufacturerName.hashCode() : 0);
        result = 31 * result + (instrumentName != null ? instrumentName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
