package contrroler;

import com.google.gson.Gson;
import dto.Cart_DTO;
import dto.Response_DTO;
import entity.CartEntity;
import entity.UserEntity;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "RemoveCartItem", urlPatterns = {"/RemoveCartItem"})
public class RemoveCartItem extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response_DTO response_DTO = new Response_DTO();
        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Cart_DTO item = gson.fromJson(request.getReader(), Cart_DTO.class);

            HttpSession httpSession = request.getSession();
            UserEntity user = (UserEntity) httpSession.getAttribute("user");

            if (user != null) {

                Criteria criteria = session.createCriteria(CartEntity.class)
                        .add(Restrictions.eq("user", user))
                        .add(Restrictions.eq("product.id", item.getPid()));

                CartEntity cartEntity = (CartEntity) criteria.uniqueResult();

                if (cartEntity != null) {
                    session.delete(cartEntity);
                    transaction.commit();
                    response_DTO.setStatus(true);
                    response_DTO.setContent("Item removed from cart");
                } else {
                    response_DTO.setStatus(false);
                    response_DTO.setContent("Item not found in cart");
                }

            } else {

                ArrayList<CartEntity> sessionCart = (ArrayList<CartEntity>) httpSession.getAttribute("sessionCart");

                if (sessionCart != null) {
                    boolean itemRemoved = false;
                    for (CartEntity cartItem : sessionCart) {
                        if (cartItem.getProduct().getId() == item.getPid()) {
                            sessionCart.remove(cartItem);
                            itemRemoved = true;
                            break;
                        }
                    }

                    if (itemRemoved) {
                        response_DTO.setStatus(true);
                        response_DTO.setContent("Item removed from session cart");
                    } else {
                        response_DTO.setStatus(false);
                        response_DTO.setContent("Item not found in session cart");
                    }
                } else {
                    response_DTO.setStatus(false);
                    response_DTO.setContent("Session cart is empty");
                }
            }
            session.close();
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(response_DTO));

        } catch (Exception e) {
            e.printStackTrace();
             response_DTO.setContent("Unable to process your request");
        }
    }

}
