package com.control.store;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.shop.WxInfo;
import com.platform.auth.TokenManager;
import com.platform.base.UserCookieManager;
import com.until.errorcode.MAGICCODE;

public class QueryWxInfoController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("QUERY WXINFO!");
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        
        HttpSession session = request.getSession(); 
        String token = (String)session.getAttribute("token");
        if (null == token)
        {
            token =  UserCookieManager.getCookieValueByName(request, "token");
            if (null == token)
            {
                map.put("code", MAGICCODE.MAGIC_NOT_LOGIN);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }
        
        String keyID = (String)session.getAttribute("keyID");
        if (null == keyID)
        {
            keyID = UserCookieManager.getCookieValueByName(request, "keyID");
            if(null == keyID)
            {
                map.put("code", MAGICCODE.MAGIC_NOT_LOGIN);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }
        
        TokenManager tokenManager = new TokenManager();
        WxInfo wxInfo = tokenManager.queryWxInfo(keyID);
        if(null == wxInfo)
        {
            map.put("code", MAGICCODE.MAGIC_WXINFO_NULL);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        }else
        {
            json = JSONObject.fromObject(wxInfo);
            stream.write(json.toString().getBytes("UTF-8"));
        }
        
        return null;
    }

}
