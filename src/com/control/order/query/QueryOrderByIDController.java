package com.control.order.query;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.order.Order;
import com.model.production.OrderProduction;
import com.platform.base.UserCookieManager;
import com.service.interfaces.OrderServiceInterface;
import com.service.order.OrderService;
import com.until.num.UntilNum;
import com.until.replace.ReplaceSrvToHttp;

public class QueryOrderByIDController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        System.out.println("QUERY ORDER BY ID ORDER!");
        
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
        
        String orderId = request.getParameter("orderID");
        if(!UntilNum.isNumber(orderId))
        {
            System.out.println("ORDERID　ＥＲＲＯＲ！");
            return new ModelAndView("/store/error.html");
        }
        OrderServiceInterface orderService = new OrderService();
        Order order = orderService.queryOrderByID(orderId, keyID);
        if(null == order)
        {
            return new ModelAndView("/store/error.html");
        }
      
        List<OrderProduction> productionList = order.getProductionList();
        Iterator<OrderProduction> it = productionList.iterator();
        while(it.hasNext())
        {
            OrderProduction production = it.next();
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
        
        return new ModelAndView("/order/orderDetail.jsp","order",order);
    }
                
}
