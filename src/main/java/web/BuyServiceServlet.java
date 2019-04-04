package web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import web.service.io.order.OrderService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(name = "buyService", urlPatterns = "/buyService")
public class BuyServiceServlet extends HttpServlet {
    @Inject
    private OrderService orderService;
    private Gson gson = new Gson();

    private List<String> retrieveArticlesAsJson(HttpServletRequest request) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String json = "";
            if (br != null) {
                json = br.readLine();
            }
            List<String> articles = gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
            return articles;
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        try {
            List<String> articles = retrieveArticlesAsJson(request);
            if (articles.size()>0 && orderService.storeItemsIfPresent(articles)) {
                response.setStatus((201));
            } else {
                response.setStatus((401));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus((500));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
