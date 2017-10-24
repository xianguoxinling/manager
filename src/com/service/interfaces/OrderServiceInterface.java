package com.service.interfaces;

import java.util.List;

import com.model.order.Order;
import com.until.queryterm.QueryTerm;

public interface OrderServiceInterface
{
    public List<Order> queryAllOrder(String token,String magicKey,QueryTerm queryTerm);
    public List<Order> queryFinishedOrder(String token,String magicKey,QueryTerm queryTerm);
    public List<Order> queryCancledOrder(String token,String magicKey,QueryTerm queryTerm);
    public List<Order> queryPrePayOrder(String token,String magicKey,QueryTerm queryTerm);
    public List<Order> queryPayedOrder(String token,String magicKey,QueryTerm queryTerm);
    public List<Order> queryOnroadOrder(String token,String magicKey,QueryTerm queryTerm);
    
    public int deliverOrder(String orderID,String token,String magicKey);
    public int cancledOrder(String orderID,String token,String magicKey);
    public int finishedOrder(String orderID,String token,String magicKey);
    public Order queryOrderByID(String ID,String magicKey);
    public Order queryOrderByUUID(String uuid,String magicKey);
    public Order queryOrderInfoByUUID(String UUID);
}
