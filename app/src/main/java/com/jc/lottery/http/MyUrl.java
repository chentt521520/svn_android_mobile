package com.jc.lottery.http;


import com.jc.lottery.BuildConfig;

/**
 * @ Create_time: 2018/9/4 on 16:28.
 * @ description：网址
 * @ author: vchao
 */
public class MyUrl {
//    public static final String Host = BuildConfig.host;// 真正的接口 服务器地址（无线pos专用）
    public static final String Host = BuildConfig.host;// 真正的接口 服务器地址（无线pos专用）
    public static final String JKC_Host = "http://jicaikeji.qicp.vip:8888/instant";// 即开彩 服务器地址（无线pos专用）
    public static final String Newest_Host = "http://jicaikeji.qicp.vip:8888";
    public static final String Update_Host = "http://jicaikeji.qicp.vip:8887";
//    public static final String JKC_Host = "http://coreapi.easy.win:8888/instant";// 即开彩 服务器地址（无线pos专用）
//    public static final String Newest_Host = "http://coreapi.easy.win:8888";
//    public static final String Update_Host = "http://lotto.easy.win";
    public static final String Video_Host = "http://18.195.243.225:8001";//直播回放地址
    public static final String Down_pre = BuildConfig.download;// 下载前缀地址（版本更新使用）

    public static final String pos_drawNotOpenQuery = Newest_Host + "/lottery/draw/drawNotOpenQuery";//  奖期查询（无线pos）（真）
    public static final String new_queryGameList = Newest_Host + "/lottery/game/queryGameList";//  玩法查询（游戏查询）（无线pos）
    public static final String pos_selectGameAdd = Newest_Host + "/order/happiness8/selectGameAdd";//  玩法查询（游戏查询）（无线pos）
    public static final String new_pos_gamePlayQuery = Host + "/gamePlay/gamePlayQuery";// 玩法查询（无线pos）
    public static final String queryGameList = Host + "/gameWx/queryGameList";//  玩法查询（游戏查询）（无线pos）
    public static final String pos_gamePlayQuery = Host + "/happiness8Wx/gamePlayQuery";// 玩法查询（无线pos）

    public static final String pos_quick3_appOrder = Host + "/quick3Wx/wxOrder";// 快3 投注（无线pos）
    public static final String pos_happiness8_appOrder = Host + "/happiness8Wx/wxOrder";// 快乐8 投注（无线pos）
    public static final String pos_array5_appOrder = Host + "/array5Wx/wxOrder";// 排列5 投注（无线pos）
    public static final String pos_36Selection7_appOrder = Host + "/36Selection7Wx/wxOrder";// 36选7 投注（无线pos）
    public static final String pos_37x6_appOrder = Newest_Host + "/order/37Selection6/terminalOrder";// 37选6 投注（无线pos）
    public static final String pos_49x6_appOrder = Newest_Host + "/order/49Selection6/terminalOrder";// 49选6 投注（无线pos）
//    public static final String pos_90x5_appOrder = Host + "/90x5Wx/wxOrder";// 90选5 投注（无线pos）
    public static final String pos_90x5_appOrder = Newest_Host + "/order/90Selection5/terminalOrder";// 90选5 投注（无线pos）

