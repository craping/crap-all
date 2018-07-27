
package org.crap.jrain.mvc.param;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * ClassName: Constant
 * 
 * @author Crap
 * 
 *         2016年9月6日 下午4:53:40
 * 
 * @since JDK 1.8
 * 
 * @descr 与业务相关的常量定义
 */
public interface Constant {

	Marker CM_REBATES_MARKER = MarkerManager.getMarker("CM_REBATES");
	Marker CM_ACTIVITY_MARKER = MarkerManager.getMarker("CM_ACTIVITY");
	Marker CM_COMMISSION_MARKER = MarkerManager.getMarker("CM_COMMISSION");
	Marker CM_CONTRACT_MARKER = MarkerManager.getMarker("CM_CONTRACT");
	Marker CM_FINANCIAL_MARKER = MarkerManager.getMarker("CM_FINANCIAL");
	Marker CM_LOTTERY_MARKER = MarkerManager.getMarker("CM_LOTTERY");
	Marker CM_WARN_MARKER = MarkerManager.getMarker("CM_WARN");
	Marker CM_WEBSOCKET_MARKER = MarkerManager.getMarker("CM_WEBSOCKET");
	Marker CM_REPORT_MARKER = MarkerManager.getMarker("CM_REPORT_MARKER");

	interface System {

		String DEFAULT_PASSWORD = "111111";

		/**
		 * redis key名
		 */
		interface RedisKey {
			String CONTRACT_CONFIG = "t_sys_contract_config";
			String CONTRACT_RECORD = "t_contract_records";
			String DAILY_WAGE_TEMS_NUM = "t_commission_daily_wage_terms_num"; // 日工资条款数
		}

		/**
		 * 规定的系统参数名
		 */
		interface ParamName {
			String LOGIN_CREDIT = "login_credit";
			String SIGNIN_CREDIT = "signin_credit";
		}

		/** redisKey帮助符 **/
		interface KeyHelp {
			String FULL_STOP = ".";
		}

		/**
		 * 注册类型
		 */
		interface REGType {
			// 注册类型 RT001=代理链接注册 RT002=代理手动注册 RT003=系统注册
			String LINK = "RT001";
			String MANUAL = "RT002";
			String SYS = "RT003";
		}

		/**
		 * 系统参数默认值
		 */
		interface ParamValue {
			int LOGIN_CREDIT = 5;
			int SIGNIN_CREDIT = 5;
		}

		interface State {
			String INVALID = "ST000"; // 已失效
			String ACTIVE = "ST001"; // 活动的
			String LOCKED = "ST002"; // 锁定的
			String EXCEPTION = "ST003"; // 异常的
		}

		interface DataStatus {
			int Y = 1; // 启用
			int N = 0; // 未启用
			String YR = "1"; // 用于缓存状态启用
			String NR = "0"; // 用于缓存状态未启用
		}

		interface RealFlag {
			int Y = 1;
			int N = 0;
		}

		/**
		 * 菜单类型
		 */
		interface UiType {
			int FIRSTMENU = 1;
			int SECONDMENU = 2;
			int THREEMENU = 3;
		}

		/**
		 * 关联类型
		 */
		interface RelType {
			int APK = 1;
			int GOODS = 2;
		}

		/**
		 * 子类型
		 */
		interface SysType {
			String MGR = "mgr";
			String WEB = "web";
		}

		interface UserType {
			int MGR = 1;
			int WEB = 2;
		}

		/**
		 * 手续费类型
		 */
		interface FeeType {
			// 返手续费
			String ADD = "FT001";
			// 扣手续费
			String MINUS = "FT002";
		}

		interface AccountType {
			// 1 超级管理员2 机构管理员 3 普通用户 4 员工
			int ADMIN = 1;
			int ORGADMIN = 2;
			int MEMBER = 3;
			int EMPLOYEE = 4;
		}

		interface BankCard {
			int MaxNumber = 6;
		}

