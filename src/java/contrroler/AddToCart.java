package contrroler;

import com.google.gson.Gson;
import dto.Cart_DTO;
import dto.Response_DTO;
import entity.CartEntity;
import entity.ProductEntity;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;

@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();
        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Cart_DTO item = gson.fromJson(request.getReader(), Cart_DTO.class);

            ProductEntity product = (ProductEntity) session.get(ProductEntity.class, item.getPid());

            System.out.println(product.getTitile());

            if (product == null) {
                response_DTO.setContent("Product not found");

            } else if (item.getQty() <= 0) {
                response_DTO.setContent("Invalid quantity!");
            } else if (item.getQty() > product.getQty()) {
                response_DTO.setContent("Not enough stock!");
            } else {

                HttpSession httpSession = request.getSession();
                UserEntity user = (UserEntity) httpSession.getAttribute("user");

                if (user != null) {

                    Criteria criteria = session.createCriteria(CartEntity.class)
                            .add(Restrictions.eq("user", user))
                            .add(Restrictions.eq("product", product));

                    CartEntity cartEntity = (CartEntity) criteria.uniqueResult();

                    if (cartEntity != null) {

                        cartEntity.setQty(cartEntity.getQty() + item.getQty());
                        session.update(cartEntity);
                        response_DTO.setContent("Quantity updated in cart");
                    } else {

                        CartEntity newCart = new CartEntity();
                        newCart.setQty(item.getQty());
                        newCart.setProduct(product);
                        newCart.setUser(user);
                        session.save(newCart);
                        response_DTO.setContent("Product added to cart");
                    }

                    transaction.commit();
                    response_DTO.setStatus(true);

                } else {

                    ArrayList<CartEntity> sessionCart = (ArrayList<CartEntity>) httpSession.getAttribute("sessionCart");

                    if (sessionCart == null) {
                        sessionCart = new ArrayList<>();
                    }

                    boolean itemExists = false;
                    for (CartEntity cartItem : sessionCart) {
                        if (cartItem.getProduct().getId() == item.getPid()) {
                            cartItem.setQty(cartItem.getQty() + item.getQty());
                            response_DTO.setContent("Quantity updated in session cart");
                            itemExists = true;
                            break;
                        }
                    }

                    if (!itemExists) {
                        CartEntity newCartItem = new CartEntity();
                        newCartItem.setQty(item.getQty());
                        newCartItem.setProduct(product);
                        sessionCart.add(newCartItem);
                        response_DTO.setContent("Product added to session cart");
                    }

                    httpSession.setAttribute("sessionCart", sessionCart);
                    response_DTO.setStatus(true);

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
