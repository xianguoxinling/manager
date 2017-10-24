package com.control.production.pic;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.until.errorcode.MAGICCODE;

public class UploadPicFromOtherController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("!!!!!!!!ZZZZ!");
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        
        String token = request.getParameter("token");
        if (null == token)
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        String keyID = request.getParameter("keyID");
        if (null == keyID)
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        String uuid = request.getParameter("uuid");
        if(null == uuid)
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        
        
    
        return null;
    }

}
