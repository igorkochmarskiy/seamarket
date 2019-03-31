package web;

import web.service.io.CatalogueService;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static web.WebConstants.STATIC_FILES_DIRECTORY;

@WebServlet(name = "items", urlPatterns = "/shop/items")

public class ItemsServlet extends HttpServlet {

    @Inject
    private CatalogueService catalogue;

    @Override
    public void init() throws ServletException {
        super.init();
        catalogue.init();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items", catalogue.getItems());
        RequestDispatcher view = request.getRequestDispatcher(STATIC_FILES_DIRECTORY + "/items.jsp");
        view.forward(request, response);
    }
}
