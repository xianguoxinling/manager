package com.control.production;

import java.io.OutputStream;
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
import com.platform.check.CheckParameter;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.replace.ReplaceSrvToHttp;

public class UpdateProductionDetailController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        
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
        String uuid = request.getParameter("productionUUID");
        String detail = request.getParameter("detail");
        
        System.out.println("DETAIL:"+detail);
        
        if(MAGICCODE.OK != CheckParameter.checkParameter(uuid, detail))
        {
        }
        
        ProductionServiceInterface shopService = new ProductionService();
        int result = shopService.updateProductionDetail(uuid, detail, token, keyID);
        if(MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        List<Production> prodcutionList = shopService.queryAllShopProduction(keyID);
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
        
        return new ModelAndView("/store/editproductlist.jsp", "productlist", prodcutionList);
    }
        

}
