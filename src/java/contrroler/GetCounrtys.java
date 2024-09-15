package contrroler;

import com.google.gson.Gson;
import entity.ContryEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Session;

@WebServlet(name = "GetCounrtys", urlPatterns = {"/GetCounrtys"})
public class GetCounrtys extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<ContryEntity> countryList = ( List<ContryEntity>)session.createCriteria(ContryEntity.class).list();

        Gson gson = new Gson();

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(countryList));
        session.close();
    }
}