    public static final String pos_noteRecordQuery = Host + "/user/noteRecordQuery";//  投注记录查询（无线pos）
//    public static final String pos_findRule = Host + "/queryWx/findRule";//  规则查询（无线pos）
    public static final String pos_findRule = Newest_Host + "/order/order/findRule";//  规则查询（无线pos）
    public static final String pos_refundTicket = Host + "/queryWx/refundTicket";// 退票 （无线pos）
    public static final String pos_userLogin = Host + "/login/terminalLogin";// 登陆部分服务器地址 （无线pos）
    public static final String pos_login = Host + "/user/login";// 新登陆部分服务器地址 （无线pos）
    public static final String pos_terminalActivation = Host + "/login/terminalActivation";// 设备激活（无线pos）
    public static final String pos_userTypeQuery = Host + "/login/userTypeQuery";// 用户类型信息查询（管理员，非管理员） （无线pos）
    public static final String pos_userBalanceQuery = Host + "/user/balanceQuery";// 用户余额查询 （无线pos）
    public static final String pos_rechargeMoney = Host + "/recharge/rechargeMoney";// 余额充值 （无线pos）
    public static final String pos_rechargeCardQuery = Host + "/recharge/rechargeCardQuery";//充值卡信息核查（无线pos）
    public static final String pos_rechargeQuery = Host + "/recharge/rechargeQuery";// 余额充值记录查询 （无线pos）
    public static final String pos_withdrawCash = Host + "/recharge/withdrawCash";// 提现 （无线pos）
    public static final String pos_fundDetails = Host + "/recharge/fundDetails";// 资金明细 （无线pos）
    public static final String pos_k3_notice = Host + "/notice/k3Notice";// 快3 打印通知（无线pos）
    public static final String pos_kl8_notice = Host + "/notice/kl8Notice";// 快乐8 打印通知（无线pos）
    public static final String pos_36x7_notice = Host + "/notice/36Selection7Notice";// 36选7 打印通知（无线pos）
//    public static final String pos_37x6_notice = Newest_Host + "/pay/printer/37Selection6Printer";// 37选6 打印通知（无线pos）
    public static final String pos_37x6_notice = Newest_Host + "/order/printer/37Selection6Printer";// 37选6 打印通知（无线pos）
//    public static final String pos_49x6_notice = Newest_Host + "/pay/printer/49Selection6Printer";// 49选6 打印通知（无线pos）
    public static final String pos_49x6_notice = Newest_Host + "/order/printer/49Selection6Printer";// 49选6 打印通知（无线pos）
//    public static final String pos_90x5_notice = Newest_Host + "/pay/printer/90Selection5Printer";// 90选5 打印通知（无线pos）
    public static final String pos_90x5_notice = Newest_Host + "/order/printer/90Selection5Printer";// 90选5 打印通知（无线pos）
    public static final String pos_pl5_notice = Host + "/notice/pl5Notice";// 排列5 打印通知（无线pos）
//    public static final String pos_app_update = Host + "/version/publishedVersionQuery";// 版本更新（无线pos）
    public static final String pos_app_update = Update_Host + "/userapi/queryupdate";// 版本更新（无线pos）
    public static final String pos_advertising_query  = Host + "/queryWx/advertisingQuery";// 票面广告查询
    public static final String screen_k3_currentQuery = Host + "/quick3/currentQuery";// 快3查询当前奖期开奖结果
    public static final String screen_k3_History = Host + "/quick3/historyQuery";// 快3 查询历史开奖结果
    public static final String screen_kl8_History = Host + "/happiness8/historyQuery";// 快乐8 查询历史开奖结果
    public static final String screen_pl5_History = Host + "/array5/historyQuery";// 排列5 查询历史开奖结果
    public static final String screen_36x7_History = Host + "/36Selection7/historyQuery";// 36选7 查询历史开奖结果
    public static final String pos_Validation = Host + "/login/validation";// 二级密码验证
    public static final String screen_37x6_History = Newest_Host + "/screen/37Selection6/historyQuery";// 37选6 查询历史开奖结果
    public static final String screen_49x6_History = Newest_Host + "/screen/49Selection6/historyQuery";// 49选6 查询历史开奖结果
    public static final String screen_90x5_History = Newest_Host + "/screen/90Selection5/historyQuery";// 90选5 查询历史开奖结果
    public static final String screen_37x6_CurrentQuery = Newest_Host + "/screen/37Selection6/currentQuery";// 37选6 查询当前开奖结果
    public static final String screen_49x6_CurrentQuery = Newest_Host + "/screen/49Selection6/currentQuery";// 37选6 查询当前开奖结果
    public static final String screen_90x5_CurrentQuery = Newest_Host + "/screen/90Selection5/currentQuery";// 90选5 查询当前开奖结果
    public static final String screen_time_Calibration = Newest_Host + "/screen/quick3/timeCalibration";// 时间校准
    public static final String settleTimeQuery = Newest_Host + "/order/settle/settleTimeQuery";// 最新结算时间
    public static final String settleQuery = Newest_Host + "/order/settle/settleQuery";// 乐透结算信息查询
    public static final String rechargeRecord = Newest_Host + "/pay/queryInfo/rechargeRecord";// 乐透充值记录
    public static final String rechargeDetail = Newest_Host + "/pay/queryInfo/rechargeDetail";// 乐透充值详情
    public static final String settleSettlement = Newest_Host + "/order/settle/settlement";// 乐透结算
    public static final String settleSettleRecord = Newest_Host + "/order/settle/settleRecord";// 乐透结算列表
    public static final String settleSettleDetails = Newest_Host + "/order/settle/settleDetails";// 乐透结算详情
    public static final String historyBettingQuery = Newest_Host + "/order/terminal/historyBettingQuery";// 乐透投注记录
    public static final String cashPrizeQuery = Newest_Host + "/order/terminal/cashPrizeQuery";// 乐透兑奖查询
    public static final String terminalCashPrize = Newest_Host + "/order/terminal/terminalCashPrize";// 乐透兑奖
    public static final String refundTicket = Newest_Host + "/order/order/refundTicket";// 乐透退票
    public static final String amountQuery = Newest_Host + "/lottery/userInfo/amountQuery";// 乐透账户余额
    public static final String terminalStatistics = Newest_Host + "/lottery/homePage/terminalStatistics";// 乐透销量统计
    public static final String historyBetDetail = Newest_Host + "/order/terminal/historyBetDetail";// 乐透兑奖、投注详情
    public static final String pos_37Selection6terminalOrder = Newest_Host + "/order/37Selection6/terminalOrder";// 乐透投注37*6
    public static final String screen_drawNoticeQueryList = Host + "/queryWx/drawNoticeQueryList";//  开奖信息（门户） 开奖公告详情
    public static final String screen_drawNoticeQueryDetail = Newest_Host + "/gateway/drawNotice/drawNoticeQuery";//  开奖信息（门户） 开奖公告详情
    public static final String password_update = Host + "/login/passwordUpdate";//  修改密码
    public static final String password_secondaryUpdate = Host + "/login/secondaryUpdate";//  修改二级密码
    public static final String terminalPerQuery = Host + "/query/terminalPerQuery";//  设备权限获取
    public static final String pos_GetWalleQuery = Host + "/wallet/detailQuery";// 钱包流水
//    public static final String pos_LoginExit = Host + "/user/loginExit";// 退出 （无线pos）
    public static final String pos_terminalLoginExit = Host + "/login/terminalLoginExit";// 退出 （无线pos）
    public static final String Down_Update = Host + "/version/versionDownload?versionNum=";// 下载（版本更新使用）

