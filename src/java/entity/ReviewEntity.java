package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class ReviewEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "datetime", nullable = false)
    private Date date;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "admin_text", nullable = true)
    private String adminText;

    @JoinTable(name = "product")
    @ManyToOne
    private ProductEntity product;

    @Column(name = "name", length = 70, nullable = false)
    private String name;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "rateit", nullable = false)
    private double rateit;

    public ReviewEntity() {

    }

    public ReviewEntity(String name, String text, Date date, double rateit, ProductEntity product, String email) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.rateit = rateit;
        this.product = product;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdminText() {
        return adminText;
    }

    public void setAdminText(String adminText) {
        this.adminText = adminText;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getRateit() {
        return rateit;
    }

    public void setRateit(double rateit) {
        this.rateit = rateit;
    }

}
