package com.tk.oms.oss.entity;

import org.springframework.util.StringUtils;

public class OssFile {

//	private String bucketName = "tkvip61"; // 资源包名称
	private String bucketName; // 资源包名称
	private String prefix; // 限定返回的Object key必须以prefix作为前缀。可以作为指定目录
	private String filename; // 文件名称
	private String content; // 文件内容
	private String original_prefix;	//旧的数据的文件目录，传值用于删除旧的数据
	private String original_filename; //旧的数据的文件名，传值用于删除旧的数据
	private String user_name; //用户名
    private int type; //1、通用省市县数据JS文件（树形层级关系格式） 2、通用省市县数据JSON文件（无层级关系数据
	public String getBucketName() {
		return bucketName;
	}

	public String getPrefix() {
		if(!StringUtils.isEmpty(prefix)&&!prefix.endsWith("/"))
			prefix = prefix+"/";
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOriginal_prefix() {
		return original_prefix;
	}

	public void setOriginal_prefix(String original_prefix) {
		this.original_prefix = original_prefix;
	}

	public String getOriginal_filename() {
		return original_filename;
	}
	public void setOriginal_filename(String original_filename) {
		this.original_filename = original_filename;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}