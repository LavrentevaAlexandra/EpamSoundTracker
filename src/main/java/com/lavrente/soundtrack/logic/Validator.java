package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.UserDAO;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.manager.Messenger;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 123 on 02.01.2017.
 */


public class Validator implements Messenger {
    /** The max artist length. */
    private final int MAX_ARTIST_LENGTH=255;

    /** The max bonus value. */
    private final int MAX_BONUS=100;

    /** The max cash length. */
    private final int MAX_CASH_LENGTH = 5;

    /** The max comment length. */
    private final int MAX_COMMENT_LENGTH = 65_535;

    /** The max genre length. */
    private final int MAX_GENRE_LENGTH = 45;

    /** The max price length. */
    private final int MAX_PRICE_LENGTH = 4;

    /** The max track name length. */
    private final int MAX_TRACK_NAME_LENGTH=100;

    /** The max pass length. */
    private final int MAX_PASS_LENGTH = 10;

    /** The min pass length. */
    private final int MIN_PASS_LENGTH = 6;

    /** The regex login. */
    private final String REGEX_LOGIN = "(\\w){6,10}";

    /** The regex email. */
    private final String REGEX_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";

    /** The regex card. */
    private final String REGEX_CARD = "[0-9]{13,18}";

    /** The signup success. */
    private final String SIGNUP_SUCCESS = "success";

    /** The zero. */
    private final int ZERO = 0;

    /**
     * Checks if is bank card valid.
     *
     * @param cardNumber the card number
     * @return true, if is bank card valid
     */
    public boolean isBankCardValid(String cardNumber) {
        Pattern pattern = Pattern.compile(REGEX_CARD);
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    /**
     * Checks if is bonus valid.
     *
     * @param bonus the bonus
     * @return true, if is bonus valid
     */
    public boolean isBonusValid(String bonus){
        return  (bonus.length() != ZERO && bonus.length() <= MAX_PRICE_LENGTH) && canConvertToUnsignedInt(bonus);
    }

    /**
     * Checks if is cash valid.
     *
     * @param cash the cash
     * @return true, if is cash valid
     */
    public boolean isCashValid(String cash) {
        return  !(cash.length()== ZERO && cash.length() > MAX_CASH_LENGTH) && canConvertToUnsignedDouble(cash);
    }

    /**
     * Checks if is comment valid.
     *
     * @param comment the comment
     * @return true, if is comment valid
     */
    public boolean isCommentValid(String comment) {
        return (comment.length() > ZERO && comment.length() < MAX_COMMENT_LENGTH);
    }

    /**
     * Checks if is data valid.
     *
     * @param login the login
     * @param password the password
     * @param confirmPass the confirm pass
     * @param email the email
     * @param cardNumber the card number
     * @return the string
     * @throws LogicException the logic exception
     */
    public String isDataValid(String login, String password, String confirmPass, String email, String cardNumber) throws LogicException {
        if (isLoginValid(login) && isPasswordValid(password) && isEmailValid(email) && isBankCardValid(cardNumber)
                && validateConfirmPass(confirmPass, password)) {
            if (!isLoginUnique(login)) {
                return messageManager.getProperty(MessageManager.NOT_UNIQUE_LOGIN);
            }
            if (!isEmailUnique(email)) {
                return messageManager.getProperty(MessageManager.NOT_UNIQUE_EMAIL);
            }
            return SIGNUP_SUCCESS;
        } else {
            return messageManager.getProperty(MessageManager.SIGNUP_ERROR);
        }
    }

    /**
     * Checks if is email unique.
     *
     * @param email the email
     * @return true, if is email unique
     * @throws LogicException the logic exception
     */
    boolean isEmailUnique(String email) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return (userDAO.findUserByEmail(email) == null);
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Checks if is email valid.
     *
     * @param email the email
     * @return true, if is email valid
     */
    boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks if is login unique.
     *
     * @param login the login
     * @return true, if is login unique
     * @throws LogicException the logic exception
     */
    boolean isLoginUnique(String login) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return (userDAO.findUser(login) == null);
        } catch (DAOException e) {
            throw new LogicException("Error during checking login uniqueness", e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }

    /**
     * Checks if is login valid.
     *
     * @param login the login
     * @return true, if is login valid
     */
    boolean isLoginValid(String login) {
        Pattern pattern = Pattern.compile(REGEX_LOGIN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    /**
     * Checks if is password valid.
     *
     * @param password the password
     * @return true, if is password valid
     */
    boolean isPasswordValid(String password) {
        return (password.length() >= MIN_PASS_LENGTH && password.length() <= MAX_PASS_LENGTH);
    }

    /**
     * Checks if is track valid.
     *
     * @param name the name
     * @param artist the artist
     * @param price the price
     * @param genre the genre
     * @return true, if is track valid
     * @throws LogicException the logic exception
     */
    public boolean isTrackValid(String name, String artist, String price, String genre) throws LogicException {
        return  (isTrackNameValid(name) && isTrackArtistValid(artist) && isGenreValid(genre) && isPriceValid(price));
    }

    /**
     * Checks if is track name valid.
     *
     * @param name the name
     * @return true, if is track name valid
     */
    boolean isTrackNameValid(String name) {
        return name.length() > ZERO && name.length() < MAX_TRACK_NAME_LENGTH;
    }

    /**
     * Checks if is track artist valid.
     *
     * @param artist the artist
     * @return true, if is track artist valid
     */
    boolean isTrackArtistValid(String artist) {
        return artist.length() > ZERO && artist.length() < MAX_ARTIST_LENGTH;
    }

    /**
     * Checks if is genre valid.
     *
     * @param genre the genre
     * @return true, if is genre valid
     */
    boolean isGenreValid(String genre) {
        return genre.length() > ZERO && genre.length() < MAX_GENRE_LENGTH;
    }

    /**
     * Checks if is price valid.
     *
     * @param price the price
     * @return true, if is price valid
     */
    boolean isPriceValid(String price) {
        int len=price.length();
        return  (price.length() != ZERO && price.length() <= MAX_PRICE_LENGTH) && canConvertToUnsignedDouble(price);
    }

    /**
     * Validate confirm pass.
     *
     * @param confirmPass the confirm pass
     * @param pass the pass
     * @return true, if successful
     */
    boolean validateConfirmPass(String confirmPass, String pass) {
        return pass.equals(confirmPass);
    }

    /**
     * Can convert to unsigned double.
     *
     * @param value the value
     * @return true, if successful
     */
    private boolean canConvertToUnsignedDouble(String value){
        try {
            Double doubleValue = Double.valueOf(value);
            return (doubleValue > ZERO);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Can convert to unsigned int.
     *
     * @param value the value
     * @return true, if successful
     */
    private boolean canConvertToUnsignedInt(String value){
        try {
            Integer intValue = Integer.valueOf(value);
            return (intValue >= ZERO && intValue<=MAX_BONUS);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}