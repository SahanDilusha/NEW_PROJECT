
package dto;

import java.io.Serializable;



public class Cart_DTO implements Serializable {

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
    
    private int pid;
    private double qty;
    private String title;
    private double price;
    
    
    public Cart_DTO() {
        
    }
    
}
