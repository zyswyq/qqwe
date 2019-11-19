package com.example.firstday.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 文件操作类
 * 1.创建文件夹
 * 2.创建文件
 * 3.向文件中写入一行字
 * 4.读取文件内容为字符串格式
 * 5.删除文件/文件夹(文件夹下的文件也删除)
 */
public class FileUtil {
    private FileUtil(){
    }
    public static boolean createFile(String path ,String fileName) {
        File file=new File(path+"/"+fileName);
        if (file.exists()){
            return true;}
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createDirectory(String path){
        File file=new File(path);
        if (file.exists()){
            return true;
        }
        return file.mkdirs();
    }
    public static boolean writeInFile(String path ,String content) throws IOException {
        File file=new File(path);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            outputStream=null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (outputStream!=null){
                outputStream.flush();
                outputStream.close();
            }
        }
        return true;
    }
    public static String readFile(String path) throws IOException {
        BufferedReader bufferedReader = null;
        FileInputStream inputStream = null;
        File file=new File(path);
        try {
            inputStream=new FileInputStream(file);
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            inputStream.close();
            bufferedReader.close();
            inputStream=null;
            bufferedReader=null;
            return stringBuilder.toString();
        }catch (Exception e){
            return "失败"+e.getMessage();
        }finally {
            if (inputStream!=null){
                inputStream.close();
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
        }
    }

    public static void deleteDirectory(String path){
        File file=new File(path);
        removeAllFile(file);
    }

    private static void removeAllFile(File file) {
        if (file!=null&& file.exists()){
            if (file.isDirectory()&&file.listFiles().length!=0){
                for (File listFile : file.listFiles()) {
                    if (listFile.isDirectory()){
                        removeAllFile(listFile);
                    }else {
                        listFile.delete();
                    }
                }
            }else{
                file.delete();
            }
        }
    }


}
