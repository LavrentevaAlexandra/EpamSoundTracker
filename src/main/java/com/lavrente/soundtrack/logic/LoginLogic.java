package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.UserDAO;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * Created by 123 on 02.01.2017.
 */
public class LoginLogic {

    /**
     * Check login.
     *
     * @param login the login
     * @param password the password
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public boolean checkLogin(String login, String password) throws LogicException {
        Validator validator = new Validator();
        if (!validator.isLoginValid(login) || !validator.isPasswordValid(password)) {
            return false;
        }
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);
        String md5Pass = DigestUtils.md5Hex(password);
        try {
            String dbPass = userDAO.findPassword(login);
            if (md5Pass.equals(dbPass)) {
                return true;
            }
        } catch (DAOException e) {
            throw new LogicException("Exception during login", e);
        } finally {
            userDAO.closeConnection(connection);
        }
        return false;
    }

}
