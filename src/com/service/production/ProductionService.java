package com.service.production;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.db.manager.ProductionDBManager;
import com.model.production.Production;
import com.model.production.ProductionPic;
import com.model.production.ProductionState;
import com.service.interfaces.ProductionServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;

public class ProductionService implements ProductionServiceInterface
{

    private ProductionDBManager dbManager = null;

    public ProductionService()
    {
        dbManager = new ProductionDBManager();
    }

    @Override
    public int createProduction(Production production, String userID)
    {
        int result = MAGICCODE.OK;
        // check auth

        result = dbManager.createProduction(production);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    public Production queryProduction(String id, String magicKey)
    {
        Production production = dbManager.queryProductionByID(id, magicKey);
        if (null == production)
        {

        }
        return production;
    }

    @Override
    public Production queryProductionByID(String id, String magicKey)
    {
        Production production = dbManager.queryProductionByID(id, magicKey);
        if (null != production)
        {
            int result = dbManager.queryProductionPic(production);
            if (MAGICCODE.OK != result)
            {

            }
        }
        return production;
    }

    public Production queryProductionByUUID(String uuid,String magicKey)
    {
        Production production = dbManager.queryProductionByUUID(uuid,magicKey);
        if (null != production)
        {
            int result = dbManager.queryProductionPic(production);
            if (MAGICCODE.OK != result)
            {

            }
        }
        return production;
    }
    @Override
    public List<Production> queryProductionByCategoryName(String categoryName, String magicKey, QueryTerm queryTerm)
    {
        List<Production> productionList = new ArrayList<Production>();
        int result = dbManager.queryProductionByCategoryName(categoryName, productionList, queryTerm, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return productionList;
    }

    public List<Production> queryAllProduction(String magicKey,QueryTerm queryTerm)
    {
        List<Production> productionList = new ArrayList<Production>();
        int result = dbManager.queryAllProduction(productionList, queryTerm, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return productionList;
    }
    
    public List<Production> queryAllShopProduction(String magicKey)
    {
        List<Production> productionList = new ArrayList<Production>();
        int result = dbManager.queryShopAllProduction(productionList, magicKey);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        return productionList;
    }
    
    public List<Production> queryAllShopOnlineProduction(String magicKey)
    {
        List<Production> productionList = new ArrayList<Production>();
        int result = dbManager.queryShopAllProductionByState(productionList, magicKey,"online");
        if (MAGICCODE.OK != result)
        {

        }
        return productionList;
    }
    public List<Production> queryAllShopOffsheltProduction(String magicKey)
    {
        List<Production> productionList = new ArrayList<Production>();
        int result = dbManager.queryShopAllProductionByState(productionList, magicKey,"offshelt");
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        return productionList;
    }
    
    @Override
    public int createProductionPic(String uuid, List<String> picList, String userID, String magicKey)
    {
        int result = MAGICCODE.OK;
        // check Auth
        if (null == picList)
        {
            return MAGICCODE.OK;
        }
        // Collections.sort(picList);
        // Collections.reverse(picList);
        Collections.sort(picList, new Comparator<Object>()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                return ((String) o2).compareTo((String) o1);
            }
        });
        
        List<ProductionPic> productionPicList = new ArrayList<ProductionPic>();
        result = dbManager.queryProductionPic(uuid, magicKey, productionPicList);
        if(productionPicList.size() == 0)
        {
            String mainPic = picList.get(0);
            result = dbManager.createProductionMainPic(uuid, mainPic, magicKey);
            if (MAGICCODE.OK != result)
            {

            }
        }

        result = dbManager.createProductionPic(uuid, picList, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    @Override
    public int checkProductionExist(String productionID, String magicKey)
    {
        Production production = queryProduction(productionID, magicKey);
        if (null == production)
        {
            return MAGICCODE.SHOP_PRODUCTION_NOT_EXIST;
        }
        return MAGICCODE.OK;
    }

    @Override
    public long queryProductionNum(String id, String magicKey)
    {
        long num = 0;
        num = dbManager.queryProductionNum(id, magicKey);
        if (MAGICCODE.DB_ERROR == num)
        {

        }
        return num;
    }

    @Override
    public int deleteProduction(String id, String token, String magicKey)
    {
        int result = MAGICCODE.OK;
        
        result = dbManager.updateProductionState(id, magicKey, ProductionState.DELETE);
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

	@Override
	public int onlineProduction(String id, String token, String magicKey)
    {
        int result = MAGICCODE.OK;
        
        result = dbManager.updateProductionState(id, magicKey, "online");
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
	}

	@Override
	public int offsheltProduction(String id, String token, String magicKey) 
	{
        int result = MAGICCODE.OK;
        
        System.out.println("OFFSHELT PRODUCTION:"+id+"MAGICCODEKEY:"+magicKey);
        result = dbManager.updateProductionState(id, magicKey, "offshelt");
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
	}
	
	public List<ProductionPic> queryProductionPic(String uuid,String magicKey)
	{
	    List<ProductionPic> productionPicList = new ArrayList<ProductionPic>();
	    int result = MAGICCODE.OK;
	    result = dbManager.queryProductionPic(uuid, magicKey, productionPicList);
	    if(MAGICCODE.OK != result)
	    {
	        
	    }
	    
	    return productionPicList;
	}
    
	public int updateProduction(String token,Production production)
	{
	    int result = MAGICCODE.OK;
	    result = dbManager.updateProduction(production);
	    if(MAGICCODE.OK != result)
	    {
	        
	    }
	    return result;
	}

    @Override
    public int addProductonPic(String path,String producitonUUID, String token, String magicKey)
    {
        int result = MAGICCODE.OK;
        result = dbManager.createProductionPic(path, producitonUUID, magicKey);
        if(MAGICCODE.OK != result)
        {
            
        }
        
        return result;
    }

    @Override
    public int deleteProductionPic(String id, String token, String magicKey)
    {
        int result = MAGICCODE.OK;
        result = dbManager.deleteProductionPic(id, magicKey);
        if(MAGICCODE.OK != result)
        {
            
        }
        
        return result;
    }

    @Override
    public int updateProductionMainPic(String path, String producitonUUID, String token, String magicKey)
    {
        int result = MAGICCODE.OK;
        result = dbManager.updateProductionMainPic(path, producitonUUID, magicKey);
        if(MAGICCODE.OK != result)
        {
            
        }
        return result;
    }

    @Override
    public int updateProducitonMainPicByID(String id,String producitonUUID, String token, String magicKey)
    {
        int result = MAGICCODE.ERROR;
        String path = queryProductionPicByID(id);
        if(null != path)
        {
        	result = updateProductionMainPic(path,producitonUUID,token,magicKey);
        }
        return result;
    }
    
    public String queryProductionPicByID(String id)
    {
    	return dbManager.queryProductionPicByID(id);
    }

    @Override
    public int updateProductionDetail(String productionUUID,String detail, String token, String magicKey)
    {
        int result = dbManager.updateProductionDetail(detail, productionUUID, magicKey);
        if(MAGICCODE.OK != result)
        {
            return result;
        }
        return result;
    }

    @Override
    public String queryProducitonDetail(String productionUUID, String token, String magicKey)
    {
        String detail = dbManager.queryProductionDetail(productionUUID, magicKey);
        return detail;
    }
}