		/**
		 * 角色类型
		 */
		interface RoleType {
			// 0:系统角色 1:系统资源角色
			int SYS = 0;
			int RES = 1;
		}

		/**
		 * 初始化角色ROLECODE
		 */
		interface RoleCode {
			String 客服 = "KFJS";
		}
	}

	/**
	 * 用户常量
	 *
	 */
	interface Account {

		interface Type {
			String 代理 = "AT001";
			String 会员 = "AT003";
			String 试玩会员 = "AT006";
		}

		interface Notopera {
			String 是否提现 = "OP001";
			String 是否发放活动资金 = "OP002";
			String 是否任意提现 = "OP003";
			String 是否代充 = "OP004";
			String 是否购彩 = "OP005";
		}

		interface CapitalOpType {
			String 冻结 = "LS001";
			String 解禁 = "LS002";
		}

		interface Status {
			String 删除 = "0";
			String 可用 = "1";
			String 冻结 = "2";
		}

		interface LogicStatus {
			String 可用 = "LS001";
			String 冻结 = "LS002";
			String 锁定 = "LS003";
		}

		interface OperMode {
			int 系统 = 1;
			int 手动 = 0;
		}

		interface Whether {
			int 是 = 1;
			int 否 = 0;
		}

		interface Bankcard {
			String 可用 = "LS001";
			String 冻结 = "LS002";
			String 禁用 = "LS003";
		}
	}

	/**
	 * 资金常量定义
	 */
	interface Finance {
		//不可操作原因类型
		interface ReasonType{
			String 契约分红 = "SY001";
			String 代理设置 = "SY002";
			String 平台设置 = "SY003";
		}
		interface CourseType {
		    String 修正理赔  = "LP-XZ";
		    
			/** 收入 以I开头 */
		    String 普通理赔增加  = "I-LP-PT";
		    String 佣金理赔增加  = "I-LP-YJ";
		    String 充值理赔增加  = "I-LP-CZ";
		    String 活动理赔增加  = "I-LP-HD";
		    String 分红理赔增加  = "I-LP-FH";
		    String 修正理赔增加  = "I-LP-XZ";
		    
			String 帐号充值 = "I-CZ-ZH";
			String 撤单返款 = "I-FK-CD";
			String 追号返款 = "I-FK-ZH";
			String 代理代充 = "I-CZ-DL";
			String 活动经费收款 = "I-AF-HD";
			String 活动收款 = "I-FK-HD";// 未使用
			String 投注奖金 = "I-JJ-TZ";
			String 赠送手续费 = "I-ZS-SXF";
			String 解禁资金  = "I-LF-DS"; 
			
			/** 系统分类 */
			String 投注返点 = "I-FD-TZ";
			String 下级返点 = "I-FD-XJ";
			/** 活动分类 */
			String 充值活动 = "I-HD-CZ";
			String 注册活动 = "I-HD-ZC";
			String 绑卡活动 = "I-HD-BK";
			String 工资活动 = "I-HD-GZ"; //已作废
			String 投注活动 = "I-HD-TZ";
			String 红包活动 = "I-HD-HB";

			/** 佣金分类 */
			String 充值佣金 = "I-YJ-CZ";
			String 投注佣金 = "I-YJ-TZ";
			String 亏损佣金 = "I-YJ-KS";
			String 注册佣金 = "I-YJ-ZC";
			String 工资佣金 = "I-YJ-GZ";
			/** 契约分类 */
			String 接收分红 = "I-FH-JS";
			String 领取分红 = "I-FH-LQ";
			String 提现失败 = "I-TX-CG";

			/** 支出 以O开头 */
			String 普通理赔扣减  = "O-LP-PT";
			String 分红理赔扣减  = "O-LP-FH";
            String 佣金理赔扣减  = "O-LP-YJ";
            String 活动理赔扣减  = "O-LP-HD";
            String 充值理赔扣减  = "O-LP-CZ";
            String 修正理赔扣减  = "O-LP-XZ";
            
