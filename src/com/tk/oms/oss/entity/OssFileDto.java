package com.tk.oms.oss.entity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 该类用于将文件实体或文件夹实体转化为可以在界面中展示的实体数据
 * @author songwangwen
 * @date  2017-5-4  下午2:46:16
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class OssFileDto{
    private long id; // 数据库ID记录
    private long did; // 文件夹当前目录ID记录
    private long pid; // 文件夹父目录父节点ID记录
    private String bucketName; // 资源包名称
    private String filename; // 文件名称,不包含后缀
    private String key; // 完整的文件名称,包括文件路径
    private String path; // 完整的访问URL
    private String fileModel; // 文件类型，根据扩展名确定
    private long size; //文件（或文件夹大小）
    private String formatSize; //文件格式化后的大小 KB MB等
    private int fileType;//文件类型，具体参见OssFileEnum
	private String directory_id;//防止id为非数字的情况，使用单独属性存储文件夹的ID信息
	private  Date lastModified;			//创建时间
	private  Date delete_date;			//删除时间
	private  long width;				//图片文件宽度
	private  long height;				//图片文件高度
	private  String keywords;				//关键字信息
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDid() {
		return did;
	}

	public void setDid(long did) {
		this.did = did;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getBucketName() {
        return bucketName;
    }

    public String getFilename() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    public int getFileType() {
        return fileType;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
    public String getPath() {
        return path;
    }

    public String getFileModel() {
        return fileModel;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileModel(String fileModel) {
        this.fileModel = fileModel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDirectory_id() {
		return directory_id;
	}

	public void setDirectory_id(String directory_id) {
		this.directory_id = directory_id;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getDelete_date() {
		return delete_date;
	}

	public Date getLastModified() {
		return lastModified;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public String getFormatSize() {
		//根据文件的实际size格式化
		return formatSize;
	}

	public void setFormatSize(String formatSize) {
		this.formatSize = formatSize;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	
    /**
     * 实体类转换
     * @return
     */
    public static OssFileDto conver(OssDirectory directory){
        OssFileDto of = new OssFileDto();
        of.setId(directory.getId());
        of.setDid(directory.getId());
        of.setPid(directory.getParent_id());
        of.setPath(directory.getDirectory_full_name());
        of.setKey(directory.getDirectory_full_name());
        of.setDirectory_id(directory.getDirectory_id());
        of.setFilename(directory.getDirectory_name());//文件
        of.setFileType(3);//默认为文件夹
        of.setSize(0);
        of.setLastModified(directory.getCreate_date());
        return of;
    }
}