package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_products", schema = "music_store", catalog = "")
@IdClass(OrdersProductsEntityPK.class)
public class OrdersProductsEntity {
    private int orderId;
    private int productId;

    @Id
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;



    private AlbumViewEntity albumViewEntity;
    private InstrumentViewEntity instrumentViewEntity;
    private OtherViewEntity otherViewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    public AlbumViewEntity getAlbumViewEntity() {
        return albumViewEntity;
    }

    public void setAlbumViewEntity(AlbumViewEntity albumViewEntity) {
        this.albumViewEntity = albumViewEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    public InstrumentViewEntity getInstrumentViewEntity() {
        return instrumentViewEntity;
    }

    public void setInstrumentViewEntity(InstrumentViewEntity instrumentViewEntity) {
        this.instrumentViewEntity = instrumentViewEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    public OtherViewEntity getOtherViewEntity() {
        return otherViewEntity;
    }

    public void setOtherViewEntity(OtherViewEntity otherViewEntity) {
        this.otherViewEntity = otherViewEntity;
    }




    @Id
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersProductsEntity that = (OrdersProductsEntity) o;

        if (orderId != that.orderId) return false;
        if (productId != that.productId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + productId;
        return result;
    }
}
