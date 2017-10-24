package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.model.order.WxPayInfo;
import com.model.shop.Shop;
import com.model.shop.ShopPic;
import com.until.errorcode.MAGICCODE;

public class ShopDBManager
{

    protected DBManager dbManager = null;

    public int createShopPic(ShopPic shopPic)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO shop_pic (pic,magic_key,pic_type,pic_type_value) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, shopPic.getPic());
            statement.setString(2, shopPic.getMagicKey());
            statement.setString(3, shopPic.getType());
            statement.setString(4, shopPic.getType_value());
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
    
    public int queryShopPic(List<ShopPic> picList,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from shop_pic where magic_key = ?";
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
                ShopPic shopPic = dbUntil.queryShopPicFromResultSet(resultSet); 
                picList.add(shopPic);
                
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
    
    public int createShop(String shopName,String userID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO ty_shop (name,owner,magic_key,update_time) VALUES (?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, shopName);
            statement.setString(2, userID);
            statement.setString(3, magicKey);
            statement.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
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
    
    public int updateShopOwner(String userID,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "update ty_shop set owner = ? where magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            statement.setString(2, magicKey);
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
    
    public Shop queryShopByKey(String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from ty_shop where magic_key = ?";
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
                Shop shop = dbUntil.queryShopFromResultSet(resultSet); 
                return shop;
                
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
        return null;
    }
    
    public int createWxPayInfo(WxPayInfo wxPayInfo)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "INSERT INTO wx_payinfo (app_id,mch_id,api_key,body,magic_key) VALUES (?,?,?,?,?)";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, wxPayInfo.getAPP_ID());
            statement.setString(2, wxPayInfo.getMCH_ID());
            statement.setString(3, wxPayInfo.getAPI_KEY());
            statement.setString(4, wxPayInfo.getBody());
            statement.setString(5, wxPayInfo.getMagicKey());
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
    
    public int updateWxPayInfo(WxPayInfo wxPayInfo)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "update wx_payinfo set app_id=?, mch_id=?, api_key=?, body=? where magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, wxPayInfo.getAPP_ID());
            statement.setString(2, wxPayInfo.getMCH_ID());
            statement.setString(3, wxPayInfo.getAPI_KEY());
            statement.setString(4, wxPayInfo.getBody());
            statement.setString(5, wxPayInfo.getMagicKey());
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
    
    public WxPayInfo queryWxPayInfo(String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select * from wx_payinfo where magic_key = ?";
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
                WxPayInfo wxPayInfo = dbUntil.queryWxInfoFromResultSet(resultSet);
                return wxPayInfo;
                
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
        return null;
    }
    
    public int delShopPic(String id,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "delete from shop_pic where id = ? and magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, magicKey);
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
    
    public int updateShopDetail(String detail,String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        
        String sql = "update ty_shop set detailedIntroduction = ? where magic_key = ?";

        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, detail);
            statement.setString(2, magicKey);
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
    
    public String queryShopDetail(String magicKey)
    {
        dbManager = DBManager.getInstance();
        Connection connection = null;
        String sql = "select detailedIntroduction from ty_shop where magic_key = ?";
        String detail = null;
        try
        {
            connection = dbManager.getConection();
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, magicKey);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                detail = resultSet.getString("detailedIntroduction");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return detail;
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
        return detail;
    }
}
