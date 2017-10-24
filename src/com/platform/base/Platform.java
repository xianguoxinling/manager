package com.platform.base;

import java.util.UUID;

import com.platform.auth.TokenManager;

public class Platform
{
    public static String ID = "XCXPLATFORM";
    public static String Key = "8ddf4499249244bf9c86eed422ae9162";
    private static Platform platform = null;
    public String token = null;;
    private TokenManager tokenManager = null;
    
    private Platform()
    {
        tokenManager = new TokenManager();
        token = tokenManager.updateToken(ID, Key);
    }
     
    public static Platform getInstance()
    {
        if(platform == null)
        {
            platform = new Platform();
        }
        return platform;
        
    }
    
    public String updateToken()
    {
        token = tokenManager.updateToken(ID, Key);
        return token;
    }
    
    public static void main(String args[])
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);
    }
}
