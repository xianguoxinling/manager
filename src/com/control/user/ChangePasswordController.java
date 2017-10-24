package com.control.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.platform.auth.TokenManager;
import com.platform.base.UserCookieManager;
import com.platform.check.CheckParameter;
import com.until.errorcode.MAGICCODE;

public class ChangePasswordController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
//        System.out.println("CHANGE PASSWORD!");
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
        
        String oldPassword = request.getParameter("oldpassword");
        String newPassword = request.getParameter("password");
//        System.out.println("TOKEN:"+token);
//        System.out.println("KEYID:"+keyID);
//        System.out.println("OLDPASSWORD:"+oldPassword);
//        System.out.println("NEWPASSWORD:"+newPassword);
        
        
        
        int result = CheckParameter.checkParameter(oldPassword,newPassword);
        if (MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        TokenManager tokenManager = new TokenManager();
        String code = tokenManager.changepassword(keyID, token, oldPassword, newPassword);
        
//        System.out.println("CHANGE PASSWORD AUTH CODE:"+code);
        if(MAGICCODE.MAGIC_OK.equals(code))
        {
            return new ModelAndView("/user/changesucces.html");
        }
        else
        {
            return new ModelAndView("/store/error.html");
        }
        
        
    }

}
