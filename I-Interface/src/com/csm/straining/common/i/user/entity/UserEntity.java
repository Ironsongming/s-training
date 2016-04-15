package com.csm.straining.common.i.user.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chensongming
 */
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 7605965513493190066L;
	
	// 数据库基本字段
	private long userID;
	private String username;
	private String signNature;
	private String phone;
	private List<String> tags = new ArrayList<String>();
	private String location;
	private String occupation;
	private String avatar;
	private byte status;
	
	// 业务字段
	private int rank;
	private int score;
	
	
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSignNature() {
		return signNature;
	}
	public void setSignNature(String signNature) {
		this.signNature = signNature;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
	
}
