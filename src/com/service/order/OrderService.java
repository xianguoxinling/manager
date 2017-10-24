package com.service.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.db.manager.OrderDBManager;
import com.model.address.Address;
import com.model.order.Order;
import com.model.order.OrderState;
import com.model.production.OrderProduction;
import com.service.address.AddressService;
import com.service.interfaces.AddressServiceInterface;
import com.service.interfaces.OrderServiceInterface;
import com.service.interfaces.ProductionServiceInterface;
import com.service.production.ProductionService;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;

public class OrderService implements OrderServiceInterface
{
    protected OrderDBManager dbManager = null;
    protected AddressServiceInterface addressService = null;
    ProductionServiceInterface productionService = null;

    public OrderService()
    {
        dbManager = new OrderDBManager();
        addressService = new AddressService();
        productionService = new ProductionService();
    }

    public int queryOrderProduction(List<Order> orderList, String magicKey)
    {
        int result = MAGICCODE.OK;
        Iterator<Order> it = orderList.iterator();
        while (it.hasNext())
        {
            int queryNum = 0;
            Order order = it.next();
            order.setAddress(addressService.queryAddressByID(order.getAddress().getId(), magicKey));
            List<OrderProduction> productionList = new ArrayList<OrderProduction>();
            result = dbManager.queryOrderProduction(productionList, order.getUuid(), magicKey);

            while (MAGICCODE.OK != result)
            {
                productionList = new ArrayList<OrderProduction>();
                result = dbManager.queryOrderProduction(productionList, order.getUuid(), magicKey);
                queryNum++;
                if (queryNum >= 3)
                {
                    break;
                }
            }
            order.setProductionList(productionList);
        }

        return result;
    }

    @Override
    public List<Order> queryAllOrder(String token, String magicKey, QueryTerm queryTerm)
    {
        List<Order> orderList = new ArrayList<Order>();
        int result = dbManager.queryAllOrderInfo(orderList, magicKey,queryTerm);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        result = queryOrderProduction(orderList, magicKey);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        return orderList;
    }

    @Override
    public List<Order> queryFinishedOrder(String token, String magicKey, QueryTerm queryTerm)
    {
        List<Order> orderList = new ArrayList<Order>();
        int result = dbManager.queryOrderInfoByState(orderList, magicKey, OrderState.FINISHED, queryTerm);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        result = queryOrderProduction(orderList, magicKey);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        return orderList;
    }

