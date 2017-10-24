package com.control.production;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.production.Production;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;
import com.until.replace.ReplaceSrvToHttp;

public class QueryProductionByCategoryController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("Query by category PRODUCTION!");
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        JSONArray productionJsonList = null;
        OutputStream stream = response.getOutputStream();
        String keyID = request.getParameter("keyID");
        if (null == keyID)
        {
            map.put("code", MAGICCODE.MAGIC_KEY_NULL);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        }

        String category = request.getParameter("category");
        if(null == category || "".equals(category))
        {
            map.put("code", MAGICCODE.MAGIC_PARAMETER_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        String begin = request.getParameter("begin");
        String num = request.getParameter("num");

        QueryTerm queryTerm = new QueryTerm();
        if (null != begin)
        {
            queryTerm.queryNum = Integer.parseInt(num);
            queryTerm.queryBegin = (Integer.parseInt(begin) - 1) * queryTerm.queryNum;
        }
        
        ProductionServiceInterface service = new ProductionService();
        List<Production> productionList = service.queryProductionByCategoryName(category, keyID, queryTerm);
        if(null == productionList)
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        Iterator<Production> it = productionList.iterator();
        while(it.hasNext())
        {
            Production production = it.next();
            production.setMainPic(ReplaceSrvToHttp.replace(production.getMainPic()));
        }
        
        productionJsonList = JSONArray.fromObject(productionList);
        stream.write(productionJsonList.toString().getBytes("UTF-8"));
        return null;
    }

}
