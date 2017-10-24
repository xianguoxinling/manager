package com.control.category;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.file.manager.ImgCompress;
import com.model.category.Category;
import com.platform.base.UserCookieManager;
import com.service.category.CategoryService;
import com.service.interfaces.CategoryServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.replace.ReplaceSrvToHttp;

public class CreateCategoryController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String filePath = "/srv/www/htdocs/ty";
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
        if (null == name || "".equals(name))
        {
            return new ModelAndView("/store/error.html");
        }
        String introduction = request.getParameter("introduction");
        Category category = new Category();
        category.setName(name);
        category.setIntroduction(introduction);
        category.setMagicKey(keyID);
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        if(null != multipartFile)
        {
            filePath += "/category/";
            filePath += keyID;
            filePath += "/";
            filePath += UUID.randomUUID().toString();
            filePath += "/";

            File folder = new File(filePath);
            if (!folder.exists())
            {
                folder.mkdirs();
            }
            String fileName =  multipartFile.getOriginalFilename();
            if(null != fileName && (!"".equals(fileName)))
            {
                String attacheFile = filePath + fileName;
                File source = new File(attacheFile);
                multipartFile.transferTo(source);
                compress(filePath,fileName);
                String comprssFile = filePath + "smallcompress";
                comprssFile += "/";
                comprssFile += fileName;
                category.setPic(comprssFile);
            }

        }
        
        CategoryServiceInterface categorySevice = new CategoryService();
        int result = categorySevice.createCategory(category, token);
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
                Category category2= it.next();
                if(null != category2)
                {
                    category2.setPic(ReplaceSrvToHttp.replace(category2.getPic()));
                }
            }
        }
        return new ModelAndView("/category/categorylist.jsp","categoryList",categoryList);
    }

    public int compress(String filePath,String filename)
    {
        //压缩文件路径
        String compressSmallPath = filePath + "/smallcompress";
        File compressSmallFolder = new File(compressSmallPath);
        if (!compressSmallFolder.exists())
        {
            compressSmallFolder.mkdirs();
        }
        
        //压缩
        String oldName = filePath + "/" +filename;
        String newSmallName = compressSmallPath + "/" +filename;
        ImgCompress small;
        try
        {
            small = new ImgCompress(oldName,newSmallName);
            small.resizeFix(100, 100);
        } catch (IOException e)
        {
            return 1;
        } catch (Throwable throwable)
        {
            return 1;
        }
        return 0;

    }
    
}