    @Override
    public List<Order> queryOnroadOrder(String token, String magicKey, QueryTerm queryTerm)
    {
        List<Order> orderList = new ArrayList<Order>();
        int result = dbManager.queryOrderInfoByState(orderList, magicKey, OrderState.ON_ROAD, queryTerm);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        result = queryOrderProduction(orderList, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return orderList;
    }

    @Override
    public List<Order> queryCancledOrder(String token, String magicKey, QueryTerm queryTerm)
    {
        List<Order> orderList = new ArrayList<Order>();
        int result = dbManager.queryOrderInfoByState(orderList, magicKey, OrderState.CANCLE, queryTerm);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        result = queryOrderProduction(orderList, magicKey);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        return orderList;
    }

    @Override
    public List<Order> queryPrePayOrder(String token, String magicKey, QueryTerm queryTerm)
    {
        List<Order> orderList = new ArrayList<Order>();
        int result = dbManager.queryOrderInfoByState(orderList, magicKey, OrderState.PR_PAY, queryTerm);
        if (MAGICCODE.OK != result)
        {
            System.out.println("QUERY ORDER STATE ERROR!");
            return null;
        }
        result = queryOrderProduction(orderList, magicKey);
        if (MAGICCODE.OK != result)
        {
            System.out.println("QUERY ORDER PRODUCTION ERROR!");
            return null;
        }
        return orderList;
    }

    @Override
    public List<Order> queryPayedOrder(String userID, String magicKey, QueryTerm queryTerm)
    {
        List<Order> orderList = new ArrayList<Order>();
        int result = dbManager.queryOrderInfoByState(orderList, magicKey, OrderState.PAYED, queryTerm);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        result = queryOrderProduction(orderList, magicKey);
        if (MAGICCODE.OK != result)
        {
            return null;
        }
        return orderList;
    }

    @Override
    public int cancledOrder(String orderID, String token, String magicKey)
    {
//        System.out.println("orderID:" + orderID + " userID:" + userID + " magicKey:" + magicKey);

        String state = dbManager.queryOrderState(orderID, magicKey);
        if (null == state)
        {
            return MAGICCODE.ERROR;
        }
        if (!OrderState.PR_PAY.equals(state))
        {
            return MAGICCODE.SHOP_ORDER_CANOT_BE_CANCLE;
        }

        int result = updateOrderState(orderID, OrderState.CANCLE, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }
    
    public int finishedOrder(String orderID,String token,String magicKey)
    {
        System.out.println("orderID:" + orderID + " userID:" + token + " magicKey:" + magicKey);

        String state = dbManager.queryOrderState(orderID, magicKey);
        if (null == state)
        {
            return MAGICCODE.ERROR;
        }
        
        System.out.println("ORDER STATE:"+state);
        
        if (!OrderState.ON_ROAD.equals(state))
        {
            return MAGICCODE.SHOP_ORDER_CANOT_BE_FINISHED;
        }

        int result = updateOrderState(orderID, OrderState.FINISHED, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    public int deliverOrder(String orderID, String token, String magicKey)
    {
        String state = dbManager.queryOrderState(orderID, magicKey);
        if (null == state)
        {
            return MAGICCODE.ERROR;
        }
        if (!OrderState.PAYED.equals(state))
        {
            return MAGICCODE.SHOP_ORDER_CANOT_BE_DELIVER;
        }

        int result = updateOrderState(orderID, OrderState.ON_ROAD, magicKey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    public int updateOrderState(String orderID, String state, String magickey)
    {
        int result = MAGICCODE.OK;
        result = dbManager.updateOrderState(orderID, state, magickey);
        if (MAGICCODE.OK != result)
        {

        }
        return result;
    }

    @Override
    public Order queryOrderByID(String ID, String magicKey)
    {

        System.out.println("ORDERID:"+ID);
        Order order = dbManager.queryOrderInfoByID(ID, magicKey);
        if (null != order)
        {
            Address address = addressService.queryAddressByID(order.getAddress().getId(), magicKey);
            order.setAddress(address);

            List<OrderProduction> productionList = new ArrayList<OrderProduction>();
            int result = dbManager.queryOrderProduction(productionList, order.getUuid(), magicKey);
            int queryNum = 0;
            while (MAGICCODE.OK != result)
            {
                productionList = new ArrayList<OrderProduction>();
                result = dbManager.queryOrderProduction(productionList, order.getUuid(), magicKey);
                queryNum++;
                if (queryNum >= 3)
                {
                    break;
                }
            }
            order.setProductionList(productionList);
        }
        return order;
    }

    @Override
    public Order queryOrderByUUID(String uuid, String magicKey)
    {
        Order order = dbManager.queryOrderInfoByUUID(uuid, magicKey);
        if (null != order)
        {
            Address address = addressService.queryAddressByID(order.getAddress().getId(), magicKey);
            order.setAddress(address);

            List<OrderProduction> productionList = new ArrayList<OrderProduction>();
            int result = dbManager.queryOrderProduction(productionList, order.getUuid(), magicKey);
            int queryNum = 0;
            while (MAGICCODE.OK != result)
            {
                productionList = new ArrayList<OrderProduction>();
                result = dbManager.queryOrderProduction(productionList, order.getUuid(), magicKey);
                queryNum++;
                if (queryNum >= 3)
                {
                    break;
                }
            }
            order.setProductionList(productionList);
        }
        return order;
    }

    public Order queryOrderInfoByUUID(String UUID)
    {
        Order order = dbManager.queryOrderInfoByUUID(UUID);
        return order;
    }

}
