package com.control.category;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.category.Category;
import com.platform.base.UserCookieManager;
import com.service.category.CategoryService;
import com.service.interfaces.CategoryServiceInterface;
import com.until.errorcode.MAGICCODE;

public class QueryCategoryAjaxController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
//        System.out.println("QUERY CATEGORY AJAX!");
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        JSONArray jsonCategoryList = null;
        OutputStream stream = response.getOutputStream();
        
        HttpSession session = request.getSession(); 
        String token = (String)session.getAttribute("token");
        if (null == token)
        {
            token =  UserCookieManager.getCookieValueByName(request, "token");
            if (null == token)
            {
                map.put("code", MAGICCODE.MAGIC_NOT_LOGIN);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }
        
        String keyID = (String)session.getAttribute("keyID");
        if (null == keyID)
        {
            keyID = UserCookieManager.getCookieValueByName(request, "keyID");
            if(null == keyID)
            {
                map.put("code", MAGICCODE.MAGIC_NOT_LOGIN);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }
        
        CategoryServiceInterface categorySevice = new CategoryService();
        List<Category> categoryList = categorySevice.queryCategory(keyID);
        
        jsonCategoryList = JSONArray.fromObject(categoryList);
        stream.write(jsonCategoryList.toString().getBytes("UTF-8"));
        return null;
    }

}
