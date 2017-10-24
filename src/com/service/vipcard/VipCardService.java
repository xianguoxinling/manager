package com.service.vipcard;

import java.util.ArrayList;
import java.util.List;

import com.db.manager.VipCardDBManager;
import com.model.vipcard.VipCard;
import com.service.interfaces.VipCardServiceInterface;
import com.until.errorcode.MAGICCODE;

public class VipCardService implements VipCardServiceInterface
{

    private VipCardDBManager dbManager = null;
    public VipCardService()
    {
        dbManager = new VipCardDBManager();
    }
    
    @Override
    public int createVipCard(VipCard vipCard)
    {
        if(null == vipCard || null == vipCard.getMagicKey())
        {
            return MAGICCODE.PLATFORM_ERROR;
        }
        
        int result = dbManager.createVipCard(vipCard);
        if(MAGICCODE.OK != result)
        {
            
        }
        
        return result;
    }

    @Override
    public VipCard queryVipCard(String id, String magicKey)
    {
        return dbManager.queryVipCard(id, magicKey);
    }

    @Override
    public List<VipCard> queryVipCard(String magicKey)
    {
        List<VipCard> vipCardList = new ArrayList<VipCard>();
        int result = dbManager.queryVipCard(vipCardList, magicKey);
        if(MAGICCODE.OK != result)
        {
            
        }
        return vipCardList;
    }

}
