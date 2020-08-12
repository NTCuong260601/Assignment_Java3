package dao;

import java.util.ArrayList;

public interface BaseDAO<T> {

    public ArrayList<T> getAllDB();

    public boolean addDB(T t);

    public boolean updateDB( T t);

    public boolean deleteDB( T t);

    public T findToObject(Object username);
}
