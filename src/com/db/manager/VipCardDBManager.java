package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.model.vipcard.VipCard;
import com.until.errorcode.MAGICCODE;

public class VipCardDBManager
{
    protected DBManager dbManager = null;
    
    public int createVipCard(VipCard vipCard)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "INSERT INTO vipcard(discount,name,magic_key,pic,contact_number,contact_address,introduction,update_time) VALUES (?,?,?,?,?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, vipCard.getDiscount());
            statement.setString(2, vipCard.getName());
            statement.setString(3, vipCard.getMagicKey());
            statement.setString(4, vipCard.getPic());
            statement.setString(5, vipCard.getContactNumber());
            statement.setString(6, vipCard.getContactAddress());
            statement.setString(7, vipCard.getIntroduction());
            statement.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
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
    
    public VipCard queryVipCard(String id,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from vipcard where id = ? and magic_key = ?";
        VipCard vipCard = null;
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, Long.parseLong(id));
            statement.setString(2, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                vipCard = dbUntil.queryVipCardFromResultSet(resultSet);
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
        return vipCard;
    }
    
    public int queryVipCard(List<VipCard> vipCardList,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from vipcard where magic_key = ?";
        DBUntil dbUntil = new DBUntil();
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                VipCard vipCard = dbUntil.queryVipCardFromResultSet(resultSet);
                vipCardList.add(vipCard);
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
}
