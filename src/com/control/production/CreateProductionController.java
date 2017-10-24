package com.control.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.production.Production;
import com.platform.base.UserCookieManager;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.num.UntilNum;

public class CreateProductionController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("CREATE PRODUCTION!");
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
        
        String name = request.getParameter("name");
        if(null == name || "".equals(name))
        {
            return new ModelAndView("/store/error.html");
        }
        
        String priceStr = request.getParameter("price");
        String numStr = request.getParameter("num");
        if((!UntilNum.isNumber(priceStr)) || (!UntilNum.isNumber(numStr)))
        {
            return new ModelAndView("/store/error.html");
        }
        
        long num = Long.parseLong(numStr);
        double price = Double.parseDouble(priceStr);
        String category = request.getParameter("category");
        String briefIntroduction = request.getParameter("brief_introduction");
        String detailedIntroduction = request.getParameter("detailed_introduction");
        
        Production production = new Production();
        production.setBriefIntroduction(briefIntroduction);
        production.setCategory(category);
        production.setDetailedIntroduction(detailedIntroduction);
        production.setMagicKey(keyID);
        production.setName(name);
        production.setNumber(num);
        production.setPrice(price);
        
        ProductionServiceInterface service = new ProductionService();
        int result = service.createProduction(production, token);
        if(MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        return new ModelAndView("/store/uploadpic.jsp", "p_uuid", production.getUuid());
    }
}
