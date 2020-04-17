package com.teligen.demo.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;

//import com.sun.image.codec.jpeg.*;


public class ImageUtils {
	private Image img;  
    private int width;  
    private int height;  
    private OutputStream out;
    private String formatName = "JPG";
    @SuppressWarnings("deprecation")  
    public static void main(String[] args) throws Exception {  
        System.out.println("开始：" + new Date().toLocaleString());  
        long t1 = System.currentTimeMillis();
        File destFile = new File("C:\\Users\\huangz\\Desktop\\zxh\\tt\\1.jpg");
        OutputStream bOut = new FileOutputStream(destFile);
        ImageUtils imgCom = new ImageUtils("C:\\Users\\huangz\\Desktop\\zxh\\1.jpg", bOut);
        imgCom.resizeFix(400, 400);  
        InputStream inStream = new FileInputStream(destFile);
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);  
        System.out.println("结束：" + new Date().toLocaleString());  
    }  
    /** 
     * 构造函数 
     */  
    public ImageUtils(String fileName, OutputStream bOut) throws IOException {  
        File file = new File(fileName);// 读入文件  
        img = ImageIO.read(file);      // 构造Image对象  
        width = img.getWidth(null);    // 得到源图宽  
        height = img.getHeight(null);  // 得到源图长  
        this.out = bOut;
    }  

    /** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     * @throws SQLException 
     */  
    public void resizeFix(int w, int h) throws IOException, SQLException {  
        if (width*1.0 / height > w*1.0 / h) {  
            resizeByWidth(w);  
        } else {  
            resizeByHeight(h);  
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     * @throws SQLException 
     */  
    public void resizeByWidth(int w) throws IOException, SQLException {  
        int h = (int) (height * w / width);  
        resize(w, h);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     * @throws SQLException 
     */  
    public void resizeByHeight(int h) throws IOException, SQLException {  
        int w = (int) (width * h / height);  
        resize(w, h);  
    }  
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param w int 新宽度 
     * @param h int 新高度 
     * @throws SQLException 
     */  
    public void resize(int w, int h) throws IOException, SQLException {  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
        // 可以正常实现bmp、png、gif转jpg  
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        //encoder.encode(image); // JPEG编码  
        ImageIO.write(image, formatName, out);
        out.close();  
    }  
}