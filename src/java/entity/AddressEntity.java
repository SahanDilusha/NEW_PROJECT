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
@Table(name = "address")
public class AddressEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "line_1", length = 45, nullable = false)
    private String line_1;

    @Column(name = "line_2", length = 45, nullable = false)
    private String line_2;

    @Column(name = "mobile", length = 10, nullable = false)
    private String mobile;

    @Column(name = "post_code", length = 10, nullable = false)
    private String post_code;

    @Column(name = "state", length = 45, nullable = false)
    private String state;

    @Column(name = "city", length = 45, nullable = false)
    private String city;

    @Column(name = "status", length = 45, nullable = false)
    private int status;

    @JoinColumn(name = "country_id")
    @ManyToOne
    private ContryEntity country_id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user_id;

    public AddressEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine_1() {
        return line_1;
    }

    public void setLine_1(String line_1) {
        this.line_1 = line_1;
    }

    public String getLine_2() {
        return line_2;
    }

    public void setLine_2(String line_2) {
        this.line_2 = line_2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContryEntity getCountry_id() {
        return country_id;
    }

    public void setCountry_id(ContryEntity country_id) {
        this.country_id = country_id;
    }

    public UserEntity getUser_id() {
        return user_id;
    }

    public void setUser_id(UserEntity user_id) {
        this.user_id = user_id;
    }

}
