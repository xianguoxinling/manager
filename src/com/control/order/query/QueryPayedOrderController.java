package com.control.order.query;

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
import com.until.queryterm.QueryTerm;

public class QueryPayedOrderController implements Controller
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("QUERY PPAYED ORDER!");
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
        String begin = request.getParameter("begin");
        String num = request.getParameter("num");

        QueryTerm queryTerm = new QueryTerm();
        if (null != begin)
        {
            queryTerm.queryNum = Integer.parseInt(num);
            queryTerm.queryBegin = (Integer.parseInt(begin) - 1) * queryTerm.queryNum;
        }
        OrderServiceInterface orderSerivice = new OrderService();
        List<Order> orderList = orderSerivice.queryPayedOrder(token, keyID, queryTerm);
        if(null == orderList)
        {
            return new ModelAndView("/store/error.html");
        }
        request.setAttribute("magiczz", "payed");
        return new ModelAndView("/order/orderlist.jsp","orderList",orderList);
    }
}