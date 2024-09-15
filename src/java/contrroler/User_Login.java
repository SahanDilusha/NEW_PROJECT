package contrroler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
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
import entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "User_Login", urlPatterns = {"/User_Login"})
public class User_Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        User_DTO userDTO = gson.fromJson(request.getReader(), User_DTO.class);

        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getPassword());

        Response_DTO responseDTO = new Response_DTO();

        if (userDTO.getEmail().isEmpty()) {
            responseDTO.setContent("Email is required.");
        } else if (!Validations.isEmailValid(userDTO.getEmail())) {
            responseDTO.setContent("Invalid email format.");
        } else if (userDTO.getPassword().isEmpty()) {
            responseDTO.setContent("Password is required.");
        } else if (!Validations.isPasswordValid(userDTO.getPassword())) {
            responseDTO.setContent( "Password must be between 8-45 characters long, and include at least one digit, one uppercase letter, one lowercase letter, and one special character.");
        } else {

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(UserEntity.class);
            criteria.add(Restrictions.eq("email", userDTO.getEmail()));
            criteria.add(Restrictions.eq("password", userDTO.getPassword()));

            if (!criteria.list().isEmpty()) {

                UserEntity newUser = (UserEntity) criteria.uniqueResult();

                if (newUser.getVerify().equals("1")) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Mail.sendMail(newUser.getEmail(), "Verify Your Account", "<!DOCTYPE html>\n"
                                    + "<html lang=\"en\">\n"
                                    + "<head>\n"
                                    + "    <meta charset=\"UTF-8\">\n"
                                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                    + "    <title>Verify Your Account</title>\n"
                                    + "    <style>\n"
                                    + "        body, h1, h2, p { margin: 0; padding: 0; font-family: Arial, sans-serif; }\n"
                                    + "        .email-container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #ffffff; color: #333333; }\n"
                                    + "        .email-header { background-color: #ff6f00; color: #ffffff; padding: 20px; text-align: center; }\n"
                                    + "        .email-header h1 { font-size: 24px; }\n"
                                    + "        .otp-code { margin: 20px 0; padding: 15px; border: 2px solid #ff6f00; border-radius: 5px; text-align: center; background-color: #fff3e0; }\n"
                                    + "        .otp-code h2 { font-size: 32px; color: #ff6f00; }\n"
                                    + "        .email-footer { text-align: center; padding: 10px; font-size: 14px; color: #777777; border-top: 1px solid #ddd; margin-top: 20px; }\n"
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
                                    + "            <p>Best regards,<br>Your Company</p>\n"
                                    + "        </div>\n"
                                    + "        <div class=\"email-footer\">\n"
                                    + "            <p>&copy; " + new SimpleDateFormat("yyyy").format(new Date()) + " Your Company. All rights reserved.</p>\n"
                                    + "        </div>\n"
                                    + "    </div>\n"
                                    + "</body>\n"
                                    + "</html>");
                        }
                    };
                    thread.start();

                   
                    request.getSession().setAttribute("email", newUser.getEmail());
                    responseDTO.setContent("1");
                } else {
                    responseDTO.setStatus(true);
                }

                session.save(newUser);
                session.beginTransaction().commit();
                newUser.setPassword(null);
                request.getSession().setAttribute("user", newUser);

                responseDTO.setStatus(true);

            } else {
                responseDTO.setContent("4");
            }
            session.close();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseDTO));
        System.out.println(gson.toJson(responseDTO));

    }

}
