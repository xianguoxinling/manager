package com.until.replace;

import com.until.control.base.BaseString;

public class ReplaceSrvToHttp
{
	public static String replace(String base)
	{
		if(null == base)
		{
			return "";
		}
		String result  = base.replace("/srv/www/htdocs/", BaseString.baseURL2);
		return result;
	}
	
	public static void main(String arts[])
	{
		String mainPic = "/srv/www/htdocs/shop/images/ccc65483/d0c49d6c-ff28-477b-96dc-01c1fa6febb7/1.jpg";
		
		String[] picFile = mainPic.split("/");
		StringBuffer newPic = new StringBuffer();
        for(int i =0;i<(picFile.length -1);i++)
        {
            newPic.append(picFile[i]);
            newPic.append("/");
        }
        newPic.append("midcompress/");
        newPic.append(picFile[picFile.length -1]);
		
		String result = ReplaceSrvToHttp.replace(newPic.toString());
		System.out.println(result);
	}
	
}
