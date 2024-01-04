package org.onetwo.ext.apiclient.wechat.dbm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.onetwo.common.db.AbstractTimeRecordableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Entity
@Table(name="wx_access_token")
@Data
@EqualsAndHashCode(callSuper=false)
public class WxAccessTokenEntity extends AbstractTimeRecordableEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6883914646989577935L;
	@Id
	private String id;
	private String wxAppid;
	private String accessToken;
	private Long expiresIn;

}

