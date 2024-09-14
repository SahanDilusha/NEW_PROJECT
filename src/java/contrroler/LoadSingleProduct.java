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
import dto.ProductDTO;
import entity.ProductEntity;
import entity.ConditionEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "LoadSingleProduct", urlPatterns = {"/LoadSingleProduct"})
public class LoadSingleProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = null;

        try {

            Gson gson = new Gson();

            session = HibernateUtil.getSessionFactory().openSession();

            ProductEntity product = (ProductEntity) session.get(ProductEntity.class, (int) gson.fromJson(req.getReader(), Integer.class));

//            ProductEntity product = (ProductEntity) session.get(ProductEntity.class, 10);
            JsonObject object = new JsonObject();
            object.add("product", gson.toJsonTree(new ProductDTO(
                    product.getId(),
                    product.getTitile(),
                    product.getDescription(),
                    product.getQty(),
                    product.getBarnd().getName(),
                    product.getMainCategory().getName(),
                    product.getSubCategory().getName(),
                    product.getCondition_id().getName(),
                    product.getShipping(),
                    product.getPrice(),
                    product.getProductSatus().getId(),
                    product.getProductSatus().getName(),
                    product.getDate(),
                    product.getRateit() >= 100 ? 0.5 : product.getRateit() >= 500 ? 3 : product.getRateit() >= 1000 ? 4 : 5)));

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(object));
            System.out.println(gson.toJson(object));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null || session.isOpen()) {
                session.close();
            }
        }
    }

}
