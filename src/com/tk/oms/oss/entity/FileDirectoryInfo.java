package com.tk.oms.oss.entity;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 运营总后台文件目录管理实体类
 * 目录管理数据表，该变用于定义通用商品图片的目录结构模板
 * 对应数据表：TKVIP_NEW.TBL_FILE_DIRECTORY_INFO
 * @author songwangwen
 * @date  2017-5-14  下午3:40:26
 */
public class FileDirectoryInfo {
	//运营总后台，【文件图片】目录模板记录ID
	public static final long PRODUCT_IMG_ID = 1L;
	//运营总后台，【其他图片】目录模板记录ID
	public static final long OTHER_IMG_ID = 2L;
	
	private  long id;//主键信息;
	private  String directory_name ;//文件夹名称;
	private  String remark ;//备注信息;
	private  String file_content_type ;//文件夹内容，1：（当前文件夹可以存储文件以及文件夹）  2：（仅允许存储文件夹）;
	private  String file_type_ids ;//允许存储的文件类型，可选值来自表tbl_dic_file_type的file_type_code字段;
	private  String picture_file_formats ;//文件格式,，多个则以逗号分隔，可选值来自表tbl_dic_file_type的file_extension字段;
	private  String video_file_formats ;//文件格式,，多个则以逗号分隔，可选值来自表tbl_dic_file_type的file_extension字段;
	private  long picture_size_limit ;//当前文件夹中，单个文件的大小限制，存储单位为b（1gb=1024mb；1mg = 1024kb；1kb=1024b）;
	private  long picture_height ;//图片固定尺寸高度;
	private  long picture_width ;//图片固定尺寸宽度;
	private  long video_size_limit ;//当前文件夹中，单个文件的大小限制，存储单位为b（1gb=1024mb；1mg = 1024kb；1kb=1024b）;
	private  long file_quantity_limit ;//文件数量上限,当前文件夹中最多允许存放的文件个数;
	private  String is_share ;//当前文件夹内容是否分享给分销商，1：不分享   2：分享;
	private  long parent_id ;//父节点id,无父节点则为0;
	private  Date create_date ;//创建时间;
	private  long ctrate_user_id ;//创建人id;
	
	private boolean isParent;// 是否拥有子文件夹 true:有子文文件夹 false 无子文文件夹
	private boolean isSearch;//是否是搜索包含关键字的文件夹  true:是   false:否
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDirectory_name() {
		return directory_name;
	}
	public void setDirectory_name(String directory_name) {
		this.directory_name = directory_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFile_content_type() {
		return file_content_type;
	}
	public void setFile_content_type(String file_content_type) {
		this.file_content_type = file_content_type;
	}
	public String getFile_type_ids() {
		return file_type_ids;
	}
	public void setFile_type_ids(String file_type_ids) {
		this.file_type_ids = file_type_ids;
	}
	public String getPicture_file_formats() {
		return picture_file_formats;
	}
	public void setPicture_file_formats(String picture_file_formats) {
		this.picture_file_formats = picture_file_formats;
	}
	public String getVideo_file_formats() {
		return video_file_formats;
	}
	public void setVideo_file_formats(String video_file_formats) {
		this.video_file_formats = video_file_formats;
	}
	public long getPicture_size_limit() {
		return picture_size_limit;
	}
	public void setPicture_size_limit(long picture_size_limit) {
		this.picture_size_limit = picture_size_limit;
	}
	public long getPicture_height() {
		return picture_height;
	}
	public void setPicture_height(long picture_height) {
		this.picture_height = picture_height;
	}
	public long getPicture_width() {
		return picture_width;
	}
	public void setPicture_width(long picture_width) {
		this.picture_width = picture_width;
	}
	public long getVideo_size_limit() {
		return video_size_limit;
	}
	public void setVideo_size_limit(long video_size_limit) {
		this.video_size_limit = video_size_limit;
	}
	public long getFile_quantity_limit() {
		return file_quantity_limit;
	}
	public void setFile_quantity_limit(long file_quantity_limit) {
		this.file_quantity_limit = file_quantity_limit;
	}
	public String getIs_share() {
		return is_share;
	}
	public void setIs_share(String is_share) {
		this.is_share = is_share;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public long getCtrate_user_id() {
		return ctrate_user_id;
	}
	public void setCtrate_user_id(long ctrate_user_id) {
		this.ctrate_user_id = ctrate_user_id;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean isSearch() {
		return isSearch;
	}
	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
}