package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.model.order.Order;
import com.model.order.WxPayInfo;
import com.model.production.OrderProduction;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;
import com.until.replace.ReplaceSrvToHttp;

public class OrderDBManager
{
    protected DBManager dbManager = null;
    
    public Order queryOrderInfoByID(String orderID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from ty_order where id = ? and magic_key = ?";
        Order order = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderID);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                order = dbUntil.queryOrderFromResultSet(resultSet);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return order;
    }
    
    public Order queryOrderInfoByUUID(String orderUUID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from ty_order where uuid = ? and magic_key = ?";
        Order order = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderUUID);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                order = dbUntil.queryOrderFromResultSet(resultSet);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return order;
    }
    
    public Order queryOrderInfoByUUID(String orderUUID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from ty_order where uuid = ?";
        Order order = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderUUID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                order = dbUntil.queryOrderFromResultSet(resultSet);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return order;
    }
    
    public int queryAllOrderInfo(List<Order> orderList,String magicKey,QueryTerm queryTerm)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from ty_order where magic_key = ? limit ?,?";
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, magicKey);
            statement.setInt(2, queryTerm.queryBegin);
            statement.setInt(3, queryTerm.queryNum);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                Order order = dbUntil.queryOrderFromResultSet(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int queryOrderInfoByState(List<Order> orderList,String magicKey,String state,QueryTerm queryTerm)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null; 
        String sql = "select * from ty_order where magic_key = ? and state = ? limit ?,?";
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, magicKey);
            statement.setString(2, state);
            statement.setInt(3, queryTerm.queryBegin);
            statement.setInt(4, queryTerm.queryNum);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                Order order = dbUntil.queryOrderFromResultSet(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int queryOrderProduction(List<OrderProduction> productionList,String order_uuid,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null; 
        String sql = "select a.production_id,a.number,a.price,b.main_pic,b.brief_introduction,b.name from order_production a left join production b on a.production_id = b.id where a.order_uuid = ? and a.magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, order_uuid);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                OrderProduction production = new OrderProduction();
                production.setId(resultSet.getString("a.production_id"));
                production.setNum(resultSet.getLong("a.number"));
                production.setPrice(resultSet.getDouble("a.price"));
                String mainPic = resultSet.getString("b.main_pic");
                mainPic = ReplaceSrvToHttp.replace(mainPic);
                production.setMainPic(mainPic);
                production.setBriefIntroduction(resultSet.getString("b.brief_introduction"));
                production.setName(resultSet.getString("b.name"));
                productionList.add(production);
                
                
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public int updateOrderState(String orderID,String state,String magickey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "update ty_order set state = ? where id = ? and magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, state);
            statement.setLong(2, Long.parseLong(orderID));
            statement.setString(3, magickey);
            statement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return MAGICCODE.DB_ERROR;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }
    
    public String queryOrderState(String orderID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null; 
        String sql = "select state from ty_order where id = ? and magic_key = ?";
        String state = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderID);
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                state = resultSet.getString("state");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return state;
    }
    
    public WxPayInfo queryWxPayInfo(String magicKey)
    {
        WxPayInfo wxPayInfo = new WxPayInfo();
        dbManager = DBManager.getInstance();
        wxPayInfo.setMagicKey(magicKey);
        Connection connection = null;
        String sql = "select * from wx_payinfo where magic_key = ?";
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                wxPayInfo.setAPI_KEY(resultSet.getString("api_key"));
                wxPayInfo.setAPP_ID(resultSet.getString("app_id"));
                wxPayInfo.setMCH_ID(resultSet.getString("mch_id"));
                wxPayInfo.setBody(resultSet.getString("body"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return wxPayInfo;
    }
}
