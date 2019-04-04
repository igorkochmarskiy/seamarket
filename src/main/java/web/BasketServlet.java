package web;

import model.Item;
import web.service.io.CatalogueService;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static web.WebConstants.STATIC_FILES_DIRECTORY;

@WebServlet(name = "basket", urlPatterns = "/shop/basket")
public class BasketServlet extends HttpServlet {
    @Inject
    private CatalogueService catalogue;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> articles = new ArrayList<>(Arrays.asList(request.getParameter("items").split(",")));
        Map<String, Item> catalogueItems = catalogue.getItems().stream().distinct()
                .collect(Collectors.toMap(Item::getArticle, item -> item));
            List<Item> items = articles.stream().map(article -> {
            Item item = catalogueItems.get(article);
            if (!catalogueItems.containsKey(article)) {
                return new Item("", article, -1);
            }
            return item;

        }).collect(Collectors.toList());
        request.setAttribute("items", items);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(STATIC_FILES_DIRECTORY + "/basket.jsp");
        requestDispatcher.forward(request, response);
    }
}
