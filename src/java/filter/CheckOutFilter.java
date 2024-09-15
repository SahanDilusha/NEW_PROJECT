package filter;

import entity.CartEntity;
import entity.UserEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebFilter(urlPatterns = {"/checkout.html"})
public class CheckOutFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute("user") == null) {

            Session session = HibernateUtil.getSessionFactory().openSession();

            if (session.createCriteria(CartEntity.class).add(Restrictions.eq("user", (UserEntity) req.getSession().getAttribute("user"))).list().isEmpty()) {
                resp.sendRedirect("index.html");
                return;
            }

            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("index.html");
        }

    }

    @Override
    public void destroy() {
    }

}
