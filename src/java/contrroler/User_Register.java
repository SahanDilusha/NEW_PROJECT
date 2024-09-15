package contrroler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Mail;
import model.Validations;
import dto.Response_DTO;
import dto.User_DTO;
import entity.CartEntity;
import entity.UserEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "User_Register", urlPatterns = {"/User_Register"})
public class User_Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        User_DTO userDTO = gson.fromJson(request.getReader(), User_DTO.class);

        Response_DTO responseDTO = new Response_DTO();

        if (userDTO.getFirst_name().isEmpty()) {
            responseDTO.setContent("First name is required.");
        } else if (userDTO.getLast_name().isEmpty()) {
            responseDTO.setContent("Last name is required.");
        } else if (userDTO.getEmail().isEmpty()) {
            responseDTO.setContent("Email is required.");
        } else if (!Validations.isEmailValid(userDTO.getEmail())) {
            responseDTO.setContent("Invalid email format.");
        } else if (userDTO.getPassword().isEmpty()) {
            responseDTO.setContent("Password is required.");
        } else if (!Validations.isPasswordValid(userDTO.getPassword())) {
            responseDTO.setContent("Password must be between 8-45 characters long, and include at least one digit, one uppercase letter, one lowercase letter, and one special character.");
        } else {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Criteria criteria = session.createCriteria(UserEntity.class)
                    .add(Restrictions.eq("email", userDTO.getEmail()));

            if (criteria.list().isEmpty()) {

                UserEntity newUser = new UserEntity();
                int code = (int) (Math.random() * 1000000);

                newUser.setEmail(userDTO.getEmail());
                newUser.setFirst_name(userDTO.getFirst_name());
                newUser.setLast_name(userDTO.getLast_name());
                newUser.setPassword(userDTO.getPassword());
                newUser.setType(true);
                newUser.setOtp(String.valueOf(code));
                newUser.setVerify("1");
                newUser.setDp(false);

                int iduser = (int) session.save(newUser);
                newUser.setId(iduser);

                List<CartEntity> sessionCart = (List<CartEntity>) request.getSession().getAttribute("sessionCart");

                if (sessionCart != null && !sessionCart.isEmpty()) {
                    for (CartEntity cartItem : sessionCart) {
                        cartItem.setUser(newUser);
                        session.save(cartItem);
                    }
                }

                session.getTransaction().commit();

                Thread emailThread = new Thread(() -> {
                    Mail.sendMail(newUser.getEmail(), "Verify Your Account", "<!DOCTYPE html>\n"
                            + "<html lang=\"en\">\n"
                            + "<head>\n"
                            + "    <meta charset=\"UTF-8\">\n"
                            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    <title>Verify Your Account</title>\n"
                            + "    <style>\n"
                            + "        body { margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #e9ecef; }\n"
                            + "        .email-container { max-width: 650px; margin: 30px auto; padding: 25px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }\n"
                            + "        .email-header { text-align: center; padding-bottom: 20px; border-bottom: 2px solid #007bff; }\n"
                            + "        .email-header h1 { color: #007bff; margin: 0; font-size: 28px; }\n"
                            + "        .email-body { padding: 20px; }\n"
                            + "        .email-body p { color: #333333; margin: 0 0 15px; line-height: 1.5; }\n"
                            + "        .otp-code { margin: 20px 0; padding: 15px; border: 1px solid #007bff; border-radius: 5px; background-color: #f8f9fa; text-align: center; }\n"
                            + "        .otp-code h2 { color: #007bff; margin: 0; font-size: 32px; }\n"
                            + "        .email-footer { text-align: center; padding: 15px; font-size: 13px; color: #6c757d; border-top: 1px solid #e9ecef; }\n"
                            + "    </style>\n"
                            + "</head>\n"
                            + "<body>\n"
                            + "    <div class=\"email-container\">\n"
                            + "        <div class=\"email-header\">\n"
                            + "            <h1>Verify Your Account</h1>\n"
                            + "        </div>\n"
                            + "        <div class=\"email-body\">\n"
                            + "            <p>Hello " + newUser.getFirst_name() + " " + newUser.getLast_name() + ",</p>\n"
                            + "            <p>Thank you for registering with us! To complete your registration, please enter the following OTP code:</p>\n"
                            + "            <div class=\"otp-code\">\n"
                            + "                <h2>" + newUser.getOtp() + "</h2>\n"
                            + "            </div>\n"
                            + "            <p>If you did not request this, please ignore this email.</p>\n"
                            + "            <p>Best regards,<br>Your Company Team</p>\n"
                            + "        </div>\n"
                            + "        <div class=\"email-footer\">\n"
                            + "            <p>&copy; " + new SimpleDateFormat("yyyy").format(new Date()) + " Your Company. All rights reserved.</p>\n"
                            + "        </div>\n"
                            + "    </div>\n"
                            + "</body>\n"
                            + "</html>");

                });
                emailThread.start();

                request.getSession().setAttribute("user", newUser);
                request.getSession().setAttribute("email", newUser.getEmail());

                responseDTO.setStatus(true);
                responseDTO.setContent("Registration successful.");
            } else {
                responseDTO.setContent("An account with this email already exists.");
            }

            session.close();
        }

        // Send the response as JSON
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseDTO));
    }

}