			String 提现申请 = "O-TX-SQ";
			String 代充扣款 = "O-KK-DC";
			String 活动经费扣款 = "O-KK-HD";
			String 投注扣款 = "O-KK-TZ";
			String 追号扣款 = "O-KK-ZH";
			String 扣除手续费 = "O-SXF-KC";
			/** 契约分类 */
			String 发放分红 = "O-FH-FF";
			
			String 发放日工资 = "O-YJ-GZ";

			String 冻结资金 = "O-FR-FC";

			String 撤单手续费 = "O-SXF-CD";
			/** 其它 */
			String 其它 = "QT001";
			/** 撤销订单奖金*/
			String 撤销订单奖金 = "O-JJ-CX";
			/** 撤销订单返点 */
			String 撤销订单返点 = "O-FD-CX";

		}

		interface IsSupport {
			int Y = 0; // 是
			int N = 1; // 否
		}
		
		interface PaymentPlatform {
			 String 银商通ID = "14765c76385b4be5a9701840da11c666";
			 String CqPayID ="b5543e71c1d646d1aa58a52cf4c51585";
			 String cqQPPayID = "c0c4f07725cd40eea3c66423b452c10c";
		}
		
		

		interface RechargeStatus {
			// LS001=等待充值\r\n LS002=处理中\r\n LS003=充值成功\ LS004=充值失败\
			String WAIT = "LS001";
			String TREATMENT = "LS002";
			String SUCCESS = "LS003";
			String FAIL = "LS004";
		}
		
		interface DepositType {
			String 网银转帐 = "CT001";
			String 微信 = "CT002";
			String 支付宝 = "CT003";
			String 快捷支付 = "CT004";
			
		}

		interface WithdrawalsStatus {
			// LS001=等待\r\n LS002=处理中\r\n LS003=成功\ LS004=失败\
			String WAIT = "LS001";
			String TREATMENT = "LS002";
			String SUCCESS = "LS003";
			String FAIL = "LS004";
		}

		interface LogicStatus {
			String SUCCESS = "LS001";
			String FAIL = "LS002";
		}

		interface PayType {
			/*
			 * PT001=网银转账 PT002=快捷支付 PT003=支付宝 PT004=微信 PT005=财付通 PT007=活动经费
			 * PT006=代充'
			 */
			String CYBERBANK = "PT001";
			String QUICK = "PT002";
			String ALIPAY = "PT003";
			String WECHAT = "PT004";
			String QQWalletScanCode ="PT008"; 
			String QQWallet ="PT011"; 
			String TENPAY = "PT005";
			String HELPRECHARGE = "PT006";
			String ACTIVITYFUNDS = "PT007";
		}

		interface WithdrawalStatus {
			// LS001=申请 LS002=处理中 LS003=提现成功 LS004=提现失败',
			String WAIT = "LS001";
			String TREATMENT = "LS002";
			String SUCCESS = "LS003";
			String FAIL = "LS004";
		}
		
		interface WithdrawalMode {
			
			int 自动 = 1;
			int 手动 = 0;
			
		}

		interface IsArbitraryWithdrawal {
			// 用户是否任意提现
			int Y = 1; // 是
			int N = 0; // 否
		}
		
		

        interface isAgentDeposit {
            // 用户是否代充
            String Y = "1"; // 是
            String N = "0"; // 否
        }
		/**
		 * 充值逻辑业务
		 */
		interface UiType {
			int FIRSTMENU = 1;
			int SECONDMENU = 2;
			int THREEMENU = 3;
		}

		/**
		 * 账变编号类型
		 */
		interface CodeType {
			String 投注编号 = "CT001";
			String 追号编号 = "CT002";
			String 账变编号 = "CT003";
		}

		/**
		 * 用户统计时间跨度 单位 天
		 */
		interface Interval {
			int 间隔时间 = 30;
		}

