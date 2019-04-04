package web.service.io;

import model.Item;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static web.WebConstants.CATALOGUE_PATH;

@ApplicationScoped
public class CatalogueServiceImpl implements CatalogueService {
    private byte[] digest = new byte[1];
    private List<Item> items = new ArrayList<>();
    private ScheduledExecutorService scheduledExecutorService;

    public CatalogueServiceImpl() {
    }

    public void init() {

        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(1);
            this.update();
            scheduledExecutorService.scheduleAtFixedRate(this::update, 0, 1, TimeUnit.MINUTES);
        }
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void updateNow() {
        File catalogueFile = new File(CATALOGUE_PATH);
        if (catalogueFile.exists()) {
            try {
                this.items = Files.lines(catalogueFile.toPath())
                        .flatMap(x -> {
                            String[] fields = x.split(",");
                            if (fields.length == 3) {
                                try {
                                    return Stream.of(new Item(fields[0].trim(), fields[1].trim(), Double.parseDouble(fields[2].trim())));
                                } catch (Exception ex) {
                                    return Stream.empty();
                                }
                            } else {
                                return Stream.empty();
                            }
                        }).collect(Collectors.toList());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void update() {

        byte[] newDigest = calculateDigest();
        if (!Arrays.equals(this.digest, newDigest)) {
            this.digest = newDigest;
            updateNow();
        }
    }

    private byte[] calculateDigest() {
        File catalogieFile = new File(CATALOGUE_PATH);
        if (catalogieFile.exists()) {
            try (InputStream fis = new FileInputStream(catalogieFile)) {

                byte[] buffer = new byte[1024];
                MessageDigest complete = MessageDigest.getInstance("MD5");
                int numRead;

                do {
                    numRead = fis.read(buffer);
                    if (numRead > 0) {
                        complete.update(buffer, 0, numRead);
                    }
                } while (numRead != -1);
                return complete.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[2];
    }
}
