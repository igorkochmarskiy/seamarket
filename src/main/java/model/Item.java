package model;

import java.util.Objects;

public class Item implements Cloneable {
    private String name;
    private String article;
    private double price;

    public Item(String name, String article, double price) {
        this.article = article;
        this.name = name;
        this.price = price;
    }

    public Item() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(article, item.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    protected Object clone(){
        return new Item(this.name,this.article,this.price);
    }
}
