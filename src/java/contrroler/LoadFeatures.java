package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import entity.BrandEntity;
import entity.ConditionEntity;
import entity.MainCategoryEntity;
import entity.SubCategoryEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "LoadFeatures", urlPatterns = {"/LoadFeatures"})
public class LoadFeatures extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();
            JsonObject object = new JsonObject();

            ArrayList<MainCategoryEntity> mainCategory = (ArrayList<MainCategoryEntity>) session.createCriteria(MainCategoryEntity.class).list();
            ArrayList<ConditionEntity> conditionEntity = (ArrayList<ConditionEntity>) session.createCriteria(ConditionEntity.class).list();
            ArrayList<BrandEntity> brand = (ArrayList<BrandEntity>) session.createCriteria(BrandEntity.class).list();

            ArrayList<SubCategoryEntity> subCategories = new ArrayList<>();
           
            for (MainCategoryEntity categoryEntity : mainCategory) {
                subCategories.addAll((ArrayList<SubCategoryEntity>) session.createCriteria(SubCategoryEntity.class)
                        .add(Restrictions.eq("mainCategory", categoryEntity))
                        .list());
            }

            object.add("main_category", gson.toJsonTree(mainCategory));
            object.add("condition", gson.toJsonTree(conditionEntity));
            object.add("sub_category", gson.toJsonTree(subCategories));
            object.add("barnd", gson.toJsonTree(brand));

            session.close();

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(object));
            System.out.println(gson.toJson(object));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