		/*
		 * interface CourseName {
		 * 
		 * //收入写在下面. String Z_H_C_Z = "帐号充值"; String C_D_F_K = "撤单返款"; String
		 * H_D_Z_S = "活动赠送"; String Y_X_F_D = "游戏返点"; String Y_J = "佣金";
		 * //支出写在下面 String T_X_S_Q = "提现申请"; String T_Z_K_K = "投注扣款"; String
		 * Z_H_K_K = "追号扣款"; String T_X_C_G = "提现成功"; }
		 */

		/**
		 * 活动经费
		 */
		interface ActivityFund {

			/**
			 * 发放类型
			 */
			interface GrantType {
				String 系统发放 = "GT001";
				String 用户发放 = "GT002";
			}

			/**
			 * 经费状态
			 */
			interface FundStatus {
				String 未接收 = "FS001";
				String 已接收 = "FS002";
			}

		}
		interface ClaimsBonusModeType{
            String 普通金额 = "BM001";
            String 活动金额 = "BM002";
        }
		
	}

	/**
	 * 返点类常量
	 *
	 */
	interface Rebates {

		interface LogicStatus {
			/** 成功 */
			String SUCCESS = "ST001";
			/** 失败 */
			String FAIL = "ST002";
			/** 撤销返点 */
			String 撤销返点 = "ST003";
		}

		interface Assessment {

			interface LogicStatus {
				/** 未开始 */
				String NOT_START = "ST000";
				/** 考核中 */
				String CHECK_IN = "ST100";
				/** 考核中并且达标 */
				String CHECK_IN_SUCCESS = "ST101";
				/** 成功 */
				String SUCCESS = "ST200";
				/** 失败 */
				String FAIL = "ST201";
			}
		}
	}

	/**
	 * 配额类常量
	 *
	 */
	interface Quota {

		interface AllotType {
			String 平台开户扣减 = "ST101";
			String 平台销户回收 = "ST102";
			String 平台降点回收 = "ST103";
			
			String 开户扣减配额 = "ST104";
			String 销户回收配额 = "ST105";
			String 降点回收配额 = "ST106";
			/** 系统升配额 *//*
			String 系统升配额 = "ST201";
			*//** 系统降配额 *//*
			String SYS_DOWN = "ST202";
			*//** 代理升配额 *//*
			String AGENT_UP = "ST203";
			*//** 代理降配额 *//*
			String AGENTlog_DOWN = "ST204";*/

			String 向代理分配额 = "ST301";
			String 接收平台配额 = "ST302";
			String 回收代理配额 = "ST303";
			String 平台回收配额 = "ST304";

			String 向下级分配额 = "ST305";
			String 接收上级配额 = "ST306";
			String 回收下级配额 = "ST307";
			String 上级回收配额 = "ST308";
		}

	}

	/**
	 * 佣金类常量
	 *
	 */
	interface Commission {
		
		String 平台标识ID = "ACCOUNT001";
		

		/** 佣金类型 */
		interface LogicType {
			/** 亏损 */
			int LOSS = 0;
			/** 投注 */
			int ORDER = 1;

		}

		/** 佣金类型 */
		interface CommissionType {
			/** 有效用户佣金 */
			String 有效用户 = "ACCOUNT";
			/** 充值佣金 */
			String 充值佣金 = "DEPOSIT";
			/** 投注佣金 */
			String 投注佣金 = "ORDER";
			/** 亏损佣金 */
			String 亏损佣金 = "LOSS";
			/** 日工资佣金 */
			String 日工资佣金 = "DAILYWAGE";
		}

		/** 佣金数据表 */
		interface TypeTable {
			String 有效用户 = "t_sys_commission_account|t_commission_account_record|commission_account_id|source_account_id|t.amount_count as amountCount";
			String 充值佣金 = "t_sys_commission_deposit|t_commission_deposit_record|commission_deposit_id|lottery_order_id|t.amount as amount,t.bet_percent as betPercent";
			String 投注亏损佣金 = "t_sys_commission_lottery|t_commission_account_lottery|commission_lottery_id|lottery_id|t.amount as amount";
		}
		
		
		
