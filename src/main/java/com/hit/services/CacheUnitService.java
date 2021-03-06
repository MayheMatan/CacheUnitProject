package com.hit.services;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * gets the actions from controller and executes the method.
 * creates an LRU and Dao to save operations in a file
 * */
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> extends java.lang.Object
{

    @SuppressWarnings({ "rawtypes" })
	private CacheUnit cacheUnit;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CacheUnitService() throws FileNotFoundException, IOException
    {
        LRUAlgoCacheImpl<T, DataModel<T>> lru = new LRUAlgoCacheImpl<>(30);
        DaoFileImpl<T> daoFile = new DaoFileImpl<>("src/out.txt");
        this.cacheUnit = new CacheUnit(lru, daoFile);
        for(int i = 0; i < 30; i++) {
            Integer integer = i;
            daoFile.save(new DataModel(Long.valueOf(i), integer));
        }
    }

    //Implementation of actions methods
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean delete(com.hit.dm.DataModel<T>[] dataModels) throws ClassNotFoundException, IOException
    {

        DataModel[] returnModels = null;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i <dataModels.length ; i++) {
            ids[i] = dataModels[i].getDataModelId();
        }
        returnModels = cacheUnit.getDataModels(ids);
        for(DataModel<T> model: returnModels) {
            model.setContent(null);
        }
        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public boolean update(com.hit.dm.DataModel<T>[] dataModels) throws IOException, ClassNotFoundException
    {

        DataModel[] returnModels = null;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i <dataModels.length ; i++) {
            ids[i] = dataModels[i].getDataModelId();
            System.out.println(ids[i]);
        }
        System.out.println(ids);
        System.out.println("After first for");
        returnModels = cacheUnit.getDataModels(ids);
        System.out.println(returnModels.length);
        System.out.println(dataModels.length);
        System.out.println(returnModels);
        System.out.println(dataModels);
        System.out.println("Got datamodels");
        for(int i = 0; i <dataModels.length ; i++) {
            for(int j = 0; j <returnModels.length ; j++) {
            	System.out.println("before if");
            	System.out.println(dataModels[i].getDataModelId ());
            	System.out.println(returnModels[j].getDataModelId());
            	System.out.println(dataModels[i].getDataModelId ().equals (returnModels[j].getDataModelId()));
                if(dataModels[i].getDataModelId ().equals (returnModels[j].getDataModelId ())) {
                    returnModels[j].setContent (dataModels[i].getContent ());
                    j = returnModels.length+1;
                    System.out.println("in if");
                }
                System.out.println("After thrid for");
            }
            System.out.println("After second for");
        }
        for(DataModel model: dataModels) {
            cacheUnit.updateFile(model);
        }
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public com.hit.dm.DataModel<T>[] get(com.hit.dm.DataModel<T>[] dataModels) throws ClassNotFoundException, IOException
    {
        Long[] ids = new Long[dataModels.length];
        DataModel[] models = null;
        for (int i = 0; i < dataModels.length; i++) {
            ids[i] = dataModels[i].getDataModelId();
        }
        models = cacheUnit.getDataModels(ids);
        return models;
    }
}
