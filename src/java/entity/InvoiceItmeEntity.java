package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_item")
public class InvoiceItmeEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "qty", nullable = false)
    private double qty;

    @JoinColumn(name = "invoice_id")
    @ManyToOne
    private InvoiceEntity invoice_id;
    
    @JoinColumn(name = "product_id")
    @ManyToOne
    private ProductEntity product_id;

    public InvoiceItmeEntity() {

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

    public InvoiceEntity getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(InvoiceEntity invoice_id) {
        this.invoice_id = invoice_id;
    }

    public ProductEntity getProduct_id() {
        return product_id;
    }

    public void setProduct_id(ProductEntity product_id) {
        this.product_id = product_id;
    }

   

}
