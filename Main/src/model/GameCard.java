package model;

public class GameCard {

    private String cardId;

    private String name;

    private Integer price;

    private Integer year;

    private String ownerUserId;

    private String boughtUserId;


    public GameCard(String cardId, String name, Integer price, Integer year, String ownerUserId, String boughtUserId) {
        this.cardId = cardId;
        this.name = name;
        this.price = price;
        this.year = year;
        this.ownerUserId = ownerUserId;
        this.boughtUserId = boughtUserId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getBoughtUserId() {
        return boughtUserId;
    }

    public void setBoughtUserId(String boughtUserId) {
        this.boughtUserId = boughtUserId;
    }

    @Override
    public String toString() {
        return "GameCard{" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", ownerUserId='" + ownerUserId + '\'' +
                ", boughtUserId='" + boughtUserId + '\'' +
                '}';
    }
}
