package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.BrandEntity;
import entity.ConditionEntity;
import entity.MainCategoryEntity;
import entity.SubCategoryEntity;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Session;

@WebServlet(name = "FilterData", urlPatterns = {"/FilterData"})
public class FilterData extends HttpServlet {

    private final Gson gson = new Gson();

    JsonObject responseObject = new JsonObject();

    Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            List<BrandEntity> barndList = (List<BrandEntity>) session.createCriteria(BrandEntity.class).list();

            List<MainCategoryEntity> mainCList = (List<MainCategoryEntity>) session.createCriteria(MainCategoryEntity.class).list();

            List<SubCategoryEntity> subCList = (List<SubCategoryEntity>) session.createCriteria(SubCategoryEntity.class).list();

            List<ConditionEntity> conditionCList = (List<ConditionEntity>) session.createCriteria(ConditionEntity.class).list();

            responseObject.add("barnd", gson.toJsonTree(barndList));
            responseObject.add("main", gson.toJsonTree(mainCList));
            responseObject.add("sub", gson.toJsonTree(subCList));
            responseObject.add("condition", gson.toJsonTree(conditionCList));

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseObject));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            session.close();
        }

    }

}
