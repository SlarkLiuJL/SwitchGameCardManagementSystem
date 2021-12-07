package model;

import java.util.Date;

public class GoodInfo {

    private String cardId;

    private String name;

    private Integer price;

    private Integer year;

    private String ownerUserId;

    private String boughtUserId;

    private Date pushdate;

    private Date boughtdate;

    private Integer isdelete;

    public GoodInfo(String cardId, String name, Integer price, Integer year, String ownerUserId, String boughtUserId, Date pushdate, Date boughtdate) {
        this.cardId = cardId;
        this.name = name;
        this.price = price;
        this.year = year;
        this.ownerUserId = ownerUserId;
        this.boughtUserId = boughtUserId;
        this.pushdate = pushdate;
        this.boughtdate = boughtdate;
    }

    public GoodInfo(String cardId, String name, Integer price, Integer year, String ownerUserId, String boughtUserId, Date pushdate, Date boughtdate, Integer isdelete) {
        this.cardId = cardId;
        this.name = name;
        this.price = price;
        this.year = year;
        this.ownerUserId = ownerUserId;
        this.boughtUserId = boughtUserId;
        this.pushdate = pushdate;
        this.boughtdate = boughtdate;
        this.isdelete = isdelete;
    }

    public Integer getIsdelete() {
        return isdelete;
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

    public Date getPushdate() {
        return pushdate;
    }

    public void setPushdate(Date pushdate) {
        this.pushdate = pushdate;
    }

    public Date getBoughtdate() {
        return boughtdate;
    }

    public void setBoughtdate(Date boughtdate) {
        this.boughtdate = boughtdate;
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
                ", pushdate=" + pushdate +
                ", boughtdate=" + boughtdate +
                '}';
    }
}
