package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.UserDAO;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.manager.Messenger;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by 123 on 04.01.2017.
 */
@SuppressWarnings("Duplicates")
public class UserLogic implements Messenger {
    private final String SUCCESS = "success";

    public User findUser(String login) throws LogicException {
        User user;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            user = userDAO.findUser(login);
        } catch (DAOException e) {
            throw new LogicException("Error during user search", e);
        }
        return user;
    }

    public User findUserById(int id) throws LogicException {
        User user;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        try {
            user = userDAO.findUserById(id);
        } catch (DAOException e) {
            throw new LogicException("Error during user search", e);
        }
        return user;
    }

    public String singUp(String login, String password, String confirmPassword, String email, String cardNumber) throws LogicException {
        Validator validator = new Validator();
        String res = validator.isDataValid(login, password, confirmPassword, email, cardNumber);
        if (SUCCESS.equals(res)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            String md5Pass = DigestUtils.md5Hex(password);
            try {
                userDAO.addUser(login, md5Pass, cardNumber, email);
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Error during signup", e);
            } finally {
                userDAO.closeConnection(connection);
            }
        } else {
            return res;
        }
    }

    public String changeLogin(int userId, String prevLogin, String newLogin) throws LogicException {
        Validator validator = new Validator();
        if (!newLogin.equals(prevLogin)) {
            if (validator.validateLogin(newLogin)) {
                if (validator.isLoginUnique(newLogin)) {
                    ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                    UserDAO userDAO = new UserDAO(connection);
                    try {
                        userDAO.changeLogin(userId, newLogin);
                        return SUCCESS;
                    } catch (DAOException e) {
                        throw new LogicException("Error during changing login", e);
                    } finally {
                        userDAO.closeConnection(connection);
                    }
                } else {
                    return messageManager.getProperty(MessageManager.NOT_UNIQUE_LOGIN);
                }
            } else {
                return messageManager.getProperty(MessageManager.CHANGE_LOGIN_ERROR);
            }
        } else {
            return SUCCESS;
        }
    }

    public String changeEmail(int userId, String prevEmail, String newEmail) throws LogicException {
        Validator validator = new Validator();
        if (!newEmail.equals(prevEmail)) {
            if (validator.validateEmail(newEmail)) {
                if (validator.isEmailUnique(newEmail)) {
                    ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                    UserDAO userDAO = new UserDAO(connection);
                    try {
                        userDAO.changeEmail(userId, newEmail);
                        return SUCCESS;
                    } catch (DAOException e) {
                        throw new LogicException("Error during changing email", e);
                    } finally {
                        userDAO.closeConnection(connection);
                    }
                } else {
                    return messageManager.getProperty(MessageManager.NOT_UNIQUE_EMAIL);
                }
            } else {
                return messageManager.getProperty(MessageManager.CHANGE_EMAIL_ERROR);
            }
        } else {
            return SUCCESS;
        }
    }

    public String changeCardNumber(int userId, String prevCardNumber, String newCardNumber) throws LogicException {
        Validator validator = new Validator();
        if (!newCardNumber.equals(prevCardNumber)) {
            if (validator.validateCard(newCardNumber)) {
                ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                UserDAO userDAO = new UserDAO(connection);
                try {
                    userDAO.changeCardNumber(userId, newCardNumber);
                    return SUCCESS;
                } catch (DAOException e) {
                    throw new LogicException("Error during changing card number", e);
                } finally {
                    userDAO.closeConnection(connection);
                }

            } else {
                return messageManager.getProperty(MessageManager.CHANGE_CARD_ERROR);
            }
        } else {
            return SUCCESS;
        }
    }

    public String changePass(int userId, String userPass, String password, String newPassword, String confPassword) throws LogicException {
        Validator validator = new Validator();
        String md5Pass = DigestUtils.md5Hex(password);
        if (userPass.equals(md5Pass)) {
            if (validator.validatePassword(newPassword)) {
                if (validator.validateConfirmPass(confPassword, newPassword)) {
                    ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                    UserDAO userDAO = new UserDAO(connection);
                    String md5NewPass = DigestUtils.md5Hex(newPassword);
                    try {
                        userDAO.changePassword(userId, md5NewPass);
                        return SUCCESS;
                    } catch (DAOException e) {
                        throw new LogicException("Error during changing password", e);
                    } finally {
                        userDAO.closeConnection(connection);
                    }
                } else {
                    return messageManager.getProperty(MessageManager.CHANGE_PASS_EQUAL_NEW_ERROR);
                }
            } else {
                return messageManager.getProperty(MessageManager.CHANGE_PASS_NEW_ERROR);
            }
        } else {
            return messageManager.getProperty(MessageManager.CHANGE_PASS_EQUAL_ERROR);
        }
    }

    public String addFunds(User user, String newCash) throws LogicException {
        Validator validator = new Validator();
        if (validator.isCashValid(newCash)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            try {
                Double cash = Double.valueOf(newCash);
                Double finalCash = user.getCash() + cash;
                userDAO.addFunds(user.getId(), finalCash);
                double newUserCash=userDAO.findCash(user.getId());
                if(newUserCash>0){
                    user.setCash(newUserCash);
                }
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Error during adding money", e);
            } finally {
                userDAO.closeConnection(connection);
            }
        }else{
            return messageManager.getProperty(MessageManager.CHANGE_CASH_ERROR);
        }
    }

    public String addComment(User user, String text, int trackId) throws LogicException {
        Validator validator = new Validator();
        if (validator.isCommentValid(text)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            try {
                userDAO.addComment(user.getId(), text, trackId);
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Error during comment addition", e);
            } finally {
                userDAO.closeConnection(connection);
            }
        }else{
            return messageManager.getProperty(MessageManager.ADD_COMMENT_ERROR);
        }
    }
}
