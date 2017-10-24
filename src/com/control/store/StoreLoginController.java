package com.control.store;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.manager.store.StoreRegisterManager;
import com.model.production.Production;
import com.platform.check.CheckParameter;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.replace.ReplaceSrvToHttp;

public class StoreLoginController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("QUERY STORE LOING!");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        if(MAGICCODE.OK != CheckParameter.checkParameter(phone,password))
        {
            return new ModelAndView("/store/storelogin.jsp");
        }
        
        StoreRegisterManager storeRegisterManager = new StoreRegisterManager();
        int result = storeRegisterManager.storeLogin(phone, password);
        if(MAGICCODE.OK != result || !MAGICCODE.MAGIC_OK.equals(storeRegisterManager.getCode()))
        {
            return new ModelAndView("/store/storelogin.jsp");
        }
        
        String token = storeRegisterManager.getToken();
        String magicKey = storeRegisterManager.getMagicKey();
        
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        Cookie cookie2 = new Cookie("keyID", magicKey);
        cookie2.setPath("/");
        cookie2.setMaxAge(60 * 60 * 24 * 7);
        ;
        response.addCookie(cookie);
        response.addCookie(cookie2);
        
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        session.setAttribute("keyID", magicKey);
        
        ProductionServiceInterface shopService = new ProductionService();
        List<Production> prodcutionList = shopService.queryAllShopProduction(magicKey);
        if(null == prodcutionList)
        {
            
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
        
        return new ModelAndView("/store/main.jsp", "productlist", prodcutionList);
    }
}
