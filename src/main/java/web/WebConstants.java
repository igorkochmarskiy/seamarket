package web;

public class WebConstants {
    /*
    path specify static content folder, that is invisible outside
     */
    public static final String STATIC_FILES_DIRECTORY = "/ui/static";
    public static final String ORDER_DIRECTORY = System.getProperty("catalina.home") + "/data/order";
    public static final String CATALOGUE_PATH = System.getProperty("catalina.home") + "/data/catalogue.csv";
}