		/** 日工资佣金状态 */
		interface LogicStatus {
			String 生效 = "ST001";
			String 失效 = "ST002";
		}
		
		
		/** 日工资佣金发放类型 */
		interface sentMode {
			int 自动发放 = 0;
			int 手动发放 = 1;
		}
		
		/** 日工资佣金状态 */
		interface LogicAmountStatus {
			String 已发放 = "LS001";
			String 未发放 = "LS002";
		}
		
	}

	/**
	 * 活动类常量
	 *
	 */
	interface Activity {

		interface RedisKey {
			String 充值活动 = "t_activity.recharge";
			String 下注活动 = "t_activity.betting";
			String 绑卡活动 = "t_activity.card";
			String 工资活动 = "t_activity.dailyWage";
			String 红包活动 = "t_activity.redPackets";
			String 注册活动 = "t_activity.register";
		}

		/** 活动金额状态 */
		interface LogicAmountStatus {
			String 已发放 = "LS001";
			String 未发放 = "LS002";
		}

		/** 活动状态 */
		interface LogicStatus {
			String 未发布 = "LS000";
			String 未开始 = "LS100";
			String 进行中 = "LS101";
			String 已结束 = "LS102";
			String 手动终止 = "LS103";
		}

		/** 发送方式 */
		interface SentMode {
			int 自动发送 = 0;
			int 手动发送 = 1;
		}

		/** 奖金类型 */
		interface BonusMode {
			String 普通金额 = "BM001";
			String 活动金额 = "BM002";
		}

		/** 活动类型 */
		interface Type {
			String 红包活动 = "REDPACKETS";
			String 绑定银行卡活动 = "CARD";
			String 注册活动表 = "REGISTER";
			String 充值赠送活动 = "RECHARGE";
			String 累计投注活动 = "BETTING";
			String 日工资活动 = "DAILYWAGE";
		}

		/** 活动数据表 */
		interface TypeTable {
			String 红包活动 = "t_redpackets_activity";
			String 绑定银行卡活动 = "t_card_no_activity";
			String 注册活动表 = "t_register_activity";
			String 充值赠送活动 = "t_recharge_activity";
			String 累计投注活动 = "t_accrued_betting_activity";
			String 日工资活动 = "t_daily_wage_activity";
		}

		/** 结算类型 */
		interface StateType {
			String 单记录计算 = "ST001";
			String 多记录计算 = "ST002";
			String 整期活动结算 = "ST003";
		}
		
		
		/** 活动范围 */
		interface Range {
			String 直属代理 = "ST001";
			String 代理 = "ST003";
			String 会员 = "ST004";

		}
		
		interface BonusType {
			//活动
			String 红包活动 = "BT301";
			String 绑定银行卡活动 = "BT302";
			String 注册活动表 = "BT303";
			String 充值赠送活动 = "BT304";
			String 累计投注活动 = "BT305";
			String 日工资活动 = "BT306";
		}
	}

	/**
	 * websocket常量
	 *
	 */
	interface Websocket {

		/** 通道端口 */
		interface WebsocketPort {
			int 后台 = 10443;
			int 前台 = 9092;
		}

		/** 通道命名空间 */
		interface Namespace {
			String 后台 = "/mgrChat"; 
			String 前台 = "/webChat";
		}

		/** 用户类型 */
		interface UserType {
			String 前台用户 = "TY001";
			String 客服 = "TY002";
			String 游客 = "TY003";
			String 后台用户 = "TY004";
		}

		/** 方法注册 */
		interface MethodRegist {
			String 登录 = "login";
			String 聊天 = "chat";
			String 查询上下级 = "users";
			String 下线 = "disconnect";
			String 上线 = "connect";
			String 分配客服 = "getService";
			String 断开客服 = "cutService";
			String 资讯推送 = "inforPush";
			String 断开连接 = "exit";
			String 退出 = "loginout";
		}

