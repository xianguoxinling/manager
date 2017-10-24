package com.control.category;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.category.Category;
import com.platform.base.UserCookieManager;
import com.service.category.CategoryService;
import com.service.interfaces.CategoryServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.replace.ReplaceSrvToHttp;

public class DeleteCategoryController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
//        System.out.println("DELETE CATEGORY!");
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
        
        String id = request.getParameter("id");
        if (null == id || "".equals(id))
        {
            return new ModelAndView("/store/error.html");

        }
        
        CategoryServiceInterface categorySevice = new CategoryService();
        int result = categorySevice.deleteCategory(id, token,keyID);
        if(MAGICCODE.OK != result)
        {
            return new ModelAndView("/store/error.html");
        }
        
        List<Category> categoryList = categorySevice.queryCategory(keyID);
        if(null == categoryList)
        {
            return new ModelAndView("/store/error.html");
        }
        else
        {
            Iterator<Category> it = categoryList.iterator();
            while(it.hasNext())
            {
                Category category = it.next();
                if(null != category)
                {
                    category.setPic(ReplaceSrvToHttp.replace(category.getPic()));
                }
            }
        }
        return new ModelAndView("/category/categorylist.jsp","categoryList",categoryList);
        
    }

}
