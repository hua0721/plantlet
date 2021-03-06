package cn.hua.action;

import cn.hua.model.BreviaryPicture;
import cn.hua.model.Explain;
import cn.hua.model.Goods;
import cn.hua.model.GoodsPicture;
import cn.hua.model.Photo;
import cn.hua.model.User;
import cn.hua.service.Service;
import cn.hua.utils.Conversion;
import cn.hua.utils.FileOperation;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.jsp.JspPage;

public class FileAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String message;
	private Service service;
	private String id;
	private ByteArrayInputStream inputStream;
	private File[] htmlfile;
	private String[] htmlfileFileName;
	private String[] htmlfileContentType;
	private int isBreviary;//1为商品缩略图，2为用户头像
	private String sourcePicId;
	private int x,y,width,height;	//用于裁剪图片
	private static Properties properties=new Properties();
	static{
		try {
			properties.load(FileAction.class.getClassLoader().getResourceAsStream("path.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setSourcePicId(String sourcePicId) {
		this.sourcePicId = sourcePicId;
	}

	public void setIsBreviary(int isBreviary) {
		this.isBreviary = isBreviary;
	}

	public File[] getHtmlfile() {
		return htmlfile;
	}

	public void setHtmlfile(File[] htmlfile) {
		this.htmlfile = htmlfile;
	}

	public String[] getHtmlfileFileName() {
		return htmlfileFileName;
	}

	public void setHtmlfileFileName(String[] htmlfileFileName) {
		this.htmlfileFileName = htmlfileFileName;
	}

	public String[] getHtmlfileContentType() {
		return htmlfileContentType;
	}

	public void setHtmlfileContentType(String[] htmlfileContentType) {
		this.htmlfileContentType = htmlfileContentType;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String downloadImg() {

		return SUCCESS;
	}
	//上传聊天时的图片
	public String uploadChatPic(){
		String path = "";
		try {
			String[] fileSuffix = new String[] { ".jpg", ".gif", ".png" };
			boolean isPass = false;
			for (String fileSuffixName : fileSuffix) {
				if (fileFileName.endsWith(fileSuffixName)) {
					isPass = true;
					break;
				}
			}
			if (!isPass) {
				message = Conversion.stringToJson("message,false,cause,上传非法格式文件");
				return SUCCESS;
			}
			String uuid = UUID.randomUUID().toString();
			FileInputStream inputStream = new FileInputStream(file);
			path = properties.getProperty("chatPicture")+"/" + uuid;
			FileOutputStream outputStream = new FileOutputStream(path);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}
			inputStream.close();
			// outputStream.flush();
			outputStream.close();
			this.message = Conversion.stringToJson("message,true,id,"+uuid);
		} catch (Exception e) {
			e.printStackTrace();
			new FileOperation(path).start(); // 启用线程删除文件
			message = Conversion.stringToJson("message,false,cause,文件系统出错");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	//下载聊天时的图片
	public String downloadChatPic() {
		try {
			if (id != null) {
				String path = properties.getProperty("chatPicture")+"/"+id;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				InputStream input = new BufferedInputStream(
						new FileInputStream(path));
				byte[] bt = new byte[1024];
				while (input.read(bt) > 0) {
					bos.write(bt);
				}
				this.inputStream = new ByteArrayInputStream(bos.toByteArray());
				bos.close();
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "download";
	}

	// 上传
	public String upload() throws Exception {
		if (id == null) {
			message = Conversion.stringToJson("cause,没有获取到商品基本信息");
			return SUCCESS;
		}
		Goods goods = service.findGoodsById(id);
		if (goods == null) {
			message = Conversion.stringToJson("cause,没有获取到商品基本信息");
			return SUCCESS;
		}
		String path = "";
		try {
			String[] fileSuffix = new String[] { ".jpg", ".gif", ".png" };
			boolean isPass = false;
			for (String fileSuffixName : fileSuffix) {
				if (fileFileName.endsWith(fileSuffixName)) {
					isPass = true;
					break;
				}
			}
			if (!isPass) {
				message = Conversion.stringToJson("cause,上传非法格式文件");
				return SUCCESS;
			}
			FileInputStream inputStream = new FileInputStream(file);
			path = getDir(properties.getProperty("goodsPicture"), null) + "/" + UUID.randomUUID();
			FileOutputStream outputStream = new FileOutputStream(path);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}
			inputStream.close();
			// outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			message = Conversion.stringToJson("cause,文件系统出错");
			return SUCCESS;
		}
		try {
			if (isBreviary == 1) {
				String breviaryPictureId = null;
				if (goods.getBreviaryPicture() != null) {
					breviaryPictureId = goods.getBreviaryPicture().getId();
					new FileOperation(goods.getBreviaryPicture().getPath())
							.start(); // 启用线程删除文件
					/*
					 * service.deleteGoodsPicture(goods.getGoodsId(),
					 * goods.getBreviaryPicture().getId());
					 */
				}
				goods.setBreviaryPicture(new BreviaryPicture(null,path));
				service.updateGoods(goods);
				if(breviaryPictureId!=null){
					service.deleteGoodsBreviaryPicture(breviaryPictureId);
				}
				this.message = Conversion.stringToJson("message,true,id,"
						+ goods.getBreviaryPicture().getId());
			} else {
				if (sourcePicId != null) {
					GoodsPicture goodsPic = service
							.getGoodsPicture(sourcePicId);
					if (goodsPic != null) {
						service.deleteGoodsPicture(goods.getGoodsId(),
								goodsPic.getId());
						new FileOperation(goodsPic.getPath())
								.start();
					}
				}
				GoodsPicture goodsPicture = new GoodsPicture(path, goods);
				service.SaveGoodsPicture(goodsPicture);
				this.message = Conversion.stringToJson("message,true,id,"
						+ goodsPicture.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new FileOperation(path).start(); // 启用线程删除文件
			message = Conversion.stringToJson("cause,服务器异常");
			return SUCCESS;

		}
		return SUCCESS;
	}
	// 上传头像
	public String uploadHeadPhoto() throws Exception {
		String path = "";
		FileInputStream inputStream=null;
		ImageInputStream iis=null;
		String sourcePath=null;
		User user = (User)ActionContext.getContext().getSession().get("user");
		if(user==null)return INPUT;
		user = service.findUserById(user.getId());
		if(user==null)return INPUT;
		try {
			String[] fileSuffix = new String[] { ".jpg", ".gif", ".png" };
			boolean isPass = false;
			String suffix = null;
			for (String fileSuffixName : fileSuffix) {
				if (fileFileName.endsWith(fileSuffixName)) {
					isPass = true;
					suffix = fileSuffixName.substring(1);
					break;
				}
			}
			if (!isPass) {
				message = Conversion.stringToJson("cause,上传非法格式文件");
				return SUCCESS;
			}
			inputStream = new FileInputStream(file);
			String pid=UUID.randomUUID()+"";
			path = getDir(properties.getProperty("headPhoto"), null) + "/" + pid;
			//FileOutputStream outputStream = new FileOutputStream(path);
			Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(suffix);
			ImageReader imageReader = iterator.next();
			iis = ImageIO.createImageInputStream(inputStream);
			imageReader.setInput(iis,true);
			ImageReadParam param = imageReader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x,y,width,height);
			param.setSourceRegion(rect);
			BufferedImage bufferedImage = imageReader.read(0,param);
			ImageIO.write(bufferedImage, suffix, new File(path));
			if(user.getPhoto()!=null)sourcePath = user.getPhoto().getPath();
			user.setPhoto(new Photo(path));
			service.updateUser(user);
			ActionContext.getContext().getSession().put("user", user);
			this.message=Conversion.stringToJson("message,true,id,"+user.getPhoto().getId());
			if(sourcePath!=null)new FileOperation(sourcePath);
		} catch (Exception e) {
			e.printStackTrace();
			message = Conversion.stringToJson("cause,文件系统出错");
			new FileOperation(path).start(); // 启用线程删除文件
			return SUCCESS;
		}finally{
			if(inputStream!=null)inputStream.close();
			if(iis!=null)iis.close();
		}
		return SUCCESS;
	}

	public String download() {
		try {
			if (id != null) {
				String path = "";
				if (isBreviary == 1) {
					BreviaryPicture breviaryPicture = service
							.getGoodsBreviaryPicture(id);
					if (breviaryPicture != null) {
						path = breviaryPicture.getPath();
					}
				} else if(isBreviary==2){
					User user = (User)ActionContext.getContext().getSession().get("user");
					if(user==null)return INPUT;
					path = user.getPhoto().getPath();
				}else{
					GoodsPicture goodsPicture = service.getGoodsPicture(id);
					if (goodsPicture != null) {
						path = goodsPicture.getPath();
					}
				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				InputStream input = new BufferedInputStream(
						new FileInputStream(path));
				if(isBreviary==1){
					//对这类图片进行缩小处理
					Image srcImg  = ImageIO.read(input);//取源图
			        int  width  =  200; //假设要缩小到200点像素
			        int  height =  srcImg.getHeight(null)*200/srcImg.getWidth(null);//按比例，将高度缩减
			        Image image =srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);//缩小
			        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			        Graphics2D g2 = bufferedImage.createGraphics();
			        g2.drawImage(image, 0, 0, width, height, Color.WHITE, null);
			        g2.dispose();
			        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			        encoder.encode(bufferedImage);
				}else{
					byte[] bt = new byte[1024];
					while (input.read(bt) > 0) {
						bos.write(bt);
					}
				}
				this.inputStream = new ByteArrayInputStream(bos.toByteArray());
				bos.close();
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "download";
	}

	// 上传多个文件
	public String uploadHtml() {
		if (id == null) {
			message = Conversion.stringToJson("cause,没有获取到商品基本信息");
			return SUCCESS;
		}
		Goods goods = service.findGoodsById(id);
		if (goods == null) {
			message = Conversion.stringToJson("cause,没有获取到商品基本信息");
			return SUCCESS;
		}
		if (htmlfile != null) {
			String[] fileSuffix = new String[] { ".jpg", ".gif", ".png",
					".css",".html" };
			for (String htmlfileName : htmlfileFileName) {
				boolean isPass = false;
				for (String fileSuffixName : fileSuffix) {
					if (htmlfileName.endsWith(fileSuffixName)) {
						isPass = true;
						break;
					}
				}
				if (!isPass) {
					message = Conversion.stringToJson("cause,上传非法格式文件");
					return SUCCESS;
				}
			}
			// 如果没有目录建立目录
			String filePath = getDir(properties.getProperty("goodsHtml"), "uuid") + "/";
			try {
				InputStream inputStream = null;
				OutputStream outputStream = null;
				for (int i = 0; i < htmlfile.length; i++) {
					inputStream = new FileInputStream(htmlfile[i]);
					outputStream = new FileOutputStream(filePath
							+ htmlfileFileName[i]);
					byte buffer[] = new byte[1024];
					int count = 0;
					while ((count = inputStream.read(buffer)) > 0) {
						outputStream.write(buffer, 0, count);
					}
					outputStream.flush();
					if (outputStream != null)
						outputStream.close();
					if (inputStream != null)
						inputStream.close();
				}
				// 此处htmlfile是服务器映射的地址
				filePath = filePath.replace(properties.getProperty("goodsHtml"), "htmlfile")
						+ "index.html";
				Explain explain = new Explain(filePath);
				service.saveGoodsExplain(explain);
				goods.setExplain(explain);
				service.updateGoods(goods);
				this.message = Conversion.stringToJson("message,true,path,"
						+ filePath + ",id," + explain.getId());
			} catch (Exception e) {
				e.printStackTrace();
				new FileOperation(filePath).start(); // 启用线程删除文件
				this.message = Conversion.stringToJson("cause,服务器异常");
			}
		}
		return SUCCESS;
	}

	public String getDir(String path, String uuid) {
		Long currentTime = System.currentTimeMillis();
		int hashcode = currentTime.hashCode();
		int dir1 = hashcode & 0xf;
		int dir2 = (hashcode >> 4) & 0xf;
		String finalPath;
		if (uuid != null)
			finalPath = path + "/" + dir1 + "/" + dir2 + "/"
					+ UUID.randomUUID();
		else
			finalPath = path + "/" + dir1 + "/" + dir2;
		File file = new File(finalPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return finalPath;
	}
}
