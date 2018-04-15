package com.system.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;



public class ImageTool{
	private BufferedImage bufferedImage;
	
	/**
	 * 通过十六进制字符串构造图片
	 * @param hexString
	 */
	public ImageTool( String hexString ){
		ByteArrayInputStream in = null;
		try{
			byte[] result = CharConvertTool.convertHexStringToByte( hexString );              //将十六进制字符串转为字节码
		    in = new ByteArrayInputStream( result );
	    	this.bufferedImage = ImageIO.read(in);                          //通过字节流构造图片
	    } catch (Exception e) {
	      e.printStackTrace();
	      this.bufferedImage = null;
	    }finally{
	    	try {
	    		if(null != in){
	    			in.close();
	    		}
				System.out.println("IO IS CLOSE!!!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	  }
	/**
	 * 从流中读取照片信息并返回字节数组
	 * @param request
	 * @return
	 */
	public static byte[] getPhotoByteFromStream(HttpServletRequest request){
		byte[] photoByte = null;
		InputStream in = null;
		ByteArrayOutputStream ba = null;
		try{
			in = request.getInputStream();
			ba = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1){
				ba.write(ch);
			}
			ba.flush();
			photoByte = ba.toByteArray();
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(null != in){
					in.close();
				}
				if(null != ba){
					ba.close();
				}
				System.out.println("IO IS CLOSE!!!!");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return photoByte;
	}
	
	public ImageTool(byte[] bytes){
		ByteArrayInputStream in = null;
		try{
			in = new ByteArrayInputStream(bytes);
	    	this.bufferedImage = ImageIO.read(in);                          //通过字节流构造图片
		} catch (Exception e) {
		      e.printStackTrace();
		      this.bufferedImage = null;
		}finally{
		   try {
			  if(null != in){
				  in.close();
			  }
			  System.out.println("IO IS CLOSE!!!!");
		   } catch (IOException e) {
			  e.printStackTrace();
		   }
		}
	}
	
	/**
	 * 按比例缩放图像
	 * @param percentOfOriginal
	 */
	public void resize(int percentOfOriginal){
	    int newWidth = this.bufferedImage.getWidth() * percentOfOriginal / 100;
	    int newHeight = this.bufferedImage.getHeight() * percentOfOriginal / 100;
	    resize(newWidth, newHeight);
	  }
	  
	/**
	 * 	根据长度和宽度缩放图像
	 * @param newWidth
	 * @param newHeight
	 */
	 public void resize(int newWidth, int newHeight){
	    int oldWidth = this.bufferedImage.getWidth();             //获得新图像的长和宽
	    int oldHeight = this.bufferedImage.getHeight();

	    if ((newWidth == -1) || (newHeight == -1)) {
	      if (newWidth == -1) {
	        if (newHeight == -1) {
	          return;
	        }
	        newWidth = newHeight * oldWidth / oldHeight;            
	      }
	      else {
	        newHeight = newWidth * oldHeight / oldWidth;
	      }
	    }

	    BufferedImage result = new BufferedImage(newWidth, newHeight, 4);

	    int widthSkip = oldWidth / newWidth;
	    int heightSkip = oldHeight / newHeight;

	    if (widthSkip == 0) widthSkip = 1;
	    if (heightSkip == 0) heightSkip = 1;

	    for (int x = 0; x < oldWidth; x += widthSkip) {
	      for (int y = 0; y < oldHeight; y += heightSkip) {
	        int rgb = this.bufferedImage.getRGB(x, y);

	        if ((x / widthSkip < newWidth) && (y / heightSkip < newHeight)) {
	          result.setRGB(x / widthSkip, y / heightSkip, rgb);
	        }
	      }

	    }

	    this.bufferedImage = result;
	  }
	
	  public BufferedImage getBufferedImage() {
			return bufferedImage;
		}
	  /**
	   * 图片转字节码
	   * @param photoPath 图片存放路径
	   */
	  public static byte[] photoToByte(String photoPath){
		  byte[] b=null;
		  FileInputStream fis = null; 
		  try {
			  File file=new File(photoPath);
			  if(file!=null){
				  fis=new FileInputStream (file);
				  if(fis!=null){
					  int len=fis.available();
					  b=new byte[len];
					  fis.read(b);//file中的内容全读到byte[]数组中
				  }
			  }
		  } catch (Exception e) {
			  e.printStackTrace();
		  }finally{
			try {
				if(null != fis){
					fis.close();
				}
				System.out.println("IO IS CLOSE!!!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
		 return b;
	  }
	  
	  
	  
	  /**
	   * 将图片存储到指定路径，供测试用
	   * @param outputPath
	   * @param bi
	   * @throws ImageFormatException
	   * @throws IOException
	   */
	  public static void storePhoto(String outputPath,BufferedImage bi){
	    FileOutputStream out = null;
		try {
		  out = new FileOutputStream(outputPath); 
		  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		  encoder.encode(bi); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		  try {
			if(out != null){
				out.close();
			}
			System.out.println("IO IS CLOSE");
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		}
	  }
	  /*测试*/
	  /*public static void main(String[] args) throws IOException
	  {
			String photoPath="D://test.jpg";
			byte[] b=photoToByte(photoPath);
			String hex=CharConvertTool.convertByteToHexString(b);
			MyDebug.info(hex);
			ImageTool it=new ImageTool(hex);
			BufferedImage   bi=it.getBufferedImage(); 
			storePhoto("D://Java//package//t.jpg",bi);
	  }*/
	  
	  public String saveAsHex(){
		  ByteArrayOutputStream out = new ByteArrayOutputStream();
		  try {
			ImageIO.write(this.getBufferedImage(), "jpeg", out);
			byte[] result = out.toByteArray();
			String hexString = CharConvertTool.convertByteToHexString(result);
			return hexString;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(out != null){
					out.close();
				}
				System.out.println("IO IS CLOSE");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
	  }
	  
	/**
	 * 从流中读取照片信息并返回字节数组
	 * @param request
	 * @return
	 */
	public static byte[] getPhotoHexStrFromStream(HttpServletRequest request){
		byte[] photoByte = null;
		InputStream in = null;
		ByteArrayOutputStream ba = null;
		try
		{
			in = request.getInputStream();
			ba = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1)
			{
				ba.write(ch);
			}
			ba.flush();
			photoByte = ba.toByteArray();
			ba.close();
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(in != null){
					in.close();
				}
				if(ba != null){
					ba.close();
				}
				System.out.println("IO IS CLOSE");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return photoByte;
	}
	  
	  
	  /**
		 * 从流中读取照片信息并返回字节数组
		 * @param request
		 * @return
		 */
		public static byte[] getByteFromStream(HttpServletRequest request){
			byte[] photoByte = null;
			InputStream in = null;
			ByteArrayOutputStream ba = null;
			try
			{

			    in = request.getInputStream();
			    ba = new ByteArrayOutputStream();
				int ch;
				while ((ch = in.read()) != -1)
				{
					ba.write(ch);
				}
				ba.flush();
				photoByte = ba.toByteArray();
			} catch (Exception e){
				e.printStackTrace();
			}finally{
				try {
					if(in != null){
						in.close();
					}
					if(ba != null){
						ba.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return photoByte;
		}
		  /**
		   * 返回图片的byte数组数据
		   * @return
		   */
		  public byte[] saveAsBytes(){
			  ByteArrayOutputStream out = null;
			  try {
				out = new ByteArrayOutputStream();
				ImageIO.write(this.getBufferedImage(), "jpeg", out);
				byte[] result = out.toByteArray();
				out.close();
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}finally{
				try {
					if(out != null){
						out.close();
					}
					System.out.println("IO IS CLOSE");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			 
		  }
		  /**
		   * 返回文件的byte数组数据
		   * @return
		   */
		  public static byte[] getByteFromFile(File f){
				byte[] photoByte = null;
				InputStream in = null;
				try
				{
					in = new FileInputStream(f);
					int length = (int)f.length();
					photoByte = new byte[length];
					int offset = 0;
					int numRead = 0;
					while(offset<photoByte.length && (numRead=in.read(photoByte,offset,photoByte.length-offset))>0){
						offset += numRead;
					}					
				} catch (Exception e){
					e.printStackTrace();
				}finally{
					try {
						if(in != null){
							in.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return photoByte;
			}	
		  
	  /** *//**
	     * 给图片添加水印
	     * 
	     * @param file
	     *            需要添加水印的图片
	     * @param markContent
	     *            水印的文字
	     * @param markContentColor
	     *            水印文字的颜色
	     * @param qualNum
	     *            图片质量
	     * @return
	     */
	    public static boolean createMark(File file, String markContent,
	            Color markContentColor, float qualNum) {
	    	FileOutputStream out = null;
	        try {
	        	Image theImg = ImageIO.read(file);
	        	int width = theImg.getWidth(null);
	        	int height = theImg.getHeight(null);
	        	BufferedImage bimage = new BufferedImage(width, height,
	        			BufferedImage.TYPE_INT_RGB);
	        	Graphics2D g = bimage.createGraphics();
	        	g.setColor(markContentColor);
	        	g.setBackground(Color.white);
	        	g.drawImage(theImg, 0, 0, null);
	        	g.drawString(markContent, 0, width/30); // 添加水印的文字和设置水印文字出现的内容
	        	g.dispose();
	            out = new FileOutputStream(file);
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
	            param.setQuality(qualNum, true);
	            encoder.encode(bimage, param);
	        } catch (Exception e) {
	            return false;
	        }finally{
	        	try {
	        		if(out != null){
	        			out.close();
	        		}
					System.out.println("IO IS CLOSE");
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        return true;
	    }
	    
	    /**
	     * 压缩图片
	     * 
	     * @param file
	     * @return
	     * @throws IOException
	     */
		public static byte[] compressPic(File file){
			byte[] b =  null;
			ByteArrayOutputStream out = null;
			try {
				Image img = ImageIO.read(file);
				int outputWidth = 1024; // 默认输出图片宽
				int outputHeight = 800; // 默认输出图片高
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null)) / (double) outputWidth
						+ 0.1;
				double rate2 = ((double) img.getHeight(null)) / (double) outputHeight
						+ 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight,
						BufferedImage.TYPE_INT_RGB);
				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST),
						0, 0, null);
				out = new ByteArrayOutputStream();
				ImageIO.write(tag, "jpg", out);
				b = out.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(out != null){
						out.close();
					}
					System.out.println("IO IS CLOSE");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return b;
		}
		
		/**
		   * 返回文件的byte数组数据
		   * @return
		   */
		public static byte[] getPhotoByteFromFile(File f){
			byte[] photoByte = null;
			InputStream in = null;
			try
			{
				in = new FileInputStream(f);
//					ByteArrayOutputStream ba = new ByteArrayOutputStream();
//					int ch;
//					while ((ch = in.read()) != -1)
//					{
//						ba.write(ch);
//					}
//					ba.flush();
//					photoByte = ba.toByteArray();
//					in.close();
//					ba.close();
				int length = (int)f.length();
				photoByte = new byte[length];
				int offset = 0;
				int numRead = 0;
				while(offset<photoByte.length && (numRead=in.read(photoByte,offset,photoByte.length-offset))>0){
					offset += numRead;
				}
			} catch (Exception e){
				e.printStackTrace();
			}finally{
				try {
					if(in != null){
						in.close();
					}
					System.out.println("IO IS CLOSE");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return photoByte;
		}
		    
}