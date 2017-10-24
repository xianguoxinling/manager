package com.control.production;

import java.io.OutputStream;
import java.util.HashMap;
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

public class QueryProductionByIDForEditController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("Query by id for edit PRODUCTION!");
//        Map<String, String> map = new HashMap<String, String>();
//        JSONObject json = null;
//        OutputStream stream = response.getOutputStream();
        
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
        String productionID = request.getParameter("productionID");
        ProductionServiceInterface service = new ProductionService();
        Production production = service.queryProductionByID(productionID, keyID);
        if(null == production)
        {
            
        }
        
        request.setAttribute("category", production.getCategory());
//        map.put("category", production.getCategory());
//        json = JSONObject.fromObject(map);
//        stream.write(json.toString().getBytes("UTF-8"));
        
        
        return new ModelAndView("/store/editproduct.jsp","production",production);
    }
}
