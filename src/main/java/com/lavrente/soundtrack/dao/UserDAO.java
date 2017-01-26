package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 03.01.2017.
 */

@SuppressWarnings("Duplicates")
public class UserDAO extends AbstractDAO<User> {

    /** The Constant SQL_ADD_USER. */
    private static final String SQL_ADD_USER = "INSERT INTO user(login,password,card_number, email) VALUES(?,?,?,?)";

    /** The Constant SQL_ADD_COMMENT. */
    private static final String SQL_ADD_COMMENT = "INSERT INTO comment(user_id,audio_track_id,date,text) VALUES(?,?,?,?)";

    /** The Constant SQL_CHANGE_CARD. */
    private static final String SQL_CHANGE_CARD = "UPDATE user SET card_number=? WHERE id=?";

    /** The Constant SQL_CHANGE_CASH. */
    private static final String SQL_CHANGE_CASH = "UPDATE user SET cash_account=? WHERE id=?";

    /** The Constant SQL_CHANGE_EMAIL. */
    private static final String SQL_CHANGE_EMAIL = "UPDATE user SET email=? WHERE id=?";

    /** The Constant SQL_CHANGE_LOGIN. */
    private static final String SQL_CHANGE_LOGIN = "UPDATE user SET login=? WHERE id=?";

    /** The Constant SQL_CHANGE_PASS. */
    private static final String SQL_CHANGE_PASS = "UPDATE user SET password=? WHERE id=?";

    /** The Constant SQL_SELECT_ALL_CLIENTS. */
    private static final String SQL_SELECT_ALL_CLIENTS = "SELECT user.id, user.login, user.discount, COUNT(user.login) as count\n"+
            " FROM audio_track_order.`order` Left join `user` ON `order`.user_id=`user`.id\n"+
            " GROUP BY user.login ORDER BY user.login";

    /** The Constant SQL_SELECT_CASH. */
    private static final String SQL_SELECT_CASH = "SELECT cash_account FROM user WHERE id=?";

    /** The Constant SQL_SELECT_PASSWORD_BY_LOGIN. */
    private static final String SQL_SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM user WHERE login=?";

    /** The Constant SQL_SELECT_USER_BY_ID. */
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user WHERE id=?";

    /** The Constant SQL_SELECT_USER_BY_LOGIN. */
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";

    /** The Constant SQL_SELECT_USER_BY_EMAIL. */
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";

    /** The Constant SQL_SET_BONUS. */
    private static final String SQL_SET_BONUS = "UPDATE user SET discount=? WHERE id=?";

    /**
     * Instantiates a new user DAO.
     *
     * @param connection the connection
     */
    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Adds the comment.
     *
     * @param userId the user id
     * @param text the text
     * @param trackId the track id
     * @throws DAOException the DAO exception
     */
    public void addComment(int userId, String text, int trackId) throws DAOException {
        PreparedStatement statement = null;
        try {
            Comment comment=new Comment(userId,trackId,text);
            statement = connection.prepareStatement(SQL_ADD_COMMENT);
            statement.setInt(1, userId);
            statement.setInt(2, trackId);
            statement.setString(3,comment.getDateTime());
            statement.setString(4,text);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during comment addition", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Adds the user.
     *
     * @param login the login
     * @param password the password
     * @param cardNumber the card number
     * @param email the email
     * @throws DAOException the DAO exception
     */
    public void addUser(String login, String password, String cardNumber, String email) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, cardNumber);
            statement.setString(4, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during user addition", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change card number.
     *
     * @param userId the user id
     * @param newCard the new card
     * @throws DAOException the DAO exception
     */
    public void changeCardNumber(int userId, String newCard) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_CARD);
            statement.setString(1, newCard);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing card number", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change cash.
     *
     * @param userId the user id
     * @param cash the cash
     * @throws DAOException the DAO exception
     */
    public void changeCash(int userId, Double cash) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_CASH);
            statement.setDouble(1, cash);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during cash change", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change email.
     *
     * @param userId the user id
     * @param newEmail the new email
     * @throws DAOException the DAO exception
     */
    public void changeEmail(int userId, String newEmail) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_EMAIL);
            statement.setString(1, newEmail);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing email", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change login.
     *
     * @param userId the user id
     * @param newLogin the new login
     * @throws DAOException the DAO exception
     */
    public void changeLogin(int userId, String newLogin) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_LOGIN);
            statement.setString(1, newLogin);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing login", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change password.
     *
     * @param userId the user id
     * @param newPass the new pass
     * @throws DAOException the DAO exception
     */
    public void changePassword(int userId, String newPass) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PASS);
            statement.setString(1, newPass);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing password", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Creates the client list.
     *
     * @param set the set
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<User> createClientList(ResultSet set) throws DAOException {
        List<User> clientList=new ArrayList<>();
        try {
            while (set.next()) {
                int userId = set.getInt("id");
                String login = set.getString("login");
                int discount = set.getInt("discount");
                int orderCount = set.getInt("count");
                clientList.add(new User(userId, login, discount, orderCount));
            }
            return clientList;
        } catch (SQLException e) {
            throw new DAOException("Exception during client list creation", e);
        }
    }

    /**
     * Creates the user.
     *
     * @param set the set
     * @return the user
     * @throws DAOException the DAO exception
     */
    private User createUser(ResultSet set) throws DAOException {
        try {
            if (set.next()) {
                int id = set.getInt("id");
                String login = set.getString("login");
                String password = set.getString("password");
                double cash = set.getDouble("cash_account");
                int role = set.getInt("role");
                int discount = set.getInt("discount");
                String card_number = set.getString("card_number");
                String email = set.getString("email");
                return new User(id, login, password, cash, role, discount, card_number, email);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during user creation", e);
        }
    }

    /**
     * Find clients.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<User> findClients()throws DAOException{
        List<User> userList;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_CLIENTS);
            ResultSet set = statement.executeQuery();
            userList = createClientList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during clients search",e);
        } finally {
            closeStatement(statement);
        }
        return userList;
    }

    /**
     * Find cash.
     *
     * @param userId the user id
     * @return the double
     * @throws DAOException the DAO exception
     */
    public double findCash(int userId) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_CASH);
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return set.getInt("cash_account");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during cash search", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Find password.
     *
     * @param login the login
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findPassword(String login) throws DAOException {
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN);
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                password = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during user password by login search", e);
        } finally {
            closeStatement(statement);
        }
        return password;
    }

    /**
     * Find user.
     *
     * @param login the login
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUser(String login) throws DAOException {
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            user = createUser(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during user by login search", e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }

    /**
     * Find user by email.
     *
     * @param email the email
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserByEmail(String email) throws DAOException {
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            user = createUser(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during user by email search", e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }

    /**
     * Find user by id.
     *
     * @param id the id
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserById(int id) throws DAOException {
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            user = createUser(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during user by id search", e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }

    /**
     * Sets the bonus.
     *
     * @param userId the user id
     * @param bonus the bonus
     * @throws DAOException the DAO exception
     */
    public void setBonus(int userId, int bonus) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SET_BONUS);
            statement.setInt(1, bonus);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during bonus setting", e);
        } finally {
            closeStatement(statement);
        }
    }
}

