package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.file.File;
import models.file.FileCategory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.mvc.Controller;

public class Files extends Controller {

	/**
	 * 类型列表 / 文件列表
	 * @param name
	 */
    public static void list(String name) {
    	// 查找name下的files
    	if (StringUtils.isNotBlank(name)) {
    		FileCategory category = FileCategory.findByName(name);
    		if (category == null) {
    			category = new FileCategory();
    			category.name = name;
    			category.save();
    		}
    		
    		List<models.file.File> files = models.file.File.findByType(category);
            render(category, files);
    	} else {
    		// 查找所有的type
    		List<FileCategory> categories = FileCategory.all().fetch();
    		render(categories);
    	}
    }
    
    /**
     * 上传页面 / 提交上传
     * @param name
     * @param fileName 从表单 / file.getName / url 获取
     * @param file
     */
    public static void upload(String name, String fileName, String comment, java.io.File file, String url) {
    	if (StringUtils.equals(request.method, "POST")) {
    		
    		validation.required(name).message("Please enter the name.");
    		
    		// 从url下载文件
    		if (file == null && StringUtils.isNotBlank(url)) {
    			try {
    				URL u = new URL(url);
    				if (StringUtils.isBlank(fileName)) {
    					fileName = url.substring(url.lastIndexOf("/") +1);
    				}
    				Logger.info("path: %s", u.getFile());
					file = java.io.File.createTempFile(String.format("up_%S", System.currentTimeMillis()), "tmp");
					FileUtils.copyURLToFile(u, file);
				} catch (IOException e) {
					e.printStackTrace();
					error(500, e.getMessage());
				}
    		}
    		
    		validation.required(file).message("Please pick up a file.");
    		if (validation.hasErrors()) {
    			render();
    		}
    		
    		if (StringUtils.isBlank(fileName)) {
    			fileName = file.getName();
    		}
    		
    		FileCategory category = FileCategory.findByName(name);
    		if (category == null) {
    			category = new FileCategory();
    			category.name = name;
    			category.save();
    		}
    		
    		// 转移文件至data目录
    		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
    		String outputPath = String.format("data/%s/%s/", name, sdf.format(new Date()), fileName);
    		
    		InputStream is =null;
    		OutputStream os = null;
			try {
				new java.io.File(outputPath).mkdirs();
				outputPath = outputPath + fileName;
				is = new FileInputStream(file);
				os = new FileOutputStream(new java.io.File(outputPath));
	    		IOUtils.copy(is, os);
			} catch (IOException e) {
				error(500, e.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);
			}
    		 
    		models.file.File fileModel = new models.file.File();
    		fileModel.category = category;
    		fileModel.path = outputPath;
    		fileModel.size = file.length();
    		fileModel.comment = comment;
    		fileModel.revision = File.countRevision(category) + 1l;
    		fileModel.save();
    		
    		list(name);
    	} else {
    		render();
    	}
    }
    
    /**
     * 下载指定ID/最新的资源
     * @param id
     */
    public static void download(Long revision, String name) {
    	FileCategory type = FileCategory.findByName(name);
		if (type == null) {
			error(404, "resource not found!");
		}
    	
		models.file.File file = null;
    	if (revision != null) {
    		file = models.file.File.findByCategoryAndRevision(revision, name);
    	} else {
    		file = models.file.File.findLatestByType(name);
    	}
    	
    	renderBinary(new java.io.File(file.path));
    }

}