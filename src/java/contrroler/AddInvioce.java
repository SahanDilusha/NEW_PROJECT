package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.AddressEntity;
import entity.ContryEntity;
import entity.UserEntity;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Payhere;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet(name = "AddInvioce", urlPatterns = {"/AddInvioce"})
public class AddInvioce extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("status", false);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        AddressEntity userAddress = null;

        try {

            UserEntity user = (UserEntity) req.getSession().getAttribute("user");

            System.out.println(user.getEmail());
            
            if (user == null) {
                responseJsonObject.addProperty("content", "Session error!");
                sendResponse(resp, responseJsonObject);
                return;
            }

            JsonObject reqObject = (JsonObject) gson.fromJson(req.getReader(), JsonObject.class);

            System.out.println(reqObject.size());
            
            String firstName = reqObject.get("firstName").getAsString().trim();
            String lastName = reqObject.get("lastName").getAsString().trim();
            String country = reqObject.get("country").getAsString().trim();
            String address1 = reqObject.get("address1").getAsString().trim();
            String address2 = reqObject.get("address2").getAsString().trim();
            String city = reqObject.get("city").getAsString().trim();
            String state = reqObject.get("state").getAsString().trim();
            String zipcode = reqObject.get("zipcode").getAsString().trim();
            String phone = reqObject.get("phone").getAsString().trim();
            String email = reqObject.get("email").getAsString().trim();

            System.out.println(firstName);
            
            if (!validateFields(firstName, "First Name", responseJsonObject)
                    || !validateFields(lastName, "Last Name", responseJsonObject)
                    || !validateFields(country, "Country", responseJsonObject)
                    || !validateFields(address1, "Address", responseJsonObject)
                    || !validateFields(city, "City", responseJsonObject)
                    || !validateFields(state, "State", responseJsonObject)
                    || !validateFields(zipcode, "Zip Code", responseJsonObject)
                    || !validateFields(phone, "Phone", responseJsonObject)
                    || !validateFields(email, "Email", responseJsonObject)) {
                System.out.println("gg");
                sendResponse(resp, responseJsonObject);
                return;
            }
            boolean checkAddress = true;
            List<AddressEntity> addressList = session.createCriteria(AddressEntity.class).list();

            System.out.println("ko1");

            if (!addressList.isEmpty()) {
                for (AddressEntity addressEntity : addressList) {
                    if (addressEntity.getEmail().equals(email) && addressEntity.getMobile().equals(phone)) {
                        userAddress = addressEntity;
                        checkAddress = false;
                        break;
                    }
                    checkAddress = true;
                }
            }

            if (checkAddress) {
                AddressEntity newAddress = new AddressEntity();
                newAddress.setCity(city);
                newAddress.setCountry_id(new ContryEntity(Integer.parseInt(country)));
                newAddress.setEmail(email);
                newAddress.setLine_1(address1);
                newAddress.setLine_2(address2);
                newAddress.setMobile(phone);
                newAddress.setPost_code(zipcode);
                newAddress.setState(state);
                newAddress.setStatus(2);
                newAddress.setUser_id(user);

                int id = (int) session.save(newAddress);
                newAddress.setId(id);
                userAddress = newAddress;
            }

            transaction.commit();
            
            saveOrders(session, transaction, user, userAddress, responseJsonObject);
            
            responseJsonObject.addProperty("status", true);
            responseJsonObject.addProperty("content", "Invoice successfully created!");

        } catch (Exception e) {
            transaction.rollback();
            responseJsonObject.addProperty("content", "Error creating invoice.");
        } finally {
            session.close();  // Ensure the session is closed
            sendResponse(resp, responseJsonObject);
        }
    }
    
    private void saveOrders(Session session, Transaction transaction, UserEntity user, AddressEntity address, JsonObject responseJsonObject) {
        
        
        try {
      

            String merchant_id = "1227354";
            String formatted_amount = new DecimalFormat("0.00").format(3000);
            String currency = "LKR";
            String merchantSecret = "NDI4ODc5ODA4OTI4OTQzMzMxMDEyNDc4MDA1NDMyNDE4OTIw"; 
            String merchantSecretMdHash = Payhere.generateMD5(merchantSecret);

            JsonObject payhere = new JsonObject();
            payhere.addProperty("merchant_id", merchant_id);

            payhere.addProperty("return_url", "");
            payhere.addProperty("cancel_url", "");
            payhere.addProperty("notify_url", ""); //***

            payhere.addProperty("first_name", user.getFirst_name());
            payhere.addProperty("last_name", user.getLast_name());
            payhere.addProperty("email", user.getEmail());

            payhere.addProperty("phone", "");
            payhere.addProperty("address", "");
            payhere.addProperty("city", "");
            payhere.addProperty("country", "");

            payhere.addProperty("order_id", String.valueOf(123456));
            payhere.addProperty("items", "");
            payhere.addProperty("currency", "LKR");
            payhere.addProperty("amount", formatted_amount);
            payhere.addProperty("sandbox", true);

            //Generate MD5 Hash
            //merahantID + orderID + amountFormatted + currency + getMd5(merchantSecret)
            String md5Hash = Payhere.generateMD5(merchant_id + 123456 + formatted_amount + currency + merchantSecretMdHash);
            payhere.addProperty("hash", md5Hash);

            //set payment data (end)
            responseJsonObject.addProperty("success", true);
            responseJsonObject.addProperty("message", "Checkout completed");

            Gson gson = new Gson();
            responseJsonObject.add("payhereJson", gson.toJsonTree(payhere));

        } catch (Exception e) {
            transaction.rollback();
        }
    }
    

    private boolean validateFields(String fieldValue, String fieldName, JsonObject responseJsonObject) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            responseJsonObject.addProperty("content", fieldName + " is required.");
            return false;
        }
        return true;
    }

    private void sendResponse(HttpServletResponse resp, JsonObject responseJsonObject) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseJsonObject));
    }
}