		/** 上下级关系 */
		interface RelationType {
			String 下级 = "RE001";
			String 上级 = "RE002";
		}

		/** 在线状态 */
		interface OnLine {
			String 在线 = "ON";
			String 离线 = "OFF";
		}

		/** 客服最大交流人数 */
		interface maxConnetNu {
			int 最大服务用户人数 = 100;
			int 最大服务游客人数 = 100;
		}

		/** 消息状态 */
		interface logicStatus {
			String 未读 = "ST001";
			String 已读 = "ST002";
		}
		
		/** 聊天权限 */
		interface ChatPrivileges {
			String 聊天权限key = "cm.mgr.database.chat.ChatModel:chat";
		}

	}

	/**
	 * 资讯常量
	 *
	 */
	interface Information {

		/** 资讯类型 */
		interface ContentType {
			String 系统消息 = "TY001";
			String 个人消息 = "TY002";
		}

		/** 资讯状态 */
		interface LogicStatus {
			String 未发布 = "ST001";
			String 已发布 = "ST002";
		}

		/** 提示级别 */
		interface PromptLevel {
			String 不提示 = "ST001";
			String 右下角弹窗 = "ST002";
			String 屏幕固定区域跑马灯 = "ST003";
			String 登陆后固定弹窗 = "ST004";
		}

		
		/** 接收者类型 */
		interface RecipientType {
			String 所有人 = "ST001";
			String 指定用户 = "ST002";
		}
		
		/** 信息状态 */
		interface AccountLogicStatus {
			String 未读 = "ST001";
			String 已读 = "ST002";
		}
	}

	/**
	 * 契约类常量
	 *
	 */
	interface Contract {

		interface ConfigKey {
			String 配置系统契约结算日期 = "clear_day";
			String 配置系统契约最大条款数 = "max_terms";
		}

		/** 契约状态 */
		interface LogicType {
			String 等待下级处理 = "ST000";
			String 拒绝 = "ST001";
			String 生效 = "ST002";
			String 过期 = "ST003";
			String 临时待确认 = "ST004";
		}

		/** 结算类型 */
		interface ClearType {
			String 月结 = "CT001";
			String 半月结 = "CT002";
		}

		/** 签发类型 */
		interface AllottType {
			String 系统签订 = "AT001";
			String 代理签订 = "AT002";
		}

		/** 发放状态型 */
		interface AllotStatus {
			String 结算中 = "AS000";
			String 未发放 = "AS001";
			String 已发放 = "AS100";
			String 已领取 = "AS101";
			String 已拒绝 = "AS102";
		}

		/** 契约收益系统模块 */
		interface BonusModel {
			String 返点 = "REBATES";
			String 佣金 = "COMMISSION";
			String 活动 = "ACTIVITY";
		}

		/** 契约收益系统模块 */
		interface BonusType {
			String 返点 = "BT101";
			//佣金
			String 投注佣金 = "BT201";
			String 亏损佣金 = "BT202";
			String 充值佣金 = "BT203";
			String 有效用户佣金 = "BT204";
			String 日工资佣金 = "BT205";
			//活动 参见活动常量 BonusType
			String 红包活动 = "BT301";
			String 绑定银行卡活动 = "BT302";
			String 注册活动表 = "BT303";
			String 充值赠送活动 = "BT304";
			String 累计投注活动 = "BT305";
			String 日工资活动 = "BT306";
		}

		/** 契约条款类型 */
		interface TermsType {
			String 保底分红 = "TT001";
			String 销量分红 = "TT002";
		}
	}

	/**
	 * 彩票类常量
	 *
	 */
	interface Lottery {

        interface KillStatus{
            String 杀号中 = "killing";
            String 开奖中 = "opening";
        }
        
