package com.service.interfaces;

import java.util.List;

import com.model.vipcard.VipCard;

public interface VipCardServiceInterface
{
    public int createVipCard(VipCard vipCard);
    public VipCard queryVipCard(String id,String magicKey);
    public List<VipCard> queryVipCard(String magicKey);
}
