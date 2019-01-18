package model.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_products", schema = "music_store", catalog = "")
@IdClass(OrdersProductsEntityPK.class)
public class OrdersProductsEntity {
    private int orderId;
    private int productId;
    private int quantity;

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

    @Id
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersProductsEntity that = (OrdersProductsEntity) o;

        if (orderId != that.orderId) return false;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + productId;
        return result;
    }
}
