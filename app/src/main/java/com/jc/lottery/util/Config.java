package com.jc.lottery.util;


import com.jc.lottery.BuildConfig;

/**
 * @ Create_time: 2018/8/14 on 10:27.
 * @ description：设置文件，工程参数设置
 * @ author: vchao
 */
public class Config {
    // 是否开启调试模式( true 打印log / false 不打印log )
    public static final boolean DEBUG = BuildConfig.isDebug;
    public static final boolean EditTianchong =  false;
    // 是否必须登录
    public static final boolean MUST_LOGIN = true;
//    是否必须有打印机
    public static final boolean MUST_NEED_PRINTER = BuildConfig.needPrint;
//    public static final boolean MUST_NEED_PRINTER = false;
    // log的标志
    public static final String LogTag = "sxmh";
    public static final String IgnoreTag = "ignore";
    // 是否开启测试
    public static final boolean OpenTest = false;
//    是否保存account_id
    public static final boolean UseRealAccountId = true;
    public static final boolean UseScreenAccountId = true;
    // 是否使用假数据
    public static final boolean OpenFakeData = false;
    // 是否开启原来的网络请求
    public static final boolean OpenBeforeNetQuest = false;
    // 是否开启 打印页面
    public static final boolean OpenPaypage = false;
    // 摇一摇选号 摇动力度精度（数值越大，需要用越大的力气）
    public static final int ShakeAccuracy = 22;
    //    一注彩票价钱
    public static final int OneBetPrice = 500;//1000 先令
    //    生成订单号随机数范围
    public static final int RomdomValue = 200;// 从 0 开始到该值之间的随机整数
    //    下注成功返回码
    public static final String BetSuccessCode = "00000";
    // 36选7 最多和最少可选择数目
    public static final int _36x7_Code_Num_max = 20;// 20 --> 77520 注       24 ---> 346104
    public static final int _36x7_Code_Num_min = 7;

    public static final int _37x6_Code_Num_max = 6;
    public static final int _37x6_Code_Num_min = 6;

    public static int _90x5_Code_Num_max = 1;
    public static int _90x5_Code_Num_min = 1;

    //    投注方式
    public static final String eachBetMode_dan = "01"; // 01单式
    public static final String eachBetMode_fu = "02"; // 02复式
    public static final String eachBetMode_dantuo = "03";// 03 胆拖复式

    // 测试数据
    public static final String Test_Name = "17749171341";// 测试用户名
    public static final String Test_PWD = "E10ADC3949BA59ABBE56E057F20F883E"; //    测试密码（加密后）
    public static final String Test_Print_MAC = "DC:1D:30:41:C7:CE";//    打印机mac地址
    //    public static final String Test_Terminal = "1234561";//代理商编号
    public static final String Test_Terminal = "0930110344556";//设备编号
    //    public static final String Test_Terminal = "100202";//代理商编号
    public static final String Test_Terminal_id = "2";//代理商编号id
    public static final int Test_ThirdUserId = 29;//商家id
    public static final int Test_accountId = 11;//账号id
    public static final String Test_accountName = "zhengweichao";//账号名称
    public static final String Test_ThirdPartyCode = "18";//商家订单号
    public static final String Test_DrawNumber = "180917007";//奖期数

    // 快3 规则设置
    public static int K3_R001_NoteNum_max = 1000;//最高投注注数
    public static int K3_R002_NoteMultiple_max = 100;//最高投注倍数
    public static int K3_R003_NotePeriod_max = 100;//多期最多连续期数
    public static int K3_R004_NoteBonus_max = 1000000;//单注最高中奖奖金
    public static int K3_R005_NoteMoney_max = 1000000;//单张彩票最高投注金额
    public static int K3_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
    public static int K3_R007_NoteMoney_min = OneBetPrice;//最小投注金额
    public static int K3_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
    // 快乐8 规则设置
    public static int KL8_R001_NoteNum_max = 1000;//最高投注注数
    public static int KL8_R002_NoteMultiple_max = 100;//最高投注倍数
    public static int KL8_R003_NotePeriod_max = 100;//多期最多连续期数
    public static int KL8_R004_NoteBonus_max = 1000000;//单注最高中奖奖金
    public static int KL8_R005_NoteMoney_max = 1000000;//单张彩票最高投注金额
    public static int KL8_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注   单位秒
    public static int KL8_R007_NoteMoney_min = OneBetPrice;//最小投注金额
    public static int KL8_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
    // 排列5 规则设置
    public static int PL5_R001_NoteNum_max = 100;//最高投注注数
    public static int PL5_R002_NoteMultiple_max = 100;//最高投注倍数
    public static int PL5_R003_NotePeriod_max = 100;//多期最多连续期数
    public static int PL5_R004_NoteBonus_max = 1000000;//单注最高中奖奖金
    public static int PL5_R005_NoteMoney_max = 1000000;//单张彩票最高投注金额
    public static int PL5_R006_Time_End_sale = 5;//距离奖期结束倒计时内不允许投注   单位秒
    public static int PL5_R007_NoteMoney_min = OneBetPrice;//最小投注金额
    public static int PL5_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
    // 36选7 规则设置
    public static int s36x7_R001_NoteNum_max = 100;//最高投注注数
    public static int s36x7_R002_NoteMultiple_max = 100;//最高投注倍数
    public static int s36x7_R003_NotePeriod_max = 100;//多期最多连续期数
    public static int s36x7_R004_NoteBonus_max = 1000000;//单注最高中奖奖金
    public static int s36x7_R005_NoteMoney_max = 1000000;//单张彩票最高投注金额
    public static int s36x7_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
    public static int s36x7_R007_NoteMoney_min = OneBetPrice;//最小投注金额
    public static int s36x7_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟

