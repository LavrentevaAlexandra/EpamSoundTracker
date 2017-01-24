package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.TrackDAO;
import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.manager.Messenger;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 11.01.2017.
 */
public class TrackLogic implements Messenger{
    private final String SUCCESS = "success";

    public String addTrack(String name, String artist, String price, String genre, String path) throws LogicException {
        Validator validator = new Validator();
        if (validator.isTrackValid(name, artist, price, genre)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            GenreLogic genreLogic = new GenreLogic();
            try {
                int genreId = genreLogic.findGenreId(genre);
                double doublePrice=Double.valueOf(price);
                trackDAO.addTrack(name, artist, doublePrice, genreId, path );
                return SUCCESS ;
            } catch (DAOException e) {
                throw new LogicException("Exception during track addition", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else{
            return messageManager.getProperty(MessageManager.ADD_TRACK_DATA_ERROR);
        }
    }

    public void deleteTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.deleteTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Exception during track removal", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public ArrayList<Track> findDeletedTracks() throws LogicException{
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Track>) trackDAO.findDeletedTracks();
        } catch (DAOException e) {
            throw new LogicException("Exception during deleted tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public Track findTrackById(int id) throws LogicException {
        Track track;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            track = trackDAO.findTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Exception during track by id search", e);
        }
        return track;
    }

    public List<Comment> findTrackComments(int trackId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackComments(trackId);
        } catch (DAOException e) {
            throw new LogicException("Exception during all comments by track id search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public List<Track> lastTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findLastOrderedTracks();
        } catch (DAOException e) {
            throw new LogicException("Exception during last ordered tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public void recoverTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.recoverTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Exception during track recover", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
}
