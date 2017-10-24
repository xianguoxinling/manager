package com.control.production;

import java.util.Iterator;
import java.util.List;

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
import com.until.replace.ReplaceSrvToHttp;

public class EditProductionController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("EDIT PRODUCTON!");
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
        if(!UntilNum.isNumber(productionID))
        {
            return new ModelAndView("/store/error.html");
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
//        String detailedIntroduction = request.getParameter("detailed_introduction");
        
        Production production = new Production();
        production.setBriefIntroduction(briefIntroduction);
        production.setCategory(category);
//        production.setDetailedIntroduction(detailedIntroduction);
        production.setMagicKey(keyID);
        production.setName(name);
        production.setNumber(num);
        production.setPrice(price);
        production.setId(productionID);
        
        ProductionServiceInterface service = new ProductionService();
        int result = service.updateProduction(token, production);
        if(MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        List<Production> prodcutionList = service.queryAllShopProduction(keyID);
        if(null == prodcutionList)
        {
            return new ModelAndView("/store/error.html");
        }
        Iterator<Production> it = prodcutionList.iterator();
        while(it.hasNext())
        {
            Production p = it.next();
            String mainPic = p.getMainPic();
            if(null != mainPic)
            {
                String[] picFile = mainPic.split("/");
                StringBuffer newPic = new StringBuffer();
                for(int i =0;i<(picFile.length -1);i++)
                {
                    newPic.append(picFile[i]);
                    newPic.append("/");
                }
                newPic.append("midcompress/");
                newPic.append(picFile[picFile.length -1]);
                p.setMainPic(ReplaceSrvToHttp.replace(newPic.toString()));
            }
        }
        
        return new ModelAndView("/store/editproductlist.jsp", "productlist", prodcutionList);
    }

}