    public static final String pos_yoPaymentsPay = Down_pre + "/jcwxPay/paymentWx/yoPaymentsPay";// YoPay充值 （无线pos）
    public static final String pos_aPayCreateOrder = Down_pre + "/jcwxPay/paymentWx/aPayCreateOrder";// aPay创建支付订单 （无线pos）
    public static final String pos_PayRecharge = Down_pre + "/pay/recharge/recharge";// 新版充值 （无线pos）
    public static final String pos_GetRatio = Down_pre + "/pay/recharge/getRatio";// 即开彩获取充值比例 （无线pos）

    public static final String pos_CashRecordInfo = JKC_Host + "/prize/rewardRecord";// 兑奖记录（无线pos）
    public static final String pos_GetRecordInfo = JKC_Host + "/logistics/getRecord";// 领取记录 （无线pos）
    public static final String pos_GetCashInfo = JKC_Host + "/prize/cash";// 兑奖 （无线pos）
    public static final String pos_GetWinQueryInfo = JKC_Host + "/prize/winQuery";// 中奖查询 （无线pos）
    public static final String pos_GetGameQueryInfo = JKC_Host + "/query/gameType";// 游戏类型查询 （无线pos）
    public static final String pos_GetStatusQueryInfo = JKC_Host + "/logistics/getStatus";// 游戏类型查询 （无线pos）
    public static final String pos_CreateOrderInfo = JKC_Host + "/logistics/createOrder";// 创建订单 （无线pos）
    public static final String pos_GetRecordDetails = JKC_Host + "/query/getRecordDetails";// 领取记录详情查询 （无线pos）
    public static final String pos_GetRewardDetail = JKC_Host + "/prize/rewardDetail";// 兑奖记录详情查询 （无线pos）
    public static final String pos_GetActivation = JKC_Host + "/query/activation";// 即开彩激活 （无线pos）
    public static final String pos_GetActivationQuery = JKC_Host + "/query/activationQuery";// 即开彩激活状态信息查询 （无线pos）
    public static final String pos_GetBookNumQuery = JKC_Host + "/settlement/bookNumQuery";// 即开彩结算本号查询 （无线pos）
    public static final String pos_GetSettlementQuery = JKC_Host + "/settlement/query";// 即开彩结算信息查询 （无线pos）
    public static final String pos_GetSettlementSettlement = JKC_Host + "/settlement/settlement";// 即开彩结算 （无线pos）
    public static final String pos_GetSettlementRecord = JKC_Host + "/settlement/settlementRecord";// 即开彩结算列表 （无线pos）
    public static final String pos_GetSettlementDetails = JKC_Host + "/settlement/settlementDetails";// 即开彩结算详情 （无线pos）
    public static final String pos_GetSettlementCheck = JKC_Host + "/settlement/check";// 即开彩结算详情审核 （无线pos）
    public static final String pos_GetSettlementBookQuery = JKC_Host + "/settlement/bookQuery";// 即开彩结算本号查询 （无线pos）
    public static final String pos_GetOrderPay = JKC_Host + "/orderPay/payment";// 即开彩购彩订单支付 （无线pos）
    public static final String pos_GetAmountQuery= JKC_Host + "/query/amountQuery";// 即开彩账户金额查询 （无线pos）
    public static final String pos_GetPaymentRecordQuery= JKC_Host + "/orderPay/paymentRecord";// 即开彩支付记录查询 （无线pos）
    public static final String pos_GetCartonNoQuery= JKC_Host + "/logistics/getCartonNo";// 即开彩箱号查询 （无线pos）
    public static final String pos_GetLogisticsDelivery= JKC_Host + "/logistics/delivery";// 即开彩派送 （无线pos）
    public static final String pos_GetQueryBookNo= JKC_Host + "/query/getBookNo";// 即开彩查询本号重复状态 （无线pos）
    public static final String pos_GetStatisticsAmount= JKC_Host + "/statistics/amount";// 数据统计查询 （无线pos）
    public static final String pos_GetLogisticsCancel= JKC_Host + "/logistics/cancel";// 取消订单 （无线pos）
    public static final String pos_GetCloseOrder= JKC_Host + "/logisticsBack/closeOrder";// 关闭订单 （无线pos）
    public static final String pos_GetAuditOrder= JKC_Host + "/logisticsBack/auditOrder";// 审核关闭订单 （无线pos）
    public static final String pos_GetOrderTracking= JKC_Host + "/query/orderTracking";// 订单追踪 （无线pos）
    public static final String pos_GetManagement= JKC_Host + "/management/getManagement";// 查询物流信息 （无线pos）
    public static final String pos_GetActivateRecord= JKC_Host + "/query/activateRecord";// 激活记录 （无线pos）
    public static final String pos_GetDeliveryDetailsQuery= JKC_Host + "/logisticsBack/deliveryDetailsQuery";// 激活记录详情 （无线pos）
    public static final String pos_deliveryDetailsQuery= JKC_Host + "/logisticsBack/deliveryDetailsQuery";//激活详情查询 （无线pos）
//    public static final String pos_GetWalleQuery = Host + "/walleBack/walleQuery";// 即开彩结算本号查询 （无线pos）

