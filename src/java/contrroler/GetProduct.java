package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.ProductEntity;
import entity.UserEntity;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "GetProduct", urlPatterns = {"/GetProduct"})
public class GetProduct extends HttpServlet {

    private final Gson gson = new Gson();

    Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonObject obj = gson.fromJson(req.getReader(), JsonObject.class);

        Criteria criteria = session.createCriteria(ProductEntity.class);

        if (req.getSession().getAttribute("user") != null) {

            criteria.add(Restrictions.not(Restrictions.eq("user", (UserEntity) req.getSession().getAttribute("user"))));

        }
        
        if (obj.has("")) {
            
        }
        
        if (obj.has("")) {
            
        }
        
        if (obj.has("")) {
            
        }
        
        if (obj.has("")) {
            
        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(criteria.list()));

    }

}
