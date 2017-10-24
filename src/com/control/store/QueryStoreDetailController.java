package com.control.store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.platform.base.UserCookieManager;
import com.service.interfaces.ShopServiceInterface;
import com.service.shop.ShopService;

public class QueryStoreDetailController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
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
                return new ModelAndView("/store/storelogin.jsp");
            }
        }
        
        ShopServiceInterface service = new ShopService();
        String detail = service.queryShopDetail(token, keyID);
        request.setAttribute("magic", detail);
        
        return new ModelAndView("/store/storeinfo.jsp");
    }

}
