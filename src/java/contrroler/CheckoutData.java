package contrroler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Cart_DTO;
import dto.Response_DTO;
import entity.AddressEntity;
import entity.CartEntity;
import entity.UserEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "CheckoutData", urlPatterns = {"/CheckoutData"})
public class CheckoutData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonObject response_DTO = new JsonObject();
        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();

        HttpSession httpSession = req.getSession();
        UserEntity user = (UserEntity) httpSession.getAttribute("user");

        if (user != null) {

            Criteria criteria = session.createCriteria(CartEntity.class)
                    .add(Restrictions.eq("user", user));

            List<CartEntity> cartList = criteria.list();

            if (cartList != null && !cartList.isEmpty()) {

                List<Cart_DTO> cartDTOs = new ArrayList<>();
                for (CartEntity cartEntity : cartList) {
                    Cart_DTO cartDTO = new Cart_DTO();
                    cartDTO.setPid(cartEntity.getProduct().getId());
                    cartDTO.setQty(cartEntity.getQty());
                    cartDTO.setShipping(cartEntity.getProduct().getShipping());
                    cartDTO.setTitle(cartEntity.getProduct().getTitile());
                    cartDTO.setPrice(cartEntity.getProduct().getPrice());
                    cartDTOs.add(cartDTO);
                }
                response_DTO.add("cart", gson.toJsonTree(cartDTOs));
                response_DTO.addProperty("status", true);

                AddressEntity address = (AddressEntity) session.createCriteria(AddressEntity.class)
                        .add(Restrictions.eq("user_id", user))
                        .add(Restrictions.eq("status", 1)).uniqueResult();

                if (address != null) {
                    response_DTO.add("address", gson.toJsonTree(address));
                    response_DTO.addProperty("address_status", true);
                } else {
                    response_DTO.addProperty("address_status", false);
                }

            } else {
                response_DTO.addProperty("content", "Cart is empty");
            }

        } else {
            response_DTO.addProperty("content", "Session is empty");
        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response_DTO));

    }

}
