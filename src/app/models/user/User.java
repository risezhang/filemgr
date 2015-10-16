package models.user;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.Required;
import models.DomainEntity;

@Entity
public class User extends DomainEntity {
	
	@Required(message="登陆 is required.")
	public String loginName;
	public String password;
	@Required(message="Name is required.")
	public String displayName;
	public boolean isDisabled;
	public int loginCount;
	public Date lastLoginTime;
	
	@Override
	public String toString() {
		return this.displayName;
	}

}
