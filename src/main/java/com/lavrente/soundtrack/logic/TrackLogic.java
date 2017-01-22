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

import java.util.List;

/**
 * Created by 123 on 11.01.2017.
 */
public class TrackLogic implements Messenger{
    private final String SUCCESS = "success";

    public List<Track> lastTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findLastOrderedTracks();
        } catch (DAOException e) {
            throw new LogicException("Can't find last ordered tracks", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public List<Comment> findTrackComments(int trackId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackComments(trackId);
        } catch (DAOException e) {
            throw new LogicException("Can't find all comments by track id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

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
                throw new LogicException("Error during track addition", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else{
            return messageManager.getProperty(MessageManager.ADD_TRACK_DATA_ERROR);
        }
    }

    public Track findTrackById(int id) throws LogicException {
        Track track;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            track = trackDAO.findTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Error during track by id search", e);
        }
        return track;
    }

    public void deleteTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            trackDAO.deleteTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Error during track removal", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
}
