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
    private final String REGEX_LOGIN = "(\\w){6,10}";
    private final String REGEX_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";
    private final String REGEX_CARD = "[0-9]{13,18}";
    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 10;
    private final String SIGNUP_SUCCESS = "success";


    public boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile(REGEX_LOGIN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return (password.length() >= MIN_LENGTH && password.length() <= MAX_LENGTH);
    }

    boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    boolean validateCard(String cardNumber) {
        Pattern pattern = Pattern.compile(REGEX_CARD);
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    boolean validateConfirmPass(String confirmPass, String pass) {
        return pass.equals(confirmPass);
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

    String isDataValid(String login, String password, String confirmPass, String email, String cardNumber) throws LogicException {
        if (validateLogin(login) && validatePassword(password) && validateEmail(email) && validateCard(cardNumber)
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

    boolean isCashValid(String cash) {
        if (cash.length() < 1 && cash.length() > 4) {
            return false;
        }
        try {
            Double doubleCash = Double.valueOf(cash);
            if (doubleCash < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    boolean isCommentValid(String comment) {
        return (comment.length() > 2 && comment.length() < 65_535);
    }
}