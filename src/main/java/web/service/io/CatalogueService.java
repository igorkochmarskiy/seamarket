package web.service.io;

import model.Item;

import java.util.List;
public interface CatalogueService {
    List<Item> getItems();
    void init();
}
