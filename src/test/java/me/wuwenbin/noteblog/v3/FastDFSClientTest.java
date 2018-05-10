package me.wuwenbin.noteblog.v3;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.zhaoming.blog.v1.utils.FastDFSClient;

public class FastDFSClientTest {

    /**
     * 文件上传测试
     */
    @Test
    public void testUpload() {
        File file = new File("F:/15008d2ac854bfd290334ef95de1bf31209c6731.jpg");
        Map<String,String> metaList = new HashMap<String, String>();
        metaList.put("width","1024");
        metaList.put("height","768");
        metaList.put("author","testimg");
        metaList.put("date","20180508");
        String fid = FastDFSClient.uploadFile(file,file.getName().substring(file.getName().lastIndexOf(".")+1),metaList);
        System.out.println("upload local file " + file.getPath() + " ok, fileid=" + fid);
        //上传成功返回的文件ID： group1/M00/00/00/wKgAyVgFk9aAB8hwAA-8Q6_7tHw351.jpg
    }

    /**
     * 文件下载测试
     */
    @Test
    public void testDownload() {
        int r = FastDFSClient.downloadFile("group1/M00/00/00/wKhYolrxefmAZw8VAAArQ-BeA98003.jpg", new File("e:/DownloadFile_fid.jpg"));
        System.out.println(r == 0 ? "下载成功" : "下载失败");
    }

    /**
     * 获取文件元数据测试
     */
    @Test
    public void testGetFileMetadata() {
        Map<String,String> metaList = FastDFSClient.getFileMetadata("group1/M00/00/00/wKhYolrxefmAZw8VAAArQ-BeA98003.jpg");
        for (Iterator<Map.Entry<String,String>>  iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String,String> entry = iterator.next();
            String name = entry.getKey();
            String value = entry.getValue();
            System.out.println(name + " = " + value );
        }
    }

    /**
     * 文件删除测试
     */
    @Test
    public void testDelete() {
        int r = FastDFSClient.deleteFile("group1/M00/00/00/wKhYolrxMX2AVuKvAAAAWSgIB6c975_big.txt");
        System.out.println(r == 0 ? "删除成功" : "删除失败");
    }
}
