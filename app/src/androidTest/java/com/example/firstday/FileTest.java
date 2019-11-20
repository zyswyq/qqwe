package com.example.firstday;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.test.runner.AndroidJUnit4;

import com.example.firstday.util.FileUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FileTest {

    String TAG = "BASE_TEST";


    /****   test    ****/
    @Test
    public void testCreateFolder() {
        // 传空时
        boolean flag = createFolder(null);
        Log.d(TAG, "testCreateFolder    1:" + !flag);

        String targetPath = getExtStoragePath() + "dir" + File.separator + "file1";
        deleteDir(targetPath);
        deleteDir(getExtStoragePath() + "dir");

        // 普通创建文件夹
        flag = createFolder(targetPath);
        Log.d(TAG, "testCreateFolder    2:" + (flag && new File(targetPath).exists() && new File(targetPath).isDirectory()));

        // path为文件夹
        flag = createFolder(targetPath);
        Log.d(TAG, "testCreateFolder    3:" + (flag && new File(targetPath).exists() && new File(targetPath).isDirectory()));

        deleteDir(targetPath);
        try {
            new File(targetPath).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "testCreateFolder    file exist & isFile:" + (new File(targetPath).exists() && new File(targetPath).isFile()));

        // path为一个文件
        flag = createFolder(targetPath);
        Log.d(TAG, "testCreateFolder    4:" + (!flag && new File(targetPath).exists() && new File(targetPath).isFile()));
    }

    @Test
    public void testCreateFile() {
        // path传空时
        boolean flag = createFile(null, "111");
        Log.d(TAG, "testCreateFile    1 :" + !flag);

        // filename传空时
        flag = createFile("222", null);
        Log.d(TAG, "testCreateFile    2 :" + !flag);

        // path和filename都传空时
        flag = createFile(null, null);
        Log.d(TAG, "testCreateFile    3 :" + !flag);

        String targetPath = getExtStoragePath() + "dir" + File.separator + "dir2";
        String fileName = "file2";

        deleteDir(targetPath);

        // 普通创建文件
        flag = createFile(targetPath, fileName);
        Log.d(TAG, "testCreateFile    4 :" + (flag && new File(targetPath + File.separator + fileName).exists() && new File(targetPath + File.separator + fileName).isFile()));

        deleteDir(targetPath);

        try {
            new File(targetPath).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "testCreateFile    file exist & isFile:" + (new File(targetPath).exists() && new File(targetPath).isFile()));

        // 文件夹目录是一个文件时
        flag = createFile(targetPath, fileName);
        Log.d(TAG, "testCreateFile    5 :" + (!flag && new File(targetPath).exists() && new File(targetPath).isFile()));

        deleteDir(targetPath);
        new File(targetPath + File.separator + fileName).mkdirs();

        // 目标路径存在文件/文件夹
        flag = createFile(targetPath, fileName);
        Log.d(TAG, "testCreateFile    6 :" + (!flag && new File(targetPath + File.separator + fileName).exists() && new File(targetPath + File.separator + fileName).isDirectory()));

        deleteDir(targetPath);

        // path中带separator
        flag = createFile(targetPath + File.separator, fileName);
        Log.d(TAG, "testCreateFile    7 :" + (flag && new File(targetPath + File.separator + fileName).exists() && new File(targetPath + File.separator + fileName).isFile()));
    }

    @Test
    public void testWrite2File() {
        // path传空时
        boolean flag = write2File(null, "111");
        Log.d(TAG, "testWrite2File    1 :" + !flag);

        String targetPath = getExtStoragePath() + "dir" + File.separator + "file3";
        String content = "hello";

        deleteDir(targetPath);

        // 普通写入文件
        flag = write2File(targetPath, content);
        flag = flag && new File(targetPath).exists() && new File(targetPath).isFile();
        if (flag) {
            flag = readFile(targetPath).endsWith(content);
        }
        Log.d(TAG, "testWrite2File    2 :" + flag);

        deleteDir(targetPath);
        new File(targetPath).mkdirs();

        // 目标path为文件夹
        flag = write2File(targetPath, content);
        Log.d(TAG, "testWrite2File    3 :" + !flag);

        deleteDir(targetPath);

        // 目标path以separator结尾
        flag = write2File(targetPath + File.separator, content);
        Log.d(TAG, "testWrite2File    4 :" + !flag);
    }

    @Test
    public void testRead4File() {
        // path传空时
        String ret = read4File(null);
        Log.d(TAG, "testRead4File    1 :" + TextUtils.isEmpty(ret));

        String targetPath = getExtStoragePath() + "dir" + File.separator + "file4";
        String content = "hello";

        deleteDir(targetPath);
        new File(targetPath).mkdirs();

        // path为dir
        ret = read4File(targetPath);
        Log.d(TAG, "testRead4File    2 :" + TextUtils.isEmpty(ret));

        deleteDir(targetPath);

        // path无文件
        ret = read4File(targetPath);
        Log.d(TAG, "testRead4File    3 :" + TextUtils.isEmpty(ret));

        // 正常
        write2File(targetPath, content);
        ret = read4File(targetPath);
        Log.d(TAG, "testRead4File    4 :" + (ret != null && ret.equals(content)));
    }

    @Test
    public void testDeleteFile() {
        deleteFile(null);

        String targetPath = getExtStoragePath() + "dir" + File.separator + "dir5" + File.separator + "file5";
        String path = getExtStoragePath() + "dir" + File.separator + "dir5";
        String path1 = getExtStoragePath() + "dir" + File.separator + "dir5" + File.separator + "dir55";
        String fileName = "file5";
        deleteDir(targetPath);


        deleteFile(targetPath);

        // 删除文件
        deleteDir(targetPath);
        createFile(path, fileName);
        Log.d(TAG, "testDeleteFile    file exist:" + new File(targetPath).exists());
        deleteFile(targetPath);
        Log.d(TAG, "testDeleteFile    1:" + !new File(targetPath).exists());

        // 删除文件夹及文件夹下所有内如
        deleteDir(targetPath);
        createFile(path, fileName);
        createFile(path, "file51");
        createFile(path1, fileName);
        Log.d(TAG, "testDeleteFile    file exist:" + (new File(targetPath).exists() && new File(path1 + File.separator + fileName).exists()));
        deleteFile(path);
        Log.d(TAG, "testDeleteFile    2:" + !new File(path).exists());
    }

    /****   replace    ****/
    public boolean createFolder(String path) {
        return FileUtil.createDirectory(path);
    }

    public boolean createFile(String path, String name) {
        return FileUtil.createFile(path, name);
    }

    public boolean write2File(String path, String content) {
        return FileUtil.writeInFile(path, content);
    }

    public String read4File(String path) {
        return FileUtil.readFile(path);
    }

    public void deleteFile(String path) {
        FileUtil.deleteDirectory(path);
    }

    /****   privite    ****/
    //删除文件夹和文件夹里面的文件
    public void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWithFile(dir);
    }

    public void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists())
            return;
        if (!dir.isDirectory()) {
            dir.delete();
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public String getExtStoragePath() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    public String readFile(String path) {
        StringBuilder sb = new StringBuilder("");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            //读取文件内容
            while (len > 0) {
                sb.append(new String(buffer, 0, len));

                //继续将数据放到buffer中
                len = inputStream.read(buffer);
            }
            //关闭输入流
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }
}
