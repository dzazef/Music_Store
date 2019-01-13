package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "storage", schema = "music_store", catalog = "")
public class StorageEntity {
    private int productId;
    private int productsAvailable;
    private AlbumViewEntity albumViewEntity;
    private InstrumentViewEntity instrumentViewEntity;
    private OtherViewEntity otherViewEntity;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    public AlbumViewEntity getAlbumViewEntity() {
        return albumViewEntity;
    }

    public void setAlbumViewEntity(AlbumViewEntity albumViewEntity) {
        this.albumViewEntity = albumViewEntity;
    }

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    public InstrumentViewEntity getInstrumentViewEntity() {
        return instrumentViewEntity;
    }

    public void setInstrumentViewEntity(InstrumentViewEntity instrumentViewEntity) {
        this.instrumentViewEntity = instrumentViewEntity;
    }

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    public OtherViewEntity getOtherViewEntity() {
        return otherViewEntity;
    }

    public void setOtherViewEntity(OtherViewEntity otherViewEntity) {
        this.otherViewEntity = otherViewEntity;
    }

    @Id
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "products_available")
    public int getProductsAvailable() {
        return productsAvailable;
    }

    public void setProductsAvailable(int productsAvailable) {
        this.productsAvailable = productsAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageEntity that = (StorageEntity) o;

        if (productId != that.productId) return false;
        if (productsAvailable != that.productsAvailable) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + productsAvailable;
        return result;
    }
}
