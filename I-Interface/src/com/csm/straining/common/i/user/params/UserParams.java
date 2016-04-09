package com.csm.straining.common.i.user.params;


/**
 * @author chensongming
 */
public class UserParams {
	
	private long userID;
	private String username;
	private String password;
	private String phone;
	private String signNature;
	private String tags;
	private byte location;
	private byte occupation;
	private String avatar;
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSignNature() {
		return signNature;
	}
	public void setSignNature(String signNature) {
		this.signNature = signNature;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public byte getLocation() {
		return location;
	}
	public void setLocation(byte location) {
		this.location = location;
	}
	public byte getOccupation() {
		return occupation;
	}
	public void setOccupation(byte occupation) {
		this.occupation = occupation;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	

}
