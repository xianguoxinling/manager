package com.model.vipcard;

public class VipCard
{
    protected String id = null;
    protected String name = null;
    protected String introduction = null;
    protected String magicKey = null;
    protected double discount = 1;
    protected String pic = null;
    protected String createTime = null;
    protected String contactNumber = null;
    protected String contactAddress = null;
    
    public VipCard()
    {
        
    }    
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getMagicKey()
    {
        return magicKey;
    }
    public void setMagicKey(String magicKey)
    {
        this.magicKey = magicKey;
    }
    public double getDiscount()
    {
        return discount;
    }
    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getContactAddress()
    {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress)
    {
        this.contactAddress = contactAddress;
    }
    
}
