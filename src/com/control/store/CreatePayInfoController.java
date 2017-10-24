package com.control.store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.order.WxPayInfo;
import com.platform.base.UserCookieManager;
import com.service.interfaces.ShopServiceInterface;
import com.service.shop.ShopService;
import com.until.errorcode.MAGICCODE;

public class CreatePayInfoController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("CREATE PAYINFO!");
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
        
        ShopServiceInterface storeService = new ShopService();
        String body = request.getParameter("name");
        String app_id = request.getParameter("app_id");
        String api_key = request.getParameter("api_key");
        String mch_id = request.getParameter("mch_id");
        if(null == app_id || null == api_key || null == mch_id)
        {
        	return new ModelAndView("/store/addpayinfo.jsp");
        }
        app_id = app_id.trim();
        api_key = api_key.trim();
        mch_id = mch_id.trim();
        
        WxPayInfo wxPayInfo = new WxPayInfo();
        wxPayInfo.setBody(body);
        wxPayInfo.setAPP_ID(app_id);
        wxPayInfo.setAPI_KEY(api_key);
        wxPayInfo.setMCH_ID(mch_id);
        wxPayInfo.setMagicKey(keyID);
        
        int result = storeService.createWxPayInfo(wxPayInfo);
        if(MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        return new ModelAndView("/store/addpayinfo.jsp");
    }

}
