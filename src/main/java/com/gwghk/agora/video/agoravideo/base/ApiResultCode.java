package com.gwghk.agora.video.agoravideo.base;

/**
 * 摘要：API 返回码定义
 * 备注: 错误码目前定义为6位以上,不同数字开头代表不同的含义
 * 1) 10****  后台系统相关提示
 * 2) 11****  api网关相关提示
 * 
 * 3) 2*****  基础配置相关提示
 * 4) 3*****  账户系统相关提示
 * 5) 4*****  订单系统相关提示
 * 6) 5*****  支付系统相关提示
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年9月12日
 */
public enum ApiResultCode{
	/**成功*/
	SUCCESS("0", "success"),
	/**系统异常*/
	EXCEPTION("-1", "exception"),
	/**失败*/
	FAIL("-2", "fail"),
	/**连接失败*/
	RpcError("-3","rpc.error"),
	/**连接超时*/
	RpcTimeoutError("-4","rpc.timeout"),
	/**调用接口异常*/
	InfError("-5","inf.error"),
	
	/**参数有误，请检查*/
	E1("1","param.incorrect"),
	 /**请先登录后再操作！*/
    E2("2", "please.login"),
    /**该浏览器不支持,请换浏览器再试！*/
    E3("3", "browser.not.supper"),
    /**参数不合法!*/
    E4("4","param.invalid"),
    /**获取下一个序列失败!*/
    E5("5","sequence.get.failed"),
    /**文件格式有误*/
    E6("6","incorrect.file.format"),
    