    //胜负彩
    public static final String pos_GetMatchQuery= Newest_Host + "/victory/match/matchQuery";//赛事查询 （无线pos）
    public static final String pos_GetTicketQuery= Newest_Host + "/victory/ticket/ticketQuery";//投注/兑奖记录查询 （无线pos）
    public static final String pos_GetTicketDetail= Newest_Host + "/victory/ticket/ticketDetail";//投注/兑奖记录详情 （无线pos）
    public static final String pos_GetSettleTime= Newest_Host + "/victory/settle/settleTime";//结算时间查询 （无线pos）
    public static final String pos_GetSettlePreview= Newest_Host + "/victory/settle/settlePreview";//结算预览 （无线pos）
    public static final String pos_GetSettleRecord = Newest_Host + "/victory/settle/settleRecord";// 胜负彩结算列表
    public static final String pos_GetSettleDetails = Newest_Host + "/victory/settle/settleDetail";// 胜负彩结算详情
    public static final String pos_GetSettlement = Newest_Host + "/victory/settle/settlement";// 胜负彩结算
    public static final String pos_GetBackAdminSale= Newest_Host + "/victory/backAdminSale/homePage";// 胜负彩销量查询 （无线pos）
    public static final String pos_ruleSfcQuery = Newest_Host + "/victory/match/ruleSfcQuery";//  胜负彩规则查询（无线pos）
    public static final String pos_victoryBetting = Newest_Host + "/victory/match/betting";//  胜负彩投注（无线pos）
    public static final String pos_victoryPrintNotice = Newest_Host + "/victory/match/printNotice";//  胜负彩出票（无线pos）
    public static final String pos_victoryRedeemQuery = Newest_Host + "/victory/cashPrize/redeemQuery";//  胜负彩兑奖查询（无线pos）
    public static final String pos_victoryRedeem = Newest_Host + "/victory/cashPrize/redeem";//  胜负彩兑奖（无线pos）
    public static final String pos_listQuery = Newest_Host + "/victory/drawNotice/listQuery";//  胜负彩开奖公告列表（无线pos）
    public static final String pos_drawNoticeDetail = Newest_Host + "/victory/drawNotice/detail";//  胜负彩开奖公告详情（无线pos）

    //开奖直播
    public static final String pos_GetElectronicPrizeRecord= Newest_Host + "/thirdparty/query/liveRecord";//开奖记录查询 （无线pos）
    public static final String pos_GetElectronicLiveVideo= Newest_Host + "/thirdparty/query/liveVideo";//开奖视频获取 （无线pos）
    public static final String pos_GetHistoryLottery= Newest_Host + "/thirdparty/query/historyLottery";//历史开奖直播记录 （无线pos）
    public static final String pos_GetElectronicLiveVideoTest= Newest_Host + "/cy_admin/acdn";//开奖视频获取 （无线pos 测试）
}
