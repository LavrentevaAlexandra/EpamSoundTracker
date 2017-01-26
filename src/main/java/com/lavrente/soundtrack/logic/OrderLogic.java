package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.OrderDAO;
import com.lavrente.soundtrack.dao.UserDAO;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 123 on 26.01.2017.
 */
public class OrderLogic{

    public void addOrder(int trackId, double price, User user) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        try {
            try {
                connection.setAutoCommit(false);
                double money = user.getCash();
                userDAO.changeCash(user.getId(), money - price);
                orderDAO.addOrder(trackId, price, user.getId());
                connection.commit();
            } catch (SQLException e) {
                throw new LogicException("Exception during order addition", e);
            } catch (DAOException e) {
                connection.rollback();
                throw new LogicException("Exception during order addition", e);
            }finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new LogicException("Exception during order addition", e);
        }
    }

    public boolean isOrdered(int userId, int trackId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return orderDAO.isOrdered(userId, trackId);
        } catch (DAOException e) {
            throw new LogicException("Can't find is order exist", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }

    public List<Track> findMyTracks(int userId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        OrderDAO orderDAO = new OrderDAO(connection);
        try {
            return orderDAO.findOrders(userId);
        } catch (DAOException e) {
            throw new LogicException("Exception during my tracks search", e);
        } finally {
            orderDAO.closeConnection(connection);
        }
    }
}
