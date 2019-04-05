package web.service.io.order;

import model.Item;
import web.WebConstants;
import web.service.io.CatalogueService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is service that saves orders.
 */
@ApplicationScoped
public class OrderServiceImpl implements OrderService {
    @Inject
    private CatalogueService catalogue;

    /**
     *  Tries to save orders.
     *  Checks if there are products with such article and
     *  if the catalog does not contain any product with one of those, does not save any of them.
     *
     * @param articles the list of items articles
     * @return whether orders are stored
     */
    @Override
    public boolean storeItemsIfPresent(List<String> articles) throws FileNotFoundException {
        Map<String, Item> catalogueItems = catalogue.getItems().stream().distinct()
                .collect(Collectors.toMap(Item::getArticle, item -> item));
        long amountOfNonexistentItems = articles.stream().filter(article -> !catalogueItems.containsKey(article)).count();
        if (amountOfNonexistentItems == 0) {
            createDirectoryIfNonExist();
            try (PrintWriter pw = new PrintWriter(WebConstants.ORDER_DIRECTORY +"/order-"+ Timestamp.from(Instant.now()).getTime()+ ".csv")) {
                articles.forEach(article -> {
                    Item item = catalogueItems.get(article);
                    pw.println(item.getName() + "," + item.getArticle() + "," + item.getPrice());
                });
            }
            return true;
        }
        return false;
    }

    private void createDirectoryIfNonExist() {
        File directory = new File(WebConstants.ORDER_DIRECTORY);
        if (! directory.exists()){
            directory.mkdir();
        }
    }

}
