package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.GenreDAO;
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
public class TrackLogic implements Messenger {
    private final String SUCCESS = "success";

    public String addTrack(String name, String artist, String price, String genre, String path) throws LogicException {
        Validator validator = new Validator();
        if (validator.isTrackValid(name, artist, price, genre)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            GenreLogic genreLogic = new GenreLogic();
            try {
                int genreId = genreLogic.findGenreId(genre);
                double doublePrice = Double.valueOf(price);
                trackDAO.addTrack(name, artist, doublePrice, genreId, path);
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Exception during track addition", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager.ADD_TRACK_DATA_ERROR);
        }
    }

    public String changeArtist(int trackId, String newArtist) throws LogicException {
        Validator validator = new Validator();
        if (validator.isTrackArtistValid(newArtist)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            try {
                trackDAO.changeArtist(trackId, newArtist);
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Error during changing track artist", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager.CHANGE_TRACK_ARTIST_ERROR);
        }
    }

    public String changeGenre(int trackId, String newGenre) throws LogicException {
        Validator validator = new Validator();
        if (validator.isGenreValid(newGenre)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            GenreDAO genreDAO = new GenreDAO(connection);
            try {
                int genreId=genreDAO.findGenreId(newGenre);
                trackDAO.changeGenre(trackId, genreId);
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Error during changing track genre", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager.CHANGE_TRACK_GENRE_ERROR);
        }
    }

    public String changeName(int trackId, String newName) throws LogicException {
        Validator validator = new Validator();
        if (validator.isTrackNameValid(newName)) {
            ProxyConnection connection = ConnectionPool.getInstance().getConnection();
            TrackDAO trackDAO = new TrackDAO(connection);
            try {
                trackDAO.changeName(trackId, newName);
                return SUCCESS;
            } catch (DAOException e) {
                throw new LogicException("Error during changing track name", e);
            } finally {
                trackDAO.closeConnection(connection);
            }
        } else {
            return messageManager.getProperty(MessageManager.CHANGE_TRACK_NAME_ERROR);
        }
    }

    public String changePrice(int trackId, double prevPrice, String newPrice) throws LogicException {
        Validator validator = new Validator();
        if (validator.isPriceValid(newPrice)) {
            if (!Double.valueOf(newPrice).equals(prevPrice)) {
                ProxyConnection connection = ConnectionPool.getInstance().getConnection();
                TrackDAO trackDAO = new TrackDAO(connection);
                try {
                    trackDAO.changePrice(trackId, Double.valueOf(newPrice));
                    return SUCCESS;
                } catch (DAOException e) {
                    throw new LogicException("Error during changing track price", e);
                } finally {
                    trackDAO.closeConnection(connection);
                }
            }else {
                return SUCCESS;
            }
        } else {
            return messageManager.getProperty(MessageManager.CHANGE_TRACK_PRICE_ERROR);
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

    public List<Track> findAllTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException("Exception during all tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public List<Track> findSuitableTracks(String str) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            List<Track> allTracks = trackDAO.findAll();
            List<Track> res = new ArrayList<>();
            for (Track temp : allTracks) {
                if (temp.getName().toLowerCase().contains(str.toLowerCase())) {
                    res.add(temp);
                } else if (temp.getArtist().toLowerCase().contains(str.toLowerCase())) {
                    res.add(temp);
                }
            }
            return res;
        } catch (DAOException e) {
            throw new LogicException("Exception during tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public List<Track> findDeletedTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findDeletedTracks();
        } catch (DAOException e) {
            throw new LogicException("Exception during deleted tracks search", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public Track findTrackById(int id) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTrackById(id);
        } catch (DAOException e) {
            throw new LogicException("Exception during track by id search", e);
        }finally {
            trackDAO.closeConnection(connection);
        }
    }

    public List<Track> findTracksByGenre(String genre) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findTracksByGenre(genre);
        } catch (DAOException e) {
            throw new LogicException("Exception during track list by genre search", e);
        }finally {
            trackDAO.closeConnection(connection);
        }
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
