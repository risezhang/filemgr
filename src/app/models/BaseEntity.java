package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.joda.time.DateTime;

import play.db.jpa.Model;

@MappedSuperclass
public abstract class BaseEntity extends Model {

	private static final long serialVersionUID = 1L;

	@Column(updatable = false)
	private Date createTime;
	private Date updateTime;

	public DateTime getCreateTime() {
		return new DateTime(createTime);
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime.toDate();
	}

	public DateTime getUpdateTime() {
		return new DateTime(updateTime);
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime.toDate();
	}

}
