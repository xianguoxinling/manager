package com.control.store;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.shop.ShopPic;
import com.platform.base.UserCookieManager;
import com.service.interfaces.ShopServiceInterface;
import com.service.shop.ShopService;
import com.until.replace.ReplaceSrvToHttp;

public class QueryStorePicController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("QUERY STORE PIC!");
        HttpSession session = request.getSession(); 
        String token = (String)session.getAttribute("token");
        if (null == token)
        {
            token =  UserCookieManager.getCookieValueByName(request, "token");
            if (null == token)
            {
                return new ModelAndView("/store/storelogin.jsp");
            }
        }
        
        String keyID = (String)session.getAttribute("keyID");
        if (null == keyID)
        {
            keyID = UserCookieManager.getCookieValueByName(request, "keyID");
            if(null == keyID)
            {
                return new ModelAndView("/store/storepic.jsp");
            }
        }
        
        ShopServiceInterface storeService = new ShopService();
        List<ShopPic> shopPicList = storeService.queryProductionPic(keyID);
        Iterator<ShopPic> it = shopPicList.iterator();
        while(it.hasNext())
        {
            ShopPic shopPic = it.next();
            shopPic.setPic(ReplaceSrvToHttp.replace(shopPic.getPic()));
        }
        
        if(shopPicList.size()>0)
        {
            return new ModelAndView("/store/storepic.jsp","shoppiclist",shopPicList);
        }else
        {
            return new ModelAndView("/store/addstorepic.jsp");
        }
    }
    
}
