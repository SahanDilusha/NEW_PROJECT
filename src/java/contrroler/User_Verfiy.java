package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import dto.Response_DTO;
import entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "User_Verfiy", urlPatterns = {"/User_Verfiy"})
public class User_Verfiy extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO responseDTO = new Response_DTO();

        Gson gson = new Gson();

        JsonObject dto = gson.fromJson(request.getReader(), JsonObject.class);

        String code = dto.get("code").getAsString();

        if (code.isEmpty()) {
            responseDTO.setContent("Enter your verification code!");
        } else {

            if (request.getSession().getAttribute("email") != null) {
                String email = request.getSession().getAttribute("email").toString();
                Session session = HibernateUtil.getSessionFactory().openSession();

                Criteria criteria1 = session.createCriteria(UserEntity.class);
                criteria1.add(Restrictions.eq("email", email));
                criteria1.add(Restrictions.eq("otp", code));

                if (!criteria1.list().isEmpty()) {
                    UserEntity user = (UserEntity) criteria1.list().get(0);

                    user.setVerify("2");
                    session.update(user);
                    session.beginTransaction().commit();
                    request.getSession().removeAttribute("email");
                    responseDTO.setStatus(true);
                    request.getSession().setAttribute("user", user);

                } else {
                    responseDTO.setContent("Invalid verification code!");
                }

            } else {
                responseDTO.setContent("Verification unavailbe! Please Sign In!");
            }

        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseDTO));

    }

}
