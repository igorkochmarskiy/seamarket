package web.service.io.order;

import java.io.FileNotFoundException;
import java.util.List;

public interface OrderService {
    boolean storeItemsIfPresent(List<String> articles) throws FileNotFoundException;
}
