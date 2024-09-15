package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class ContryEntity implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    private int id;
    
    @Column(name = "name",length = 45,nullable = false)
    private String name;
    
    @Column(name = "satus",length = 3,nullable = false)
    private int satus;
    
    public ContryEntity(){
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSatus() {
        return satus;
    }

    public void setSatus(int satus) {
        this.satus = satus;
    }
    
    
    
}
