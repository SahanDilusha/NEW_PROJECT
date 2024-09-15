package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class InvoiceEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "datetime", nullable = false)
    private Date datetime;

    @Column(name = "payment_method", nullable = false)
    private boolean payment_method;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user_id;

    @JoinColumn(name = "invoce_status_id")
    @ManyToOne
    private InvoiceStatus invoce_status_id;


    public InvoiceEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean isPayment_method() {
        return payment_method;
    }

    public void setPayment_method(boolean payment_method) {
        this.payment_method = payment_method;
    }

    public UserEntity getUser_id() {
        return user_id;
    }

    public void setUser_id(UserEntity user_id) {
        this.user_id = user_id;
    }

    public InvoiceStatus getInvoce_status_id() {
        return invoce_status_id;
    }

    public void setInvoce_status_id(InvoiceStatus invoce_status_id) {
        this.invoce_status_id = invoce_status_id;
    }

}
