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
@Table(name = "product")
public class ProductEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", length = 150, nullable = false)
    private String titile;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "qty", nullable = false)
    private int qty;

    @JoinColumn(name = "barnd_id")
    @ManyToOne
    private BrandEntity barnd;

    @JoinColumn(name = "main_category_id")
    @ManyToOne
    private MainCategoryEntity mainCategory;

    @JoinColumn(name = "sub_category_id")
    @ManyToOne
    private SubCategoryEntity subCategory;

    @JoinColumn(name = "condition_id")
    @ManyToOne
    private ConditionEntity condition_id;

    @Column(name = "shipping", nullable = false)
    private double shipping;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "rateit", nullable = false)
    private double rateit;

    @JoinColumn(name = "product_status_id")
    @ManyToOne
    private ProductSatusEntity productSatus;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;

    @Column(name = "product_date", nullable = false)
    private Date date;

    public ProductEntity() {

    }

    public ProductEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BrandEntity getBarnd() {
        return barnd;
    }

    public void setBarnd(BrandEntity barnd) {
        this.barnd = barnd;
    }

    public MainCategoryEntity getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategoryEntity mainCategory) {
        this.mainCategory = mainCategory;
    }

    public SubCategoryEntity getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategoryEntity subCategory) {
        this.subCategory = subCategory;
    }

   

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public ProductSatusEntity getProductSatus() {
        return productSatus;
    }

    public void setProductSatus(ProductSatusEntity productSatus) {
        this.productSatus = productSatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public double getRateit() {
        return rateit;
    }

    public void setRateit(double rateit) {
        this.rateit = rateit;
    }

    public ConditionEntity getCondition_id() {
        return condition_id;
    }

    public void setCondition_id(ConditionEntity condition_id) {
        this.condition_id = condition_id;
    }

}
