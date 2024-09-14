package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;

public class ProductDTO implements Serializable {

    @Expose
    private int id;

    @Expose
    private String titile;

    @Expose
    private String description;

    @Expose
    private int qty;

    @Expose
    private int barndId;

    @Expose
    private String barndName;

    @Expose
    private int mainCategoryId;

    @Expose
    private String mainCategoryName;

    @Expose
    private int subCategoryId;

    @Expose
    private String subCategoryName;

    @Expose
    private int conditionId;

    @Expose
    private String consumerName;

    @Expose
    private double shipping;

    @Expose
    private double price;
    
    @Expose
    private double rateit;

    @Expose
    private int productSatusId;

    @Expose
    private String productSatusName;

    @Expose
    private Date date;

    @Expose
    private boolean shippingAdd;

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public ProductDTO() {

    }

    public ProductDTO(int id, String title, String description, int qty, String brandName,
            String mainCategoryName, String subCategoryName, String consumerName,
            double shipping, double price, int productStatusId, String productStatusName,
            Date date,double rateit) {
        this.id = id;
        this.titile = title;
        this.description = description;
        this.qty = qty;
        this.barndName = brandName;
        this.mainCategoryName = mainCategoryName;
        this.subCategoryName = subCategoryName;
        this.consumerName = consumerName;
        this.shipping = shipping;
        this.price = price;
        this.productSatusId = productStatusId;
        this.productSatusName = productStatusName;
        this.date = date;
        this.rateit = rateit;
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

    public int getBarndId() {
        return barndId;
    }

    public void setBarndId(int barndId) {
        this.barndId = barndId;
    }

    public String getBarndName() {
        return barndName;
    }

    public void setBarndName(String barndName) {
        this.barndName = barndName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }


    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public int getProductSatusId() {
        return productSatusId;
    }

    public void setProductSatusId(int productSatusId) {
        this.productSatusId = productSatusId;
    }

    public String getProductSatusName() {
        return productSatusName;
    }

    public void setProductSatusName(String productSatusName) {
        this.productSatusName = productSatusName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(int mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public boolean isShippingAdd() {
        return shippingAdd;
    }

    public void setShippingAdd(boolean shippingAdd) {
        this.shippingAdd = shippingAdd;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRateit() {
        return rateit;
    }

    public void setRateit(double rateit) {
        this.rateit = rateit;
    }

}