        interface KillType{
            String 投注金额 = "KT001";
            String 中奖金额 = "KT002";
            String 盈利金额 = "KT003";
        }
        
		interface Code {
			String 福彩3D = "fc3d";
			String 北京PK10 = "bjpk10";
			String 重庆时时彩 = "cqssc";
			String 广东十一选五 = "gd11x5";
			String 江西十一选五 = "jx11x5";
			String 排列三五 = "pl5";
			String 山东十一选五 = "sd11x5";
			String 上海时时乐 = "shssl";
			String 天津时时彩  = "tjssc";
			String 新疆时时彩 = "xjssc";
			String 腾讯分分彩  = "txffc";
			String 分分彩60 = "ffc60";
			String 分分彩120 = "ffc120";
			String 分分11X5 = "ff11x5";
			String 分分PK10 = "ffpk10";
		}
		
		interface LotteryStatus {
			String 销售中 = "ST001";
			String 停止销售 = "ST002";
		}

		interface buyMode {
			String WEB投注 = "BM001";
			String PC投注 = "BM002";
			String IOS投注 = "BM003";
			String ANDROID投注 = "BM004";
			String MOBILE浏览器 = "BM005";
		}
		
		interface hotOmitType{
			int 直选 = 1;
			int 组选 = 2;
			int 和值 = 3;
			int 跨度 = 4;
			int 和值尾数 = 5;
		}

		interface TLotteryHistory {
			String 销售中 = "ST001";
			String 截止销售 = "ST002";
			String 开奖中 = "ST003";
			String 已开奖 = "ST004";
			String 官方跳开 = "ST005";
			String 停止销售 = "ST006";
			String 已撤单 = "ST007";
			String 未销售 = "ST008";
		}

		interface TLotteryOrder {
			String 可撤单 = "ST001";
			String 等待开奖 = "ST002";
			String 已中奖 = "ST003";
			String 未中奖 = "ST004";
			String 自己撤单 = "ST005";
			String 系统撤单 = "ST006";
			String 撤销奖金 = "ST007";
		}

		
		interface IsKill {
			int 否 = 0;
			int 是 = 1;
		}
		
		interface TLotteryKind {
			String 高频彩 = "LK001";
			String 低频彩 = "LK002";
			String 超高频彩 = "LK003";
		}

		interface TOrderBonus {
			String 已派奖 = "ST001";
			String 未派奖 = "ST002";
			String 撤销奖金 = "ST003";
		}

		interface LotteryOrderSourceType {
			String 订单资源 = "ST001";
			String 返点资源 = "ST002";
		}

		interface TSystemBetModel {
			String 开启 = "ST001";
			String 禁用 = "ST002";
		}
		
		interface TSystemBetModelIsDefault {
			int 否 = 0;
			int 是 = 1;
		}

		interface TLotteryOrderChase {
			String 进行中 = "ST001";
			String 已完成 = "ST002";
			int 是否跳开即停_否 = 0;
			int 是否跳开即停_是 = 1;
			int 是否中奖即停_否 = 0;
			int 是否中奖即停_是 = 1;
			interface ChaseType {
				int 同倍追号 = 0;
				int 翻倍追号 = 1;
				int 利润追号 = 2;
			}

			interface repealType {
				String 用户普通订单撤单 = "ST001";
				String 系统撤单 = "ST002";
				String 用户追号订单撤单 = "ST003";
			}
			
			
			interface repealRange {
				String 停止彩票撤单 = "ST001";
			}
		}

		interface TOrderChaseExpect {
			String 进行中 = "ST001";
			String 已完成 = "ST002";
			String 取消 = "ST003";
			String 用户撤单 = "ST001";
			String 系统撤单 = "ST002";
		}

		/* 订单查询范围 */
		interface RangeType {
			String 团队 = "RT001";
			String 个人 = "RT002";
		}

		/*彩票停止类型*/
		interface StopType {
			String 立即停止 = "ST001";
			String 延迟停止 = "ST002";
		}
		
