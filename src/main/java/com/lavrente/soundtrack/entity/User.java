package com.lavrente.soundtrack.entity;

/**
 * Created by 123 on 02.01.2017.
 */
public class User extends Entity{
    private int id;
    private String login;
    private String password;
    private int role;
    private double cash;
    private int discount;
    private String cardNumber;
    private String email;
    private int orderCount;

    /**
     * Instantiates a new user.
     *
     * @param id the id
     * @param login the login
     * @param password the password
     * @param cash the cash
     * @param role the role
     * @param discount the discount
     * @param cardNumber the card number
     * @param email the email
     */
    public User(int id, String login, String password, double cash, int role, int discount, String cardNumber, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.cash = cash;
        this.discount = discount;
        this.cardNumber = cardNumber;
        this.email = email;
    }

    public User(int id, String login, int discount, int orderCount) {
        this.id = id;
        this.login = login;
        this.discount = discount;
        this.orderCount= orderCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