	/**包含非法字符*/
	E100001("10001","bos.contains.illegal.characters"),
	/**参数有误，请检查*/
	E100002("10002","bos.parameter.incorrect.please.check"),
	/**账号不能为空*/
	E100003("10003","bos.account.cannot.empty"),
	/**密码不能为空*/
	E100004("10004","bos.password.cannot.empty"),
	/**验证码不能为空*/
	E100005("10005","bos.verification.code.cannot.empty"),
	/**验证码不正确*/
	E100006("10006","bos.verification.code.incorrect"),
	/**账号不正确*/
	E100007("10007","bos.account.number.incorrect"),
	/**密码不正确*/
	E100008("10008","bos.password.incorrect"),
	/**对不起，请先登录！*/
	E100009("10009","bos.please.login.in.first"),
	/**对不起，无权限访问！*/
	E100010("10010","bos.no.access"),
	/**会话超时！*/
	E100011("10011","bos.session.timed.out"),
    /**上传失败，文件不能超过2M!*/
	E100012("10012","bos.upload.failed.file.cannot.exceed.10M"),
	/**上传失败，文件为空!*/
	E100013("10013","bos.upload.failed.file.is.empty"),
	/**手机号不能为空*/
	E100014("10014","bos.phone.number.cannot.be.empty"),
	/**查询结果为空*/
	E100015("10015","bos.query.result.is.empty"),
	/**初始数据，不能修改*/
	E100016("10016","bos.initial.data.cannot.be.modified"),
	/**角色不能为空*/
	E100017("10017","bos.character.not.be.empty"),
	/**账号不能重复*/
	E100018("10018","bos.account.cannot.be.repeated"),
	/**超级用户不可删除*/
	E100019("10019","bos.users.cannot.be.deleted"),
	/**原密码与新密码相同*/
	E100020("10020","bos.password.same.new.password"),
	/**账号不能为空!*/
	E100021("10021","bos.upload.failed.error.line.account.cannot.be.empty"),
	/**用户名不能为空!*/
	E1000212("100212","bos.upload.failed.error.line.username.cannot.be.empty"),
	/**门店编号不能为空!*/
	E1000213("100213","bos.upload.failed.error.line.storeno.cannot.be.empty"),
	/**保存失败!*/
	E1000214("100214","bos.upload.failed.error.line.save.error"),
	/**门店编号不存在*/
	E1000215("100215","bos.upload.failed.error.line.store.does.not.exist"),
	/**用户账号已存在!*/
	E100022("10022","bos.upload.failed.error.in.line.user.account.already.exists"),
	/**密码不能为空!*/
	E100023("10023","bos.upload.failed.error.in.line.password.cannot.be.empty"),
	/**角色编号不能为空!*/
	E100024("10024","bos.upload.failed.line.error.role.number.cannot.be.empty"),
	/**角色编号不存在!*/
	E100025("10025","bos.upload.failed.error.line.role.number.does.not.exist"),
	/**角色编号被禁用!*/
	E1000251("100251","bos.upload.failed.error.line.role.number.is.disabled"),
	/**状态不能为空!*/
	E100026("10026","bos.upload.failed.error.line.status.cannot.be.empty"),
	/**状态填写有误,正确格式(Y/N)!*/
	E100027("10027","bos.upload.failed.error.line.status.is.ncorrectly.filled.correct.format"),
	/**excel中有重复用户账号!*/
	E100028("10028","bos.upload.failed.error.line.duplicate.user.account.in.excel"),
	/**上传的数据为空，无需上传!*/
	E100029("10029","bos.upload.failed.uploaded.data.is.empty.no.upload.required"),
	/**编号不能为空*/
	E100030("10030","bos.number.not.be.empty"),
	/**编号不能重复*/
	E100031("10031","bos.number.cannot.be.repeated"),
	/**角色名称不能重复*/
	E1000311("100311","bos.role.name.cannot.be.repeated"),
	/**用户的角色已被禁用*/
	E100032("10032","bos.user.role.disabled"),
	/**用户未授角色*/
	E100033("10033","bos.user.not.granted.role"),
	/**用户被禁用*/
	E100034("10034","bos.user.is.disabled"),
	/**上级菜单编码不能为空*/
	E100035("10035","bos.upper.menu.code.cannot.be.empty"),
	/**上级菜单不存在*/
	E100036("10036","bos.upper.menu.does.not.exist"),
	/**请先删除子节点*/
	E100037("10037","bos.please.delete.the.child.node.first"),
	/**上级数据字典不能为空*/
	E100038("10038","bos.super.data.dictionary.cannot.be.empty"),
	/**删除的数据不存在*/
	E100040("10040","bos.deleted.data.does.not.exist"),
	/**业务代码不能重复*/
	E100041("10041","bos.headCode.cannot.be.repeated"),
	/**角色正在使用，不能删除*/
	E100042("10042","bos.role.used.cannot.deleted"),
	/**总店信息，不能删除*/
	E100043("10043","bos.general.store.used.cannot.deleted"),
	/**所属门店编号不能为空*/
	E100044("10044","bos.store.number.cannot.be.empty"),
	/**所属门店不存在*/
	E100045("10045","bos.store.does.not.exist"),
	/**上传数据栏位不能为空*/
	E100046("100046","bos.upload.data.field.cannot.be.empty"),
	/**经纬度整数长度不能超过3位*/
	E100047("100047","bos.store.lat.and.lng.length.not.exceed.3bits"),
	/**支付方式对应渠道代码不能重复*/
	E100048("10048","bos.paytype.channel.cannot.be.repeated"),
	/**收款方式对应渠道代码不能重复*/
	E100049("10049","bos.payTransfer.channel.cannot.be.repeated"),
	/**门店下有子门店，不能删除*/
	E100050("10050","bos.sub.shop.under.store.cannot.be.deleted"),
	/**门店下有用户，不能删除*/
	E100051("10051","bos.users.under.store.cannot.be.deleted"),
	/**上传的图片格式暂只支持JPG、PNG、BMP、JPEG*/
	E100052("10052","bos.upload.file.format.valid"),
	/**该地区已存在总店，不允许再新增总店了*/
    E100053("10053","bos.store.canot.add.totalStore"),


	/**api网关相关提示**/
	/**token或sgin无效*/
	E110001("110001", "api.token.or.sgin.invalid"),
	
