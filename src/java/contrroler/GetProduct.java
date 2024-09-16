package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.BrandEntity;
import entity.MainCategoryEntity;
import entity.ProductEntity;
import entity.SubCategoryEntity;
import entity.UserEntity;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "GetProduct", urlPatterns = {"/GetProduct"})
public class GetProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();

        JsonObject responseJsonObject = new JsonObject();

        JsonObject obj = gson.fromJson(req.getReader(), JsonObject.class);

        Criteria criteria = session.createCriteria(ProductEntity.class);

        if (req.getSession().getAttribute("user") != null) {

            criteria.add(Restrictions.not(Restrictions.eq("user", (UserEntity) req.getSession().getAttribute("user"))));

        }

        if (obj.has("category")) {

            if (!obj.get("category").toString().equals("0")) {

                criteria.add(Restrictions.eq("mainCategory", new MainCategoryEntity(Integer.parseInt(obj.get("category").toString()))));

            }

        }

        if (obj.has("subcategory")) {
            if (!obj.get("subcategory").toString().equals("0")) {
                criteria.add(Restrictions.eq("subCategory", new SubCategoryEntity(Integer.parseInt(obj.get("subcategory").toString()))));
            }
        }

        if (obj.has("brand")) {
            if (!obj.get("brand").toString().equals("0")) {
                criteria.add(Restrictions.eq("barnd", new BrandEntity(Integer.parseInt(obj.get("brand").toString()))));
            }
        }

        double startPrice = obj.get("price_range_start").getAsDouble();
        double endPrice = obj.get("price_range_end").getAsDouble();

        criteria.add(Restrictions.ge("price", startPrice));
        criteria.add(Restrictions.le("price", endPrice));
        
        String sortText = obj.get("sort_text").getAsString();

        
        
        if (sortText.equals("1")) {
            criteria.addOrder(Order.desc("id"));

        } else if (sortText.equals("2")) {
            criteria.addOrder(Order.asc("id"));

        } else if (sortText.equals("3")) {
            criteria.addOrder(Order.asc("titile"));

        } else if (sortText.equals("4")) {
            criteria.addOrder(Order.asc("price"));

        }

        //get all product count
        responseJsonObject.addProperty("allProductCount", criteria.list().size());

//        set product range
        int firstResult = obj.get("firstResult").getAsInt();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(2);

        List<ProductEntity> productlist = (List<ProductEntity>) criteria.list();

        for (ProductEntity product : productlist) {
            product.setUser(null);
        }

        responseJsonObject.add("productlist", gson.toJsonTree(productlist));

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseJsonObject));

    }

}
