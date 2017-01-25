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
    private final int MAX_ARTIST_LENGTH=255;
    private final int MAX_CASH_LENGTH = 4;
    private final int MAX_COMMENT_LENGTH = 65_535;
    private final int MAX_GENRE_LENGTH = 45;
    private final int MAX_PRICE_LENGTH = 3;
    private final int MAX_TRACK_NAME_LENGTH=100;
    private final int MAX_PASS_LENGTH = 10;
    private final int MIN_PASS_LENGTH = 6;
    private final String REGEX_LOGIN = "(\\w){6,10}";
    private final String REGEX_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";
    private final String REGEX_CARD = "[0-9]{13,18}";
    private final String SIGNUP_SUCCESS = "success";
    private final int ZERO = 0;

    boolean isBankCardValid(String cardNumber) {
        Pattern pattern = Pattern.compile(REGEX_CARD);
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    boolean isCashValid(String cash) {
        return  !(cash.length()== ZERO && cash.length() > MAX_CASH_LENGTH) && canConvertToUnsignedDouble(cash);
    }

    boolean isCommentValid(String comment) {
        return (comment.length() > ZERO && comment.length() < MAX_COMMENT_LENGTH);
    }

    String isDataValid(String login, String password, String confirmPass, String email, String cardNumber) throws LogicException {
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

    boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

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

    boolean isLoginValid(String login) {
        Pattern pattern = Pattern.compile(REGEX_LOGIN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    boolean isPasswordValid(String password) {
        return (password.length() >= MIN_PASS_LENGTH && password.length() <= MAX_PASS_LENGTH);
    }

    boolean isTrackValid(String name, String artist, String price, String genre) throws LogicException {
        return  (isTrackNameValid(name) && isTrackArtistValid(artist) && isGenreValid(genre) && isPriceValid(price));
    }

    boolean isTrackNameValid(String name) {
        return name.length() > ZERO && name.length() < MAX_TRACK_NAME_LENGTH;
    }

    boolean isTrackArtistValid(String artist) {
        return artist.length() > ZERO && artist.length() < MAX_ARTIST_LENGTH;
    }

    boolean isGenreValid(String genre) {
        return genre.length() > ZERO && genre.length() < MAX_GENRE_LENGTH;
    }

    boolean isPriceValid(String price) {
        return  !(price.length() == ZERO && price.length() > MAX_PRICE_LENGTH) && canConvertToUnsignedDouble(price);
    }

    boolean validateConfirmPass(String confirmPass, String pass) {
        return pass.equals(confirmPass);
    }


    private boolean canConvertToUnsignedDouble(String value){
        try {
            Double doublePrice = Double.valueOf(value);
            return (doublePrice > ZERO);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}