    // 胜负彩 规则设置
    public static int victory_R001_NoteNum_max = 100;//最高投注注数
    public static int victory_R002_NoteMultiple_max = 100;//最高投注倍数
    public static int victory_R003_NoteMoney_max = 100;//单张彩票最高投注金额
    public static int victory_R004_NoteRefund_Time_max = 5;//不允许投注倒计时
    public static int victory_R005_Note_max = 15;//投注赛事场次
//    public static int s36x7_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
//    public static int s36x7_R007_NoteMoney_min = OneBetPrice;//最小投注金额
//    public static int s36x7_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟

    // 37选6 规则设置
//    public static int s37x6_R001_NoteNum_max = 1000;//最高投注注数
//    public static int s37x6_R002_NoteMultiple_max = 100;//最高投注倍数
//    public static int s37x6_R003_NotePeriod_max = 100;//多期最多连续期数
//    public static int s37x6_R004_NoteBonus_max = 100000;//单注最高中奖奖金
//    public static int s37x6_R005_NoteMoney_max = 100000;//单张彩票最高投注金额
//    public static int s37x6_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
//    public static int s37x6_R007_NoteMoney_min = OneBetPrice;//最小投注金额
//    public static int s37x6_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
    public static int s37x6_R001_NoteNum_max = 0;//最高投注注数
    public static int s37x6_R002_NoteMultiple_max = 0;//最高投注倍数
    public static int s37x6_R003_NotePeriod_max = 0;//多期最多连续期数
    public static int s37x6_R004_NoteBonus_max = 100000;//单注最高中奖奖金
    public static int s37x6_R005_NoteMoney_max = 0;//单张彩票最高投注金额
    public static int s37x6_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
    public static int s37x6_R007_NoteMoney_min = 0;//最小投注金额
    public static int s37x6_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟

    public static int s49x6_R001_NoteNum_max = 0;//最高投注注数
    public static int s49x6_R002_NoteMultiple_max = 0;//最高投注倍数
    public static int s49x6_R003_NotePeriod_max = 0;//多期最多连续期数
    public static int s49x6_R004_NoteBonus_max = 100000;//单注最高中奖奖金
    public static int s49x6_R005_NoteMoney_max = 0;//单张彩票最高投注金额
    public static int s49x6_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
    public static int s49x6_R007_NoteMoney_min = 0;//最小投注金额
    public static int s49x6_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
    // 90选5 规则设置
//    public static int s90x5_R001_NoteNum_max = 1000;//最高投注注数
//    public static int s90x5_R002_NoteMultiple_max = 100;//最高投注倍数
//    public static int s90x5_R003_NotePeriod_max = 100;//多期最多连续期数
//    public static int s90x5_R004_NoteBonus_max = 10000000;//单注最高中奖奖金
//    public static int s90x5_R005_NoteMoney_max = 10000000;//单张彩票最高投注金额
//    public static int s90x5_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
//    public static int s90x5_R007_NoteMoney_min = OneBetPrice;//最小投注金额
//    public static int s90x5_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
    public static int s90x5_R001_NoteNum_max = 0;//最高投注注数
    public static int s90x5_R002_NoteMultiple_max = 0;//最高投注倍数
    public static int s90x5_R003_NotePeriod_max = 0;//多期最多连续期数
    public static int s90x5_R004_NoteBonus_max = 10000000;//单注最高中奖奖金
    public static int s90x5_R005_NoteMoney_max = 0;//单张彩票最高投注金额
    public static int s90x5_R006_Time_End_sale = 30;//距离奖期结束倒计时内不允许投注  单位秒
    public static int s90x5_R007_NoteMoney_min = 0;//最小投注金额
    public static int s90x5_R008_NoteRefund_Time_max = 5;//退票时间  单位分钟
}
