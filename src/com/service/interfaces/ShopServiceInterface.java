package com.service.interfaces;
import java.util.List;

import com.model.order.WxPayInfo;
import com.model.shop.Shop;
import com.model.shop.ShopPic;

public interface ShopServiceInterface
{
    public int createShop(String shopName,String phone,String password);
    public int updateShopOwner(String userID,String magicKey);
    public List<ShopPic> queryProductionPic(String magicKey);
    public int delShopPic(String token,String id,String magicKey);
    public int createShopPic(ShopPic shopPic);
    public Shop queryShopByKey(String magicKey);
    public int createWxPayInfo(WxPayInfo wxPayInfo);
    public WxPayInfo queryWxPayInfo(String magicKey);
    
    public int updateShopDetail(String token,String detail,String magicKey);
    public String queryShopDetail(String token,String magicKey);
}
