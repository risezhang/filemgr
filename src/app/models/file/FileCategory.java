	package models.file;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

/**
 * 文件类型
 * 
 * @author liang
 *
 */
@Entity
public class FileCategory extends Model {
	
	public static final long serialVersionUID = 1L;

	// 名称
	@Column(unique=true, updatable=false)
	public String name;

	// 上传时间
	@Column(updatable = false)
	public Date createTime = new Date();

	/**
	 * 根据名称，查找文件类型
	 * @param name
	 * @return
	 */
	public static FileCategory findByName(String name) {
		return find("name = ?", name).first();
	}
	
}
