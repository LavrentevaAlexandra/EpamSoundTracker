package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.GenreDAO;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;

/**
 * Created by 123 on 12.01.2017.
 */
public class GenreLogic {
    public int findGenreId(String genre) throws LogicException{
        int id;
        ProxyConnection connection= ConnectionPool.getInstance().getConnection();
        GenreDAO genreDAO=new GenreDAO(connection);
        try{
            id=genreDAO.findGenreId(genre);
            if(id==-1){
                id=addNewGenre(genre);
            }
        }catch (DAOException e){
            throw new LogicException("Error during genre id search", e);
        }
        return id;
    }
    public int addNewGenre(String genre) throws LogicException{
        int id;
        ProxyConnection connection= ConnectionPool.getInstance().getConnection();
        GenreDAO genreDAO=new GenreDAO(connection);
        try{
            id=genreDAO.addGenre(genre);
        }catch (DAOException e){
            throw new LogicException("Error during genre id search", e);
        }
        return id;
    }
}
