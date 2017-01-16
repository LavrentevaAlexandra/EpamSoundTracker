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

public class Validator implements Messenger{
    private final String REGEX_LOGIN = "(\\w){6,10}";
    private final String REGEX_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";
    private final String REGEX_CARD = "[0-9]{13,18}";
    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 10;
    private final String SIGNUP_SUCCESS = "Success";



    public boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile(REGEX_LOGIN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return (password.length() >= MIN_LENGTH && password.length() <= MAX_LENGTH);
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateCard(String cardNumber) {
        Pattern pattern = Pattern.compile(REGEX_CARD);
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    public boolean validateConfirmPass(String confirmPass,String pass) {
        return pass.equals(confirmPass);
    }

    public boolean isLoginUnique(String login) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            return (userDAO.findUser(login) == null);
        } catch (DAOException e) {
            throw new LogicException("Error during checking login uniqueness",e);
        } finally {
            userDAO.closeConnection(connection);
        }
    }
    public boolean isEmailUnique(String email) throws LogicException {
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



    public String isDataValid(String login, String password, String confirmPass,String email, String cardNumber) throws LogicException {
        if (validateLogin(login)&& validatePassword(password) && validateEmail(email) && validateCard(cardNumber)
                    && validateConfirmPass(confirmPass,password)){
            if (!isLoginUnique(login)) {
                return messageManager.getProperty(MessageManager.NOT_UNIQUE_LOGIN);
            }
            if (!isEmailUnique(email)) {
                return messageManager.getProperty(MessageManager.NOT_UNIQUE_EMAIL);
            }
            return SIGNUP_SUCCESS;
        }else{
            return messageManager.getProperty(MessageManager.SIGNUP_ERROR);
        }
    }

/*    public String isEmailChangeValid(String newEmail) throws LogicException {
        String res = "";
        if (!isEmailValid(newEmail)) {
            return EMAIL_MSG;
        }
        if (!isEmailUnique(newEmail)) {
            return EMAIL_UNIQUE_MSG;
        }
        return res;
    }

    public String isLoginChangeValid(String newLogin) throws LogicException {
        String res = "";
        if (!isLoginLengthValid(newLogin)) {
            return LOGIN_MSG;
        }
        if (!isLoginUnique(newLogin)) {
            return LOGIN_UNIQUE_MSG;
        }
        return res;
    }

    public String isCardChangeValid(String newCard) throws LogicException {
        String res = "";
        if (!isCardValid(newCard)) {
            return CARD_MSG;
        }
        if(!newCard.isEmpty()) {
            if (!isCardUnique(newCard)) {
                return CARD_UNIQUE_MSG;
            }
        }
        return res;
    }

    public String isPasswordChangeValid(String oldPass, String newPass, String newPassConf, int id) throws LogicException {
        String res = "";
        if (!isPasswordCorrect(oldPass, id)) {
            return INCORRECT_PASSWORD_MSG;
        }
        if (!isPasswordLengthValid(newPass)) {
            return PASSWORD_MSG;
        }
        if (!isConfirmPasswordValid(newPass, newPassConf)) {
            return CONFIRM_MSG;
        }
        return res;
    }

    public String isMoneyChangeValid(Double money, String card) {
        if(card == null || card.isEmpty()){
            return CARD_IS_EMPTY_MSG;
        }
        String res = "";
        if (String.valueOf(money).length() < 1 && String.valueOf(money).length() > 4) {
            return INCORRECT_MONEY_MSG;
        }
        return res;
    }*/


/*


    public boolean isPasswordCorrect(String password, int id) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Password = DigestUtils.md5Hex(password);
        try {
            if (md5Password.equals(userDAO.findPasswordById(id))) {
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull", e);
            }
        }
    }

    public boolean isCardUnique(String card) throws LogicException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            if (userDAO.findUserByCard(card) == null) {
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Connection can't be returned into pull", e);
            }
        }
    }*/
}
