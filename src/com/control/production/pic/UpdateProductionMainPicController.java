package com.control.production.pic;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.production.ProductionPic;
import com.platform.base.UserCookieManager;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.replace.ReplaceSrvToHttp;

public class UpdateProductionMainPicController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("update production main!");
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
        String productionUUID = request.getParameter("producitonUUID");
        String picID = request.getParameter("picID");
        if(null == productionUUID || null == picID)
        {
        	
        }
        
        ProductionServiceInterface service = new ProductionService();
        int result = service.updateProducitonMainPicByID(picID, productionUUID, token, keyID);
        if(MAGICCODE.OK != result)
        {
        	
        }
        
        List<ProductionPic> picList = service.queryProductionPic(productionUUID, keyID);
        Iterator<ProductionPic> it = picList.iterator();
        while(it.hasNext())
        {
            ProductionPic productionPic = it.next();
            String mainPic = productionPic.getPath();
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
                productionPic.setPath(ReplaceSrvToHttp.replace(newPic.toString()));
            }
        }
        return new ModelAndView("/store/productpic.jsp","piclist",picList);
    }
}
