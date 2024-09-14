package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartEntity implements Serializable{
    
    @Id
    @Column(name = "")
    private int id;
    
    @Column(name = "qty",nullable = false)
    private double qty;
    
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;
    
    @JoinColumn(name = "product_id")
    @ManyToOne
    private ProductEntity product;
    
    public  CartEntity(){
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
    
    
    
    
}
