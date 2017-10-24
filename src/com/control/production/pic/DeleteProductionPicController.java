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

public class DeleteProductionPicController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("delete production pic!");
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
        String picID = request.getParameter("id");
        if(null == picID || "".equals(picID))
        {
            return new ModelAndView("/store/error.html");
        }
        ProductionServiceInterface service = new ProductionService();
        
        int result = service.deleteProductionPic(picID, token, keyID);
        if(MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        String uuid = request.getParameter("uuid");
        List<ProductionPic> picList = service.queryProductionPic(uuid, keyID);
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
        request.setAttribute("uuid", uuid);
        return new ModelAndView("/store/productpic.jsp","piclist",picList);
    }
}
