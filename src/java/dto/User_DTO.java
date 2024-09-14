package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class User_DTO implements Serializable {

    @Expose
    private String email;

    @Expose(serialize = false, deserialize = true)
    private String password;

    @Expose
    private String first_name;

    @Expose
    private String last_name;

    @Expose
    private String otp;
    
    @Expose
    private boolean dp;

    public User_DTO() {

    }

    public User_DTO(String email, String password, String first_name, String last_name, String otp) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isDp() {
        return dp;
    }

    public void setDp(boolean dp) {
        this.dp = dp;
    }

}