	/** 基础配置  **/
	/** 支付方式不能为空  **/
	E200001("200001", "base.api.result.code.200001"),
	/** 收款方式不能为空  **/
	E200002("200002", "base.api.result.code.200002"),
	/** 支付币种不能为空  **/
	E200003("200003", "base.api.result.code.200003"),
	/** 收款币种不能为空  **/
	E200004("200004", "base.api.result.code.200004"),
	/** 门店编号不能为空  **/
	E200005("200005", "base.api.result.code.200005"),
	/** 货币对已经存在  **/
	E200006("200006", "base.api.result.code.200006"),
	/** 支付方式，收款方式已经存在  **/
	E200007("200007", "base.api.result.code.200007"),
	/** 类型代码，类型名称已经存在  **/
	E200008("200008", "base.api.result.code.200008"),
	/** 门店对应的手续配置已经存在  **/
	E200009("200009", "base.api.result.code.200009"),
	/** 总门店对应的手续配置不能删除  **/
	E200010("200010", "base.api.result.code.200010"),
	/** 渠道代码已存在  **/
	E200011("200011", "base.api.result.code.200011"),

	/** 账户系统  **/
	/**账号不能为空*/
	E300001("300001", "ucenter.api.result.code.300001"),
	/**姓不能为空*/
	E300002("300002", "ucenter.api.result.code.300002"),
	/**名不能为空*/
	E300003("300003", "ucenter.api.result.code.300003"),
	/**性别不能为空*/
	E300004("300004", "ucenter.api.result.code.300004"),
	/**出生日期不能为空*/
	E300005("300005", "ucenter.api.result.code.300005"),
	/**国家不能为空*/
	E300006("300006", "ucenter.api.result.code.300006"),
	/**联系地址不能为空*/
	E300007("300007", "ucenter.api.result.code.300007"),
	/**证件类型不能为空*/
	E300008("300008", "ucenter.api.result.code.300008"),
	/**证件编号不能为空*/
	E300009("300009", "ucenter.api.result.code.300009"),
	/**证件图片不能为空*/
	E300010("300010", "ucenter.api.result.code.300010"),
	/**自拍照不能为空*/
	E300011("300011", "ucenter.api.result.code.300011"),
	/**银行名称不能为空*/
	E300012("300012", "ucenter.api.result.code.300012"),
	/**银行卡号不能为空*/
	E300013("300013", "ucenter.api.result.code.300013"),
	/**手机号不能为空*/
	E300014("300014", "ucenter.api.result.code.300014"),
	/**收款人编号不能为空*/
	E300015("300015", "ucenter.api.result.code.300015"),
	/**手机号码已经存在*/
	E300016("300016", "ucenter.api.result.code.300016"),
	/**手机号码不存在*/
	E300017("300017", "ucenter.api.result.code.300017"),
	/**登录账号或者密码错误*/
	E300018("300018","ucenter.api.result.code.300018"),
	/**认证资料状态不正确,不允许提交*/
	E300019("300019","ucenter.api.result.code.300019"),
	/**客户账号信息不存在*/
	E300020("300020","ucenter.api.result.code.300020"),
	/**审核状态不正确*/
	E300021("300021","ucenter.api.result.code.300021"),
	/**上传失败，上传文件最大为10M*/
	E300022("300022","ucenter.api.result.code.300022"),
	/**上传失败，导入数据为空*/
	E300023("300023","ucenter.api.result.code.300023"),
	/**账号已经被锁定，不允许登录*/
	E300024("300024","ucenter.api.result.code.300024"),
	/**账号已经被锁定，不允许登录*/
	E300025("300025","ucenter.api.result.code.300025"),
	/**账号已经禁用，不允许操作*/
	E300026("300026","ucenter.api.result.code.300026"),
	/**银行卡号已经存在*/
	E300027("300027","ucenter.api.result.code.300027"),
	/**电子钱包卡号已经存在*/
	E300028("300028","ucenter.api.result.code.300028"),
	/**收款人信息不存在*/
	E300029("300029","ucenter.api.result.code.300029"),
	/**级别名称已经存在*/
	E300030("300030", "ucenter.api.result.code.300030"),
	/**默认级别不允许删除*/
	E300031("300031", "ucenter.api.result.code.300031"),
	/**级别正在使用,不允许删除*/
	E300032("300032", "ucenter.api.result.code.300032"),
	/**级别名称不允许为空*/
	E300033("300033", "ucenter.api.result.code.300033"),
	/**级别状态不允许为空*/
	E300034("300034", "ucenter.api.result.code.300034"),
	/**级别不存在*/
	E300035("300035", "ucenter.api.result.code.300035"),
	/**默认级别不允许修改*/
	E300036("300036", "ucenter.api.result.code.300036"),
	/**账号级别不能重复*/
	E300037("300037", "ucenter.api.result.code.300037"),
	/**不支持该证件类型*/
	E300038("300038", "ucenter.api.result.code.300038"),
	/**黑名单账号不允许上传认证凭证*/
	E300039("300039", "ucenter.api.result.code.300039"),
	/**非启用账号不允许上传认证凭证*/
	E300040("300040", "ucenter.api.result.code.300040"),
	/**校验失败，所上传文件中无数据*/
	E300041("300041","ucenter.api.result.code.300041"),
	/** 订单系统 **/
	/**订单编号不能为空*/
	E400001("400001", "order.api.result.code.400001"),
	/**您的订单还未支付成功，不能设置出单状态！*/
	E400002("400002", "order.api.result.code.400002"),
	/**您当前登录账户所属门户不能修改其它门店订单！*/
	E400003("400003", "order.api.result.code.400003"),
	/**保存失败，订单编号系统已存在！*/
	E400004("400004", "order.api.result.code.400004"),
	/**保存失败，订单不存在！*/
	E400005("400005", "order.api.result.code.400005"),
	/**保存失败，状态不匹配，只允许待审批的订单进行审批！*/
	E400006("400006", "order.api.result.code.400006"),
	/**保存失败，订单已关闭，不允许修改!*/
	E400007("400007", "order.api.result.code.400007"),
	/**保存失败，状态不匹配，不允许修改支付状态！*/
	E400008("400008", "order.api.result.code.400008"),
	/**保存失败，状态未变更！*/
	E400009("400009", "order.api.result.code.400009"),
	/**保存失败，加载不到订单货币与兑换货币的汇率设置*/
	E400010("400010", "order.api.result.code.400010"),
	/**保存失败，订单货币与兑换货币汇率已变更*/
	E400011("400011", "order.api.result.code.400011"),
	/**保存失败，当前支付状态不能修改为待支付*/
	E400012("400012", "order.api.result.code.400012"),
	/**保存失败，当前订单不支持修改为此支付状态，请检查*/
	E400013("400013", "order.api.result.code.400013"),
	/**保存失败，该订单并非待审批且支付成功的订单*/
	E400014("400014", "order.api.result.code.400014"),
	/**保存失败，当前订单不允许修改为此出单状态*/
	E400015("400015", "order.api.result.code.400015"),
	/**保存失败，获取订单编号失败*/
    E4000016("4000016", "order.api.result.code.400016"),
    /**订单不存在！*/
    E400017("400017", "order.api.result.code.400017"),
    /**操作失败，该订单不能取消*/
    E400018("400018", "order.api.result.code.400018"),
    /**操作失败，该订单已被取消过，不能再次取消*/
    E400019("400019", "order.api.result.code.400019"),
    /**操作失败，只允许审批通过且待支付中的订单上传支付证明，请确认*/
    E400020("400020", "order.api.result.code.400020"),
    /**操作失败，此订单非转账凭证支付，不支持上传支付证明，请确认*/
    E400021("400021", "order.api.result.code.400021"),
    /**操作失败，此订单待支付时间已过，已失效，不能再上传支付证明，请确认*/
    E400022("400022", "order.api.result.code.400022"),
    /**操作失败，加载支付用户信息失败*/
    E400023("400023", "order.api.result.code.400023"),
    /**操作失败， 支付用户信息不存在*/
    E400024("400024", "order.api.result.code.400024"),
    /**操作失败，获取总门店编号失败*/
    E400025("400025", "order.api.result.code.400025"),
    /**操作失败，获取支付渠道的门店信息失败*/
    E400026("400026", "order.api.result.code.400026"),
    /**操作失败，获取门店手续费信息失败*/
    E400027("400027", "order.api.result.code.400027"),
    /**操作失败，获取用户有效证件信息失败*/
    E400028("400028", "order.api.result.code.400028"),
    /**操作失败，获取用户支付限额信息失败*/
    E400029("400029", "order.api.result.code.400029"),
    /**操作失败，当前付款金额低于单笔支付最低金额*/
    E400030("400030", "order.api.result.code.400030"),
    /**操作失败，当前付款金额高于单笔支付最高金额*/
    E400031("400031", "order.api.result.code.400031"),
    /**操作失败，当前所属门店未配置手续费，不允许下单*/
    E400032("400032", "order.api.result.code.400032"),
    /**操作失败，当前所属门店手续费类型设置已变更，请重新下单*/
    E400033("400033", "order.api.result.code.400033"),
    /**操作失败，当前所属门店手续费率已变更，请重新下单*/
    E400034("400034", "order.api.result.code.400034"),
    /**操作失败，手续费校验不通过,参考值:{0}*/
    E400035("400035", "order.api.result.code.400035"),
    /**操作失败，兑换金额校验不通过,参考值:{0}*/
    E400036("400036", "order.api.result.code.400036"),
    /**操作失败，付款人认证信息未通过审核*/
    E400037("400037", "order.api.result.code.400037"),
    /**操作失败，付款人为黑名名用户，不允许下单*/
    E400038("400038", "order.api.result.code.400038"),
    /**操作失败，获取黑名单信息失败*/
    E400039("400039", "order.api.result.code.400039"),
    /**操作失败，获取支付方式信息失败*/
    E400040("400040", "order.api.result.code.400040"),
    /**操作失败，获取收款方式信息失败*/
    E400041("400041", "order.api.result.code.400041"),
    /**操作失败，支付方式不存在或已禁用*/
    E400042("400042", "order.api.result.code.400042"),
    /**操作失败，收款方式不存在或已禁用*/
    E400043("400043", "order.api.result.code.400043"),
    /**操作失败，获取支付渠道信息失败*/
    E400044("400044", "order.api.result.code.400044"),
    /**操作失败，支付渠道不存在*/
    E400045("400045", "order.api.result.code.400045"),
    /**操作失败，支付渠道已禁用*/
    E400046("400046", "order.api.result.code.400046"),
    /**操作失败，获取收款渠道信息失败*/
    E400047("400047", "order.api.result.code.400047"),
    /**操作失败，收款渠道不存在*/
    E400048("400048", "order.api.result.code.400048"),
    /**操作失败，收款渠道已禁用*/
    E400049("400049", "order.api.result.code.400049"),
    /**操作失败，付款人与当前登录账号不一致*/
    E400050("400050", "order.api.result.code.400050"),
    /**操作失败，请提供有效的身份证明*/
    E400051("400051", "order.api.result.code.400051"),
    /**操作失败，订单金额校验不通过,参考值:{0}*/
    E400052("400052", "order.api.result.code.400052"),
    /**操作失败，用户不存在有效的身份证件*/
    E400053("400053", "order.api.result.code.400053"),
    /**操作失败，此类订单此时的支付状态不允许审核*/
    E400054("400054", "order.api.result.code.400054"),
    /**操作失败，此用户未启用*/
    E400055("400055", "order.api.result.code.400055"),
    /**操作失败，订单货币与门户的结算货币不一致*/
    E400056("400056", "order.api.result.code.400056"),
    /**保存失败，需要配置订单货币与门店结算货币的汇率*/
    E400057("400057", "order.api.result.code.400057"),
    /**操作失败，只允许编辑保存当前门店的订单*/
    E400058("400058", "order.api.result.code.400058"),
    /** 支付系统  */
	/**用户级别不能重复*/
	E500001("500001", "pay.api.result.code.500001"),
 
	;
	
	private String code;
	private String message;

	private ApiResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ApiResultCode getByCode(String code){
		for(ApiResultCode ae : ApiResultCode.values()){
			if(ae.getCode().equals(code)){
				return ae;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}