package com.service.interfaces;

import java.util.List;

import com.model.production.Production;
import com.model.production.ProductionPic;
import com.until.queryterm.QueryTerm;

public interface ProductionServiceInterface
{
    public int createProduction(Production production,String userID);
    public int createProductionPic(String uuid,List<String> picList,String userID,String magicKey);
    public List<Production> queryAllProduction(String magicKey,QueryTerm queryTerm);
    public List<Production> queryAllShopProduction(String magicKey);
    public List<Production> queryAllShopOnlineProduction(String magicKey);
    public List<Production> queryAllShopOffsheltProduction(String magicKey);
    public Production queryProductionByID(String id,String magicKey);
    public Production queryProductionByUUID(String uuid,String magicKey);
    public List<Production> queryProductionByCategoryName(String categoryName,String magicKey,QueryTerm queryTerm);
    public int checkProductionExist(String productionID,String magicKey);
    public long queryProductionNum(String id,String magicKey);
    public int deleteProduction(String id,String token,String magicKey);
    public int onlineProduction(String id,String token,String magicKey);
    public int offsheltProduction(String id,String token,String magicKey);
    public List<ProductionPic> queryProductionPic(String uuid,String magicKey);
    public int updateProduction(String token,Production producton);
    
    public int addProductonPic(String path,String producitonUUID,String token,String magicKey);
    public int deleteProductionPic(String id,String token,String magicKey);
    public int updateProductionMainPic(String path,String producitonUUID,String token,String magicKey);
    public int updateProducitonMainPicByID(String id,String producitonUUID,String token,String magicKey);
    public int updateProductionDetail(String productionUUID,String detail, String token, String magicKey);
    public String queryProducitonDetail(String productionUUID,String token,String magicKey);
    
}
