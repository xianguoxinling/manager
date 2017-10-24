package com.control.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.order.Order;
import com.platform.base.UserCookieManager;
import com.service.interfaces.OrderServiceInterface;
import com.service.order.OrderService;
import com.until.errorcode.MAGICCODE;
import com.until.num.UntilNum;
import com.until.queryterm.QueryTerm;

public class DeliverOrderController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {

//        System.out.println("DELIVER ORDER!");
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
        
        OrderServiceInterface orderService = new OrderService();        
        String id = request.getParameter("orderID");
        
        System.out.println("Delivery ID:"+id);
        if(!UntilNum.isNumber(id))
        {
            System.out.println("DELIVERY ID ERROR!");
            return new ModelAndView("/store/error.html");
        }
        
        
        
        int result = orderService.deliverOrder(id, token, keyID);
        if(result != MAGICCODE.OK)
        {
            return new ModelAndView("/store/error.html");
        }
        
        QueryTerm queryTerm = new QueryTerm();
        OrderServiceInterface orderSerivice = new OrderService();
        List<Order> orderList = orderSerivice.queryOnroadOrder(token, keyID, queryTerm);
        if(null == orderList)
        {
            return new ModelAndView("/store/error.html");
        }
        request.setAttribute("magiczz", "delivery");
        return new ModelAndView("/order/orderlist.jsp","orderList",orderList);
        
    }
}
