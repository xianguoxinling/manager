package com.test.ut;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.model.vipcard.VipCard;
import com.service.interfaces.VipCardServiceInterface;
import com.service.vipcard.VipCardService;
import com.until.errorcode.MAGICCODE;

public class VipCardServiceTest
{

    VipCardServiceInterface service = null;
    private String magicKey = "8b276c86da1a4ed8a28575e8a1b7296e";
    
    @Before
    public void setUp() throws Exception
    {
        service = new VipCardService();
    }

    @Test
    public void testCreateVipCard()
    {
//        VipCard vipCard = new VipCard();
//        vipCard.setName("烛照家居会员卡");
//        vipCard.setDiscount(0.98);
//        vipCard.setContactAddress("厦门思明区莲花北路5-7号");
//        vipCard.setContactNumber("15557193876");
//        vipCard.setIntroduction("烛照家居会员卡");
//        vipCard.setMagicKey("8b276c86da1a4ed8a28575e8a1b7296e");
//        vipCard.setPic("/srv/www/htdoc/");
//        
//        int result = service.createVipCard(vipCard);
//        assertEquals(MAGICCODE.OK,result);
        
    }

    @Test
    public void testQueryVipCardStringString()
    {
        VipCard vipCard = service.queryVipCard("1", magicKey);
        assertEquals(vipCard.getName(),"烛照家居会员卡");
        assertEquals(vipCard.getContactAddress(),"厦门思明区莲花北路5-7号");
        assertEquals(vipCard.getContactNumber(),"15557193876");
        assertEquals(vipCard.getMagicKey(),magicKey);
        assertEquals(vipCard.getPic(),"/srv/www/htdoc/");
    }

    @Test
    public void testQueryVipCardString()
    {
        List<VipCard> vipCardList = service.queryVipCard(magicKey);
        assertEquals(1,vipCardList.size());
    }

}
