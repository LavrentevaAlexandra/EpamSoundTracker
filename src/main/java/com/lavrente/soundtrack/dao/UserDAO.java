package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 123 on 03.01.2017.
 */

@SuppressWarnings("Duplicates")
public class UserDAO extends AbstractDAO<User>{
    private static final String SQL_SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM user WHERE login=?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String SQL_ADD_USER = "INSERT INTO user(login,password,card_number, email) VALUES(?,?,?,?)";
    private static final String SQL_CHANGE_LOGIN="UPDATE user SET login=? WHERE id=?";
    private static final String SQL_CHANGE_EMAIL="UPDATE user SET email=? WHERE id=?";
    private static final String SQL_CHANGE_CARD="UPDATE user SET card_number=? WHERE id=?";
    private static final String SQL_CHANGE_PASS="UPDATE user SET password=? WHERE id=?";
    private static final String SQL_CHANGE_CASH="UPDATE user SET cash_account=? WHERE id=?";

    public UserDAO(ProxyConnection connection) {
            super(connection);
    }

    public String findPassword(String login) throws DAOException{
        String password = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN);
            statement.setString(1,login);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                password = set.getString(1);
            }
        }catch (SQLException e){
            throw new DAOException("Error during user password by login search", e);
        }finally {
            closeStatement(statement);
        }
        return password;
    }

    public User findUser(String login) throws DAOException{
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1,login);
            user = createUser(statement);
        }catch (SQLException e){
            throw new DAOException("Error during user by login search",e);
        }finally {
            closeStatement(statement);
        }
        return user;
    }

    public User findUserById(int id) throws DAOException {
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            user = createUser(statement);
        } catch (SQLException e) {
            throw new DAOException("Error during user by id search",e);
        } finally {
            closeStatement(statement);
        }
        return user;
    }

    public User findUserByEmail(String email) throws DAOException{
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            statement.setString(1,email);
            user = createUser(statement);
        }catch (SQLException e){
            throw new DAOException("Error during user by email search",e);
        }finally {
            closeStatement(statement);
        }
        return user;
    }

    public User createUser(PreparedStatement statement) throws SQLException{
        ResultSet set=statement.executeQuery();
        if(set.next()){
            int id=set.getInt("id");
            String login=set.getString("login");
            String password=set.getString("password");
            double cash = set.getDouble("cash_account");
            int role = set.getInt("role");
            int discount=set.getInt("discount");
            String card_number = set.getString("card_number");
            String email = set.getString("email");
            return new User(id,login,password,cash,role,discount,card_number,email);
        }else {
            return null;
        }
    }

    public void addUser(String login, String password, String cardNumber, String email) throws DAOException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1,login);
            statement.setString(2,password);
            statement.setString(3,cardNumber);
            statement.setString(4,email);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Error during user addition",e);
        }finally {
            closeStatement(statement);
        }
    }

    public void changeLogin(int userId, String newLogin) throws DAOException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_LOGIN);
            statement.setString(1,newLogin);
            statement.setInt(2,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Error during changing login",e);
        }finally {
            closeStatement(statement);
        }
    }

    public void changeEmail(int userId, String newEmail) throws DAOException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_EMAIL);
            statement.setString(1,newEmail);
            statement.setInt(2,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Error during changing email",e);
        }finally {
            closeStatement(statement);
        }
    }

    public void changePassword(int userId, String newPass) throws DAOException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PASS);
            statement.setString(1,newPass);
            statement.setInt(2,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Error during changing password",e);
        }finally {
            closeStatement(statement);
        }
    }

    public void changeCardNumber(int userId, String newCard) throws DAOException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_CARD);
            statement.setString(1,newCard);
            statement.setInt(2,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Error during changing card number",e);
        }finally {
            closeStatement(statement);
        }
    }

    public void addFunds(int userId, Double cash) throws DAOException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_CASH);
            statement.setDouble(1,cash);
            statement.setInt(2,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Error during adding money",e);
        }finally {
            closeStatement(statement);
        }
    }
}

