package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.GenreDAO;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.util.List;

/**
 * Created by 123 on 12.01.2017.
 */
public class GenreLogic {

    /**
     * Find genre id.
     *
     * @param genre the genre
     * @return the int
     * @throws LogicException the logic exception
     */
    public int findGenreId(String genre) throws LogicException{
        int id;
        ProxyConnection connection= ConnectionPool.getInstance().getConnection();
        GenreDAO genreDAO=new GenreDAO(connection);
        try{
            id=genreDAO.findGenreId(genre);
        }catch (DAOException e){

            throw new LogicException("Exception during genre id search", e);
        }finally{
            genreDAO.closeConnection(connection);
        }
        return id;
    }

    /**
     * Find genres.
     *
     * @return the list
     * @throws LogicException the logic exception
     */
    public List<String> findGenres() throws LogicException{
        ProxyConnection connection= ConnectionPool.getInstance().getConnection();
        GenreDAO genreDAO=new GenreDAO(connection);
        try{
            return genreDAO.findGenres();
        }catch (DAOException e){
            throw new LogicException("Exception during genre id search", e);
        }finally{
            genreDAO.closeConnection(connection);
        }
    }
}
