package com.test.ut;

public class TestFileLastName
{

    public static void main(String[] args)
    {
        String fileName = "12541512.jpg";
        String fileNameStr[] = fileName.split("\\.");
        System.out.println(fileNameStr[fileNameStr.length-1]);

    }

}
