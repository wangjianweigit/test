package com.tk.oms.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class MemberInfo{

	private int id; // 主键ID
	private String user_name; // 用户名,仅用于与其他相关数据表关联
	private String login_name;//登录用户名（只做登录用）


	private String user_pwd; // 密码

	private String user_head_imgurl; // 用户头像
	private String user_manage_name; // 负责人姓名
	private String user_manage_sex; // 负责人性别


	private String user_manage_cardid; // 负责人身份证号码


	private String user_manage_cardid_file1;// 负责人身份证正面


	private String user_manage_cardid_file2;// 负责人身份证反面


	private String user_manage_current_address; // 负责人现居住地


	private String user_manage_telephone; // 负责人电话


	private String user_manage_mobilephone; // 负责人手机


	private String user_manage_weixin; // 负责人微信


	private String user_manage_qq; // 负责人QQ


	private String user_manage_email; // 负责人邮箱


	private String user_company_name; // 公司名称


	private String user_company_corporation; // 公司法人


	private String user_company_telephone; // 公司电话


	private int user_company_type; // 公司经营类型（代码）

	private String user_company_type_name; // 公司经营类型名称


	private int user_company_address_max; // 公司所在大区

	private int user_company_address_province; // 公司所在地省份


	private int user_company_address_city; // 公司所在地城市


	private int user_company_address_county; // 公司所在地区县


	private String user_company_address_deails; // 公司所在地详细地址


	private String user_company_comment; // 公司简介


	private Date user_create_date; // 用户注册时间


	private String user_create_ip; // 用户注册IP

	private int user_login_count; // 用户登录次数
	private Date last_update_time; // 用户数据最终更新时间
	private Date user_last_login_date; // 用户最后登录时间
	private String user_last_login_ip; // 用户最后登录IP
	
	private int apply_state; // 用户审核状态   -1.非注册会员  0.待审核 1.审核通过    3.审核驳回  4.预审通过
	private int user_state; //  用户状态   1.启用    2.禁用    4.预审通过


	private String audit_user_name; // 审核人用户名（关联TBL_SYS_USER_INFO的user_name）


	private String audit_user_realname; // 审核人姓名


	private Date audit_date; // 审核时间

	private long referee_user_id;				//业务员ID
	private long market_supervision_user_id;	//业务经理ID

	private String referee_user_name; // 推荐人用户名（关联TBL_SYS_USER_INFO的user_name）

	
	private String referee_user_realname; // 推荐人姓名

	private String market_supervision_user_name;//市场督导用户名（关联TBL_SYS_USER_INFO的user_name）

	private String market_supervision_user_realna;//市场督导姓名

	//一下字段不存储与数据表中，仅仅用于传值 songwangwen 2016.03.15
	private String user_company_qualified; //公司资质图片地址，多个以逗号分隔

	private String user_register_type;//用户知晓途径id，多个以逗号分隔

	private String openid;	//微信的OPENID

	private int has_paypwd;	//支付密码状态	1，以设置，0，未设置

	private String user_resource;//会员来源，可选值参见

	private int distribution_state; // 用户分销状态  0-默认-非分销， 1-分销

	private int is_temp; //是否业务员临时账户

	private Long issuing_grade_id;	//代发等级

	private Long store_id;     //门店ID

	private Long site_id;     //站点ID
	
	private String site_code;  //站点名称

	private String site_name; //站点名称

	private String store_name;//门店名称

	private String store_code;//门店代码

	private int sale_type;//代下单人类型

	private String create_user_name;//创建用户用户名
	
	private int user_type;//用户登入类型

	private String user_business_licence_imgurl;//营业执照图片地址

	private String business_license_number;//营业执照编号
	
	private String user_policy_period;//会员政策期
	
	private int commission_rate;//佣金比例(只有店铺会员设置佣金比例)
	
	private int partner_user_id;//合作商ID
	
	private String partner_user_realna;//合作商姓名
	
	private String supervisor_user_realna;//督导姓名

	private int supervisor_user_id;//督导ID

	private String shop_website;//店铺网址、
	private String shop_name;//店铺名称、
	private String platform;//所属平台、
	private String main_category;//主营类目、
	private String shop_photo ;//店铺照片、

	//省市县名称
	private String company_province_name;
	private String company_city_name;
	private String company_county_name;

	private int flag=1;//1:校验信息    2：登录并绑定
	private int pre_aprv_allowed_number;//预审会员允许登录次数；每次预审通过后，数量加1
	private int pre_aprv_login_number;//预审会员已经登录次数；每次预审会员登录后，数量加1
	
	private double credit_money;  //用户授信
	private String bank_account;  //银行会员子账户
	//----------------区域控货改造start-------------------
	private double user_longitude;//用户注册时的经度坐标值，作为默认门店的经度坐标
	private double user_latitude;//用户注册时的维度坐标值，作为默认门店的维度坐标
	private String user_company_location_address;//公司定位地址
	private String user_control_store_name;//会员控货门店名称
	private int user_store_address_province;//控货门店所在省id
	private int user_store_address_city;//控货门店所在城市id
	private int user_store_address_county;//控货门店所在区县id
	private String user_store_address_deails;//控货门店所在详细地址
	private String user_store_location_address;//控货门店所在定位地址
	//----------------区域控货改造end-------------------
	

	//----------------分销权限start-------------------
	private String wmall_permit;//  是否开通微商城 (0.未开通，1.已开通)
	private String posshop_permit;//是否开通POS (0.未开通，1.已开通)
	private double service_rate;//经销商服务费率
	//----------------分销权限改造end-------------------
	
	//----------------子账户登录start------------------
	private String child_login_name;//子账户用户名
	private String child_mobilephone;//子账户手机号码
	private String child_user_manage_name;//子账户姓名
	//----------------子账户登录end--------------------
	private int is_sample_user_group;//是否样品用户组 0否，1是

	private String province;
	private String city;
	private String district;
	private String sub_merchant_id;
	
	private int other_sync_state;//是否同步淘宝订单 0否，1是
	
	private int sleep_state ;//'沉睡状态(1-沉睡,2-激活待确认,3-已激活,4-无效激活)';


	/**是否开通会员卡 0 否，1 是*/
	private int is_open_vip;
	/**是否开通会员卡 0 否，1 是*/
	private int expiration_flag ;//会员卡过期标志位 0.未过期   1.已过期

	/**会员卡未过期剩余余额*/
	private BigDecimal card_balance;

	/**会员卡过期时间*/
	private Date expiration_date;

    private long shop_id;

    private String domain_name;

	/** 用户运费模板ID */
    private long user_logistics_template_id;

    /** 用户运费模板名称 */
    private String user_logistics_template_name;

	public String getSub_merchant_id() {
		return sub_merchant_id;
	}

	public void setSub_merchant_id(String sub_merchant_id) {
		this.sub_merchant_id = sub_merchant_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public double getCredit_money() {
		return credit_money;
	}
	public void setCredit_money(double credit_money) {
		this.credit_money = credit_money;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getShop_website() {
		return shop_website;
	}

	public void setShop_website(String shop_website) {
		this.shop_website = shop_website;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getMain_category() {
		return main_category;
	}

	public void setMain_category(String main_category) {
		this.main_category = main_category;
	}

	public String getShop_photo() {
		return shop_photo;
	}

	public void setShop_photo(String shop_photo) {
		this.shop_photo = shop_photo;
	}

	public String getUser_business_licence_imgurl() {
		return user_business_licence_imgurl;
	}

	public void setUser_business_licence_imgurl(String user_business_licence_imgurl) {
		this.user_business_licence_imgurl = user_business_licence_imgurl;
	}
	
	public String getUser_policy_period() {
		return user_policy_period;
	}
	public void setUser_policy_period(String user_policy_period) {
		this.user_policy_period = user_policy_period;
	}

    public String getBusiness_license_number() {
        return business_license_number;
    }

    public void setBusiness_license_number(String business_license_number) {
        this.business_license_number = business_license_number;
    }

    public int getDistribution_state() {
		return distribution_state;
	}

	public void setDistribution_state(int distribution_state) {
		this.distribution_state = distribution_state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_head_imgurl() {
		return user_head_imgurl;
	}

	public void setUser_head_imgurl(String user_head_imgurl) {
		this.user_head_imgurl = user_head_imgurl;
	}

	public String getUser_manage_name() {
		return user_manage_name;
	}

	public void setUser_manage_name(String user_manage_name) {
		this.user_manage_name = user_manage_name;
	}

	public String getUser_manage_sex() {
		return user_manage_sex;
	}

	public void setUser_manage_sex(String user_manage_sex) {
		this.user_manage_sex = user_manage_sex;
	}

	public String getUser_manage_cardid() {
		return user_manage_cardid;
	}

	public void setUser_manage_cardid(String user_manage_cardid) {
		this.user_manage_cardid = user_manage_cardid;
	}

	public String getUser_manage_cardid_file1() {
		return user_manage_cardid_file1;
	}

	public void setUser_manage_cardid_file1(String user_manage_cardid_file1) {
		this.user_manage_cardid_file1 = user_manage_cardid_file1;
	}

	public String getUser_manage_cardid_file2() {
		return user_manage_cardid_file2;
	}

	public void setUser_manage_cardid_file2(String user_manage_cardid_file2) {
		this.user_manage_cardid_file2 = user_manage_cardid_file2;
	}

	public String getUser_manage_current_address() {
		return user_manage_current_address;
	}

	public void setUser_manage_current_address(
			String user_manage_current_address) {
		this.user_manage_current_address = user_manage_current_address;
	}

	public String getUser_manage_telephone() {
		return user_manage_telephone;
	}

	public void setUser_manage_telephone(String user_manage_telephone) {
		this.user_manage_telephone = user_manage_telephone;
	}

	public String getUser_manage_mobilephone() {
		return user_manage_mobilephone;
	}

	public void setUser_manage_mobilephone(String user_manage_mobilephone) {
		this.user_manage_mobilephone = user_manage_mobilephone;
	}

	public String getUser_manage_weixin() {
		return user_manage_weixin;
	}

	public void setUser_manage_weixin(String user_manage_weixin) {
		this.user_manage_weixin = user_manage_weixin;
	}

	public String getUser_manage_qq() {
		return user_manage_qq;
	}

	public void setUser_manage_qq(String user_manage_qq) {
		this.user_manage_qq = user_manage_qq;
	}

	public String getUser_manage_email() {
		return user_manage_email;
	}

	public void setUser_manage_email(String user_manage_email) {
		this.user_manage_email = user_manage_email;
	}

	public String getUser_company_name() {
		return user_company_name;
	}

	public void setUser_company_name(String user_company_name) {
		this.user_company_name = user_company_name;
	}

	public String getUser_company_corporation() {
		return user_company_corporation;
	}

	public void setUser_company_corporation(String user_company_corporation) {
		this.user_company_corporation = user_company_corporation;
	}

	public String getUser_company_telephone() {
		return user_company_telephone;
	}

	public void setUser_company_telephone(String user_company_telephone) {
		this.user_company_telephone = user_company_telephone;
	}

	public String getSite_name () {
		return site_name;
	}

	public void setSite_name (String site_name) {
		this.site_name = site_name;
	}

	public int getUser_company_type() {
		return user_company_type;
	}

	public void setUser_company_type(int user_company_type) {
		this.user_company_type = user_company_type;
	}

	public int getUser_company_address_province() {
		return user_company_address_province;
	}

	public void setUser_company_address_province(
			int user_company_address_province) {
		this.user_company_address_province = user_company_address_province;
	}

	public int getUser_company_address_city() {
		return user_company_address_city;
	}

	public void setUser_company_address_city(int user_company_address_city) {
		this.user_company_address_city = user_company_address_city;
	}

	public int getUser_company_address_county() {
		return user_company_address_county;
	}

	public void setUser_company_address_county(int user_company_address_county) {
		this.user_company_address_county = user_company_address_county;
	}

	public String getUser_company_address_deails() {
		return user_company_address_deails;
	}

	public void setUser_company_address_deails(
			String user_company_address_deails) {
		this.user_company_address_deails = user_company_address_deails;
	}

	public String getUser_company_comment() {
		return user_company_comment;
	}

	public void setUser_company_comment(String user_company_comment) {
		this.user_company_comment = user_company_comment;
	}
	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getUser_create_date() {
		return user_create_date;
	}

	public void setUser_create_date(Date user_create_date) {
		this.user_create_date = user_create_date;
	}

	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getUser_create_ip() {
		return user_create_ip;
	}

	public void setUser_create_ip(String user_create_ip) {
		this.user_create_ip = user_create_ip;
	}

	public int getUser_login_count() {
		return user_login_count;
	}

	public void setUser_login_count(int user_login_count) {
		this.user_login_count = user_login_count;
	}
	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public String getUser_last_login_ip() {
		return user_last_login_ip;
	}

	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public void setUser_last_login_ip(String user_last_login_ip) {
		this.user_last_login_ip = user_last_login_ip;
	}

	public int getUser_state() {
		return user_state;
	}

	public void setUser_state(int user_state) {
		this.user_state = user_state;
	}

	public String getAudit_user_name() {
		return audit_user_name;
	}

	public void setAudit_user_name(String audit_user_name) {
		this.audit_user_name = audit_user_name;
	}
	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_user_realname() {
		return audit_user_realname;
	}

	public void setAudit_user_realname(String audit_user_realname) {
		this.audit_user_realname = audit_user_realname;
	}

	public String getReferee_user_name() {
		return referee_user_name;
	}

	public void setReferee_user_name(String referee_user_name) {
		this.referee_user_name = referee_user_name;
	}

	public String getReferee_user_realname() {
		return referee_user_realname;
	}

	public void setReferee_user_realname(String referee_user_realname) {
		this.referee_user_realname = referee_user_realname;
	}

	public String getUser_company_qualified() {
		return user_company_qualified;
	}

	public void setUser_company_qualified(String user_company_qualified) {
		this.user_company_qualified = user_company_qualified;
	}

	public String getUser_register_type() {
		return user_register_type;
	}

	public void setUser_register_type(String user_register_type) {
		this.user_register_type = user_register_type;
	}

	public int getUser_company_address_max() {
		return user_company_address_max;
	}

	public void setUser_company_address_max(int user_company_address_max) {
		this.user_company_address_max = user_company_address_max;
	}

	public String getUser_company_type_name() {
		return user_company_type_name;
	}

	public void setUser_company_type_name(String user_company_type_name) {
		this.user_company_type_name = user_company_type_name;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getHas_paypwd() {
		return has_paypwd;
	}

	public void setHas_paypwd(int has_paypwd) {
		this.has_paypwd = has_paypwd;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getUser_resource() {
		return user_resource;
	}

	public void setUser_resource(String user_resource) {
		this.user_resource = user_resource;
	}
	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getUser_last_login_date() {
		return user_last_login_date;
	}

	public void setUser_last_login_date(Date user_last_login_date) {
		this.user_last_login_date = user_last_login_date;
	}

	public int getIs_temp() {
		return is_temp;
	}

	public void setIs_temp(int is_temp) {
		this.is_temp = is_temp;
	}

	public Long getIssuing_grade_id() {
		return issuing_grade_id;
	}

	public void setIssuing_grade_id(Long issuing_grade_id) {
		this.issuing_grade_id = issuing_grade_id;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public int getSale_type() {
		return sale_type;
	}

	public void setSale_type(int sale_type) {
		this.sale_type = sale_type;
	}

	public String getMarket_supervision_user_name() {
		return market_supervision_user_name;
	}

	public void setMarket_supervision_user_name(String market_supervision_user_name) {
		this.market_supervision_user_name = market_supervision_user_name;
	}

	public String getMarket_supervision_user_realna() {
		return market_supervision_user_realna;
	}

	public void setMarket_supervision_user_realna(
			String market_supervision_user_realna) {
		this.market_supervision_user_realna = market_supervision_user_realna;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}

	public String getSite_code() {
		return site_code;
	}
	public void setSite_code(String site_code) {
		this.site_code = site_code;
	}
	public String getCompany_province_name() {
		return company_province_name;
	}

	public void setCompany_province_name(String company_province_name) {
		this.company_province_name = company_province_name;
	}

	public String getCompany_city_name() {
		return company_city_name;
	}

	public void setCompany_city_name(String company_city_name) {
		this.company_city_name = company_city_name;
	}

	public String getCompany_county_name() {
		return company_county_name;
	}

	public void setCompany_county_name(String company_county_name) {
		this.company_county_name = company_county_name;
	}

	public long getReferee_user_id() {
		return referee_user_id;
	}

	public void setReferee_user_id(long referee_user_id) {
		this.referee_user_id = referee_user_id;
	}

	public long getMarket_supervision_user_id() {
		return market_supervision_user_id;
	}

	public void setMarket_supervision_user_id(long market_supervision_user_id) {
		this.market_supervision_user_id = market_supervision_user_id;
	}

	public int getApply_state() {
		return apply_state;
	}

	public void setApply_state(int apply_state) {
		this.apply_state = apply_state;
	}
	public int getPre_aprv_allowed_number() {
		return pre_aprv_allowed_number;
	}
	public void setPre_aprv_allowed_number(int pre_aprv_allowed_number) {
		this.pre_aprv_allowed_number = pre_aprv_allowed_number;
	}
	public int getPre_aprv_login_number() {
		return pre_aprv_login_number;
	}
	public void setPre_aprv_login_number(int pre_aprv_login_number) {
		this.pre_aprv_login_number = pre_aprv_login_number;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public double getUser_longitude() {
		return user_longitude;
	}
	public void setUser_longitude(double user_longitude) {
		this.user_longitude = user_longitude;
	}
	public double getUser_latitude() {
		return user_latitude;
	}
	public void setUser_latitude(double user_latitude) {
		this.user_latitude = user_latitude;
	}
	public String getUser_company_location_address() {
		return user_company_location_address;
	}
	public void setUser_company_location_address(String user_company_location_address) {
		this.user_company_location_address = user_company_location_address;
	}
	public String getUser_control_store_name() {
		return user_control_store_name;
	}
	public void setUser_control_store_name(String user_control_store_name) {
		this.user_control_store_name = user_control_store_name;
	}
	public int getUser_store_address_province() {
		return user_store_address_province;
	}
	public void setUser_store_address_province(int user_store_address_province) {
		this.user_store_address_province = user_store_address_province;
	}
	public int getUser_store_address_city() {
		return user_store_address_city;
	}
	public void setUser_store_address_city(int user_store_address_city) {
		this.user_store_address_city = user_store_address_city;
	}
	public int getUser_store_address_county() {
		return user_store_address_county;
	}
	public void setUser_store_address_county(int user_store_address_county) {
		this.user_store_address_county = user_store_address_county;
	}
	public String getUser_store_address_deails() {
		return user_store_address_deails;
	}
	public void setUser_store_address_deails(String user_store_address_deails) {
		this.user_store_address_deails = user_store_address_deails;
	}
	public String getUser_store_location_address() {
		return user_store_location_address;
	}
	public void setUser_store_location_address(String user_store_location_address) {
		this.user_store_location_address = user_store_location_address;
	}
	
	public String getWmall_permit() {
		return wmall_permit;
	}

	public void setWmall_permit(String wmall_permit) {
		this.wmall_permit = wmall_permit;
	}

	public String getPosshop_permit() {
		return posshop_permit;
	}

	public void setPosshop_permit(String posshop_permit) {
		this.posshop_permit = posshop_permit;
	}

	public double getService_rate() {
		return service_rate;
	}

	public void setService_rate(double service_rate) {
		this.service_rate = service_rate;
	}

	public int getCommission_rate() {
		return commission_rate;
	}

	public void setCommission_rate(int commission_rate) {
		this.commission_rate = commission_rate;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	public int getPartner_user_id() {
		return partner_user_id;
	}

	public void setPartner_user_id(int partner_user_id) {
		this.partner_user_id = partner_user_id;
	}

	public int getSupervisor_user_id() {
		return supervisor_user_id;
	}

	public void setSupervisor_user_id(int supervisor_user_id) {
		this.supervisor_user_id = supervisor_user_id;
	}

	public String getPartner_user_realna() {
		return partner_user_realna;
	}

	public void setPartner_user_realna(String partner_user_realna) {
		this.partner_user_realna = partner_user_realna;
	}

	public String getSupervisor_user_realna() {
		return supervisor_user_realna;
	}

	public void setSupervisor_user_realna(String supervisor_user_realna) {
		this.supervisor_user_realna = supervisor_user_realna;
	}

	public String getChild_login_name() {
		return child_login_name;
	}

	public void setChild_login_name(String child_login_name) {
		this.child_login_name = child_login_name;
	}

	public String getChild_mobilephone() {
		return child_mobilephone;
	}

	public void setChild_mobilephone(String child_mobilephone) {
		this.child_mobilephone = child_mobilephone;
	}

	public String getChild_user_manage_name() {
		return child_user_manage_name;
	}

	public void setChild_user_manage_name(String child_user_manage_name) {
		this.child_user_manage_name = child_user_manage_name;
	}

	public int getIs_sample_user_group() {
		return is_sample_user_group;
	}

	public void setIs_sample_user_group(int is_sample_user_group) {
		this.is_sample_user_group = is_sample_user_group;
	}

	public int getOther_sync_state() {
		return other_sync_state;
	}

	public void setOther_sync_state(int other_sync_state) {
		this.other_sync_state = other_sync_state;
	}
	
	public int getSleep_state() {
		return sleep_state;
	}

	public void setSleep_state(int sleep_state) {
		this.sleep_state = sleep_state;
	}

	public int getIs_open_vip() {
		return is_open_vip;
	}

	public void setIs_open_vip(int is_open_vip) {
		this.is_open_vip = is_open_vip;
	}

	public BigDecimal getCard_balance() {
		return card_balance;
	}

	public void setCard_balance(BigDecimal card_balance) {
		this.card_balance = card_balance;
	}

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

	public int getExpiration_flag() {
		return expiration_flag;
	}

	public void setExpiration_flag(int expiration_flag) {
		this.expiration_flag = expiration_flag;
	}

	public long getUser_logistics_template_id () {
		return user_logistics_template_id;
	}

	public void setUser_logistics_template_id (long user_logistics_template_id) {
		this.user_logistics_template_id = user_logistics_template_id;
	}

	public String getUser_logistics_template_name () {
		return user_logistics_template_name;
	}

	public void setUser_logistics_template_name (String user_logistics_template_name) {
		this.user_logistics_template_name = user_logistics_template_name;
	}


}
