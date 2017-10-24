package com.control.production.query;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.production.Production;
import com.platform.base.UserCookieManager;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.num.UntilNum;
import com.until.replace.ReplaceSrvToHttp;

public class QueryProductionDetailController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
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
                map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
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
                map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }

        String uuid = request.getParameter("uuid");
        ProductionServiceInterface service = new ProductionService();
        Production production = service.queryProductionByUUID(uuid, keyID);
        if(null == production)
        {
            json = JSONObject.fromObject("");
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        json = JSONObject.fromObject(production);
        stream.write(json.toString().getBytes("UTF-8"));
        return null;
    }
}