package web.service.io;

import model.Item;

import javax.ejb.Local;
import java.util.List;
@Local
public interface CatalogueService {
    List<Item> getItems();
    void init();
}
