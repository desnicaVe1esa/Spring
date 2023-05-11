import cartPackage.Cart;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class PracticeServlet extends HttpServlet implements Filter {

    private String encoding;

    // Для поддержки русского языка
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        if (encoding == null) encoding = "UTF-8";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        // Соблюдает указанную клиентом кодировку символов
        if (null == request.getCharacterEncoding()) {
            request.setCharacterEncoding(encoding);
        }

        // Устанавливает тип содержимого ответа по умолчанию и кодировку
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        next.doFilter(request, response);
    }

    public void destroy() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        Integer count = (Integer) session.getAttribute("count");

        Cart cart = (Cart) session.getAttribute("cart");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String user = (String) session.getAttribute("current_user");

        if (count == null) {
            session.setAttribute("count",1);
        } else {
            session.setAttribute("count", count + 1);
        }

        if (cart == null) {
            cart = new Cart();

            cart.setName(name);
            cart.setQuantity(quantity);
        }

        if (user == null) {
            // response для анонимного пользователя
            // авторизация
            // регистрация
            // session.getAttribute("current_user", ID)
        } else {
            // response для авторизированного пользователя
        }

        session.setAttribute("cart", cart);

//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");

        try {
            PrintWriter printWriter = response.getWriter();

            printWriter.println("<html>");
//            printWriter.println("<h1> Братишка, " + name + " " + surname + "</h1>");
            printWriter.println("<h1> Чисти говно, " + count + " раз" + "</h1>");
            printWriter.println("<html>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getServletContext().getRequestDispatcher("/showCart.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
