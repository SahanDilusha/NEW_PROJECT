package contrroler;

import com.google.gson.Gson;
import dto.Cart_DTO;
import dto.Response_DTO;
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

@WebServlet(name = "GetCart", urlPatterns = {"/GetCart"})
public class GetCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response_DTO response_DTO = new Response_DTO();
        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            HttpSession httpSession = request.getSession();
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
                        cartDTO.setTitle(cartEntity.getProduct().getTitile());  // Adjust with actual getter for title
                        cartDTO.setPrice(cartEntity.getProduct().getPrice());   // Adjust with actual getter for price
                        cartDTOs.add(cartDTO);
                    }
                    response_DTO.setContent(cartDTOs);
                    response_DTO.setStatus(true);
                } else {
                    response_DTO.setContent("Cart is empty");
                }

            } else {
       
                List<CartEntity> sessionCart = (List<CartEntity>) httpSession.getAttribute("sessionCart");

                if (sessionCart != null && !sessionCart.isEmpty()) {
                    List<Cart_DTO> cartDTOs = new ArrayList<>();
                    for (CartEntity cartItem : sessionCart) {
                        Cart_DTO cartDTO = new Cart_DTO();
                        cartDTO.setPid(cartItem.getProduct().getId());
                        cartDTO.setQty(cartItem.getQty());
                        cartDTO.setTitle(cartItem.getProduct().getTitile());  // Adjust with actual getter for title
                        cartDTO.setPrice(cartItem.getProduct().getPrice());   // Adjust with actual getter for price
                        cartDTOs.add(cartDTO);
                    }
                    response_DTO.setContent(cartDTOs);
                    response_DTO.setStatus(true);
                } else {
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
