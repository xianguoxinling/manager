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

public class DeleteProductionController implements Controller
{

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
        {
            System.out.println("DELETE PRODUCTION!");
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
            ProductionServiceInterface service = new ProductionService();
            int result = service.deleteProduction(productionID, token, keyID);
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
                Production production = it.next();
                String mainPic = production.getMainPic();
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
                    production.setMainPic(ReplaceSrvToHttp.replace(newPic.toString()));
                }
            }
            
            return new ModelAndView("/store/deleteproductlist.jsp", "productlist", prodcutionList);
        }
            
}
