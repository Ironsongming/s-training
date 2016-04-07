package com.csm.straining.pic.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.util.ImageUtil;
import com.csm.straining.pic.resp.UploadResp;



/**
 * @author chensongming
 */
public class PicService {
	
	private static final Logger logger = LoggerFactory.getLogger(PicService.class);
	
	public synchronized static UploadResp save(InputStream is, String suffix) throws AppException, CoreException {
		
		UploadResp resp = new UploadResp();
		
		if (is == null) {
			throw new AppException("上存图片失败");
		}
		
		try {
			BufferedImage buff = ImageIO.read(is);
			suffix = suffix.toLowerCase();
			String fid = getFID();
			logger.debug("[fid]: " + fid);
			File f = new File(ImageUtil.getImgPath(), fid + "." + suffix);
			
			boolean res = ImageIO.write(buff, suffix, f);
			
			if (res) {
				resp.url =  ImageUtil.getLoadPath(fid + "." + suffix);
			} else {
				throw new AppException("上存图片失败");
			}
			
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new CoreException(CoreException.UPLOAD, e.getMessage());
		}
		
		return resp;
	}
	
	public synchronized static UploadResp save2(InputStream is, String suffix) {
		
		UploadResp resp = new UploadResp();
		
		if (is == null) {
			return null;
		}
		
		suffix = suffix.toLowerCase();
		String fid = getFID();
		
		FileOutputStream fos = null;
		try {
			File f = new File(ImageUtil.getImgPath(), fid + "." + suffix);
	 		fos = new FileOutputStream(f);
			
			byte[] buff = new byte[1024];
			int n = 0;
			
			while((n = is.read(buff)) != -1) {
				fos.write(buff, 0, n);
			}
			
			resp.url = ImageUtil.getLoadPath(fid + "." + suffix);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				
			}
		}
		
		return resp;
 		
	}
	
	private static String getFID() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + getNonceStr();
	}
	
	private static String getNonceStr() {
		
		String src = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < 20; i++) {
			res.append(src.charAt(new Random().nextInt(src.length() - 1)));
		}
		
		return res.toString();
		
	}
	
	public static void load(String fileName, OutputStream os) throws Exception {
		
		BufferedInputStream is = null;
		ByteArrayOutputStream baos = null;
		
		try {
			File f = new File(ImageUtil.getImgPath(), fileName);
			
			is = new BufferedInputStream(new FileInputStream(f));
			baos = new ByteArrayOutputStream();
			
			byte[] buff = new byte[1024];
			int n = 0;
			while ((n = is.read(buff)) != -1) {
				baos.write(buff, 0, n);
			}
			
			is.close();
			os.write(baos.toByteArray());
		} catch (Exception e) {
			logger.debug("load pic error", e);
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		
	}

	

}