		/*彩票停止范围*/
		interface StopRange {
			String 停止销售 = "ST001";
			String 停止采集和销售 = "ST002";
		}
		
		/*彩票开始范围*/
		interface StartRange {
			String 开始采集 = "ST001";
			String 开始采集和销售 = "ST002";
		}
		
	}

	/**
	 * 调度常量
	 * 
	 * @author zev
	 *
	 */
	interface Scheduler {
		/** 彩票系统触发器 */
		String LOTTERY_TRIGGER_GROUP = "lottery.trigger";
		/** 彩票系统分组 */
		String LOTTERY_JOB_GROUP = "lottery.job";
		/** 北京PK10 */
		String LOTTERY_JOB_BJPK10 = "bjpk10";
		/** 彩猫PK10 */
		String LOTTERY_JOB_FFPK10 = "ffpk10";
		/** 重庆时时彩 */
		String LOTTERY_JOB_CQSSC = "cqssc";
		/** 福彩3D */
		String LOTTERY_JOB_FC3D = "fc3d";
		/** 广东十一选五 */
		String LOTTERY_JOB_GD11X5 = "gd11x5";
		/** 香港六合彩 */
		String LOTTERY_JOB_HK6 = "hk6";
		/** 黑龙江时时彩 */
		String LOTTERY_JOB_HLJSSC = "hljssc";
		/** 江西十一选五 */
		String LOTTERY_JOB_JX11X5 = "jx11x5";
		/** 排列三 */
		String LOTTERY_JOB_PL3 = "pl3";
		/** 排列五 */
		String LOTTERY_JOB_PL5 = "pl5";
		/** 山东十一选五 */
		String LOTTERY_JOB_SD11X5 = "sd11x5";
		/** 上海时时乐 */
		String LOTTERY_JOB_SHSSL = "shssl";
		/** 天津时时彩 */
		String LOTTERY_JOB_TJSSC = "tjssc";
		/** 新疆时时彩 */
		String LOTTERY_JOB_XJSSC = "xjssc";
		/** 报表系统触发器 */
		String REPORT_TRIGGER_GROUP = "report.trigger";
		/** 彩票系统分组 */
		String REPORT_JOB_GROUP = "report.job";
	}
	
	/**
	 * 报表
	 * @author zev
	 *
	 */
	interface Report{
		interface RedisKey{
			String 每日提现统计 = "withdrawals_day";
			String 每日充值统计 = "deposit_day";
			String 每日投注统计 = "lottery_order_day";
			String 每日佣金统计 = "commission_day";
			String 每日活动统计 = "activity_day";
			String 每日分红统计 = "contract_day";
			String 每日销量统计 = "order_sales_day";
		}
		
		interface RechargeWithdrawalsType{
			String 提现 = "RT001";
			String 快捷支付充值 = "RT002";
			String 网银充值 = "RT003";
			String 微信充值 = "RT004";
			String 支付宝充值 = "RT005";
		}
		
		interface ActivityType{
			String 日工资 = "RT001";
			String 注册 = "RT002"; 
			String 红包 = "RT003"; 
			String 累计充值 = "RT004";
			String 绑卡 = "RT005";
			String 累计投注 = "RT006";
		}
		
		interface CommissionType{
			String 充值佣金 = "RT001";
			String 用户佣金 = "RT002";
			String 亏损佣金 = "RT003";
			String 投注佣金 = "RT004";
			String 日工资佣金 = "RT005";
		}
		
		interface ContractType{
			String 半月分红 = "RT001";
			String 月分红 = "RT002";
		}
		
		/**
		 * 时间区间类型
		 *
		 */
		interface  TimeInterval{
			String 今日 = "TI001";
			String 过去7天 = "TI007";
			String 过去10天 = "TI010";
			String 过去15天 = "TI015";
			String 过去30天 = "TI030";
			String 过去60天 = "TI060";
			String 自选 = "TI000";
		}

	}
}
