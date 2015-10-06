package models.file;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

/**
 * 文件
 * 
 * @author liang
 *
 */
@Entity
public class File extends Model {

	public static final long serialVersionUID = 1L;

	// 名称
	@ManyToOne(fetch=FetchType.LAZY)
	public FileCategory category;
	
	// 文件名
	public String name;
	
	// 版本号 （对应一个）
	public Long revision;

	// 保存路径
	public String path;

	// 文件大小
	public Long size;
	
	@Column(length=255)
	public String comment;

	// 上传时间
	@Column(updatable = false)
	public Date createTime = new Date();
	
	public static List<File> findByType(FileCategory category) {
		return find("category = ? order by createTime desc", category).fetch();
	}

	public static models.file.File findLatestByType(String name) {
		return find("category.name = ? order by createTime desc", name).first();
	}
	
	public static Long countRevision(FileCategory category) {
		return count("category = ?", category);
	}

	public static File findByCategoryAndRevision(Long revision, String name) {
		return find("revision = ? and category.name = ?", revision, name).first();
	}
}
