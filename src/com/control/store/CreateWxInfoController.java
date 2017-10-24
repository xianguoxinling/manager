package com.control.store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.shop.WxInfo;
import com.platform.auth.TokenManager;
import com.platform.base.UserCookieManager;
import com.until.errorcode.MAGICCODE;

public class CreateWxInfoController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("CREATE WXINFO!");
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
        
        String app_id = request.getParameter("app_id");
        String app_secret = request.getParameter("app_secret");
        if(null == app_id || null == app_secret)
        {
        	return new ModelAndView("/store/addwxinfo.jsp");
        }
        
        app_id = app_id.trim();
        app_secret = app_secret.trim();
        WxInfo wxInfo = new WxInfo();
        wxInfo.setAppID(app_id);
        wxInfo.setAppSecret(app_secret);
        wxInfo.setMagicKey(keyID);
        
        TokenManager manager = new TokenManager();
        String code = manager.createWxInfo(wxInfo);
//        System.out.println("MANAGER CREATE WXINFO:"+code);
        if(!MAGICCODE.MAGIC_OK.equals(code))
        {
            return new ModelAndView("/store/error.html");
        }else
        {
            return new ModelAndView("/store/addwxinfo.jsp");
        }
    }

}
