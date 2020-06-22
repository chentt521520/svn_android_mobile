package com.jc.lottery.content;

import android.graphics.Color;

/**
 * @ Create_time: 2018/09/03 on 11:03.
 * @ description:  常量，非必须情况下，请勿修改
 * @ author: vchao
 */
public class Constant {

    public static final String Game_Name_k3 = "快3";
    public static final String Game_Type_k3_value = "4";
    public static final int BLUETOOTH_REQUEST_CODE = 0x001;
    public static final String EXTRA_DEVICE_ADDRESS = "address";


    public static final String Game_Name_kl8 = "快乐8";
    public static final String Game_Name_ssq = "双色球";
    public static final String Game_Name_fc3d = "福彩3d";
    public static final String Game_Name_fc3D = "福彩3D";
    public static final String Game_Name_7lc = "七乐彩";
    public static final String Game_Name_7xc = "七星彩";
    public static final String Game_Name_pk10 = "PK10";
    public static final String Game_Name_pl3 = "排列三";
    public static final String Game_Name_pl5 = "排列五";
    public static final String Game_Name_pl5_ = "排列5";
    public static final String Game_Name_dlt = "大乐透";
    public static final String Game_Name_36x7 = "36选7";
    public static final String Game_Name_37x6 = "37选6";
    public static final String Game_Name_90x5 = "90选5";
    public static final String Game_Name_49x6 = "49选6";


    /**
     * 请求头键名
     */
    public static final String ACCEPT = "application/json";
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    public static final String SESSION_NAME = "session_name";
    public static final String SESSION_ID = "sessid";
    public static final String TOKEN = "token";

    // SharedPreference名字
    public static final String SP_NAME = "user_info";

    // 接口返回码
    public static final String CODE_SUCCESS = "00000";
    public static final String CODE_FAILED = "40001";

    // 游戏类型
    public static final String GAME_ALIAS_SSQ = "ssq";
    public static final String GAME_ALIAS_K3 = "k3";
    public static final String GAME_ALIAS_36X7 = "36x7";
    public static final String GAME_ALIAS_37X6 = "37x6";
    public static final String GAME_ALIAS_49X6 = "49x6";
    public static final String GAME_ALIAS_90X5 = "90x5";
    public static final String GAME_ALIAS_KL8 = "kl8";
    public static final String GAME_ALIAS_PL5 = "pl5";
    public static final String Jackpot = "Jackpot";

    //    玩法
    public static final String Game_Play_k3_he = "和值";
    public static final String Game_Play_k3_sth_dan = "三同号";
    public static final String Game_Play_k3_sth_tong = "三同号通选";
    public static final String Game_Play_k3_sbth = "三不同号";
    public static final String Game_Play_k3_slh = "三连号通选";
    public static final String Game_Play_k3_eth_dan = "二同号单选";
    public static final String Game_Play_k3_eth_fu = "二同号复选";
    public static final String Game_Play_k3_ebth = "二不同号";

    public static final String Game_Play_Code_k3_he = "k3001"; // 玩法 对应的玩法码
    public static final String Game_Play_Code_k3_sth_dan = "k3002";
    public static final String Game_Play_Code_k3_sth_tong = "k3007";
    public static final String Game_Play_Code_k3_sbth = "k3004";
    public static final String Game_Play_Code_k3_slh = "k3006";
    public static final String Game_Play_Code_k3_eth_dan = "k3003";
    public static final String Game_Play_Code_k3_eth_fu = "k3008";
    public static final String Game_Play_Code_k3_ebth = "k3005";

    public static final String Game_Play_Win_Level_90x5_1 = "90x5001";
    public static final String Game_Play_Win_Level_90x5_2 = "90x5002";
    public static final String Game_Play_Win_Level_90x5_3 = "90x5003";
    public static final String Game_Play_Win_Level_90x5_4 = "90x5004";
    public static final String Game_Play_Win_Level_90x5_5 = "90x5005";
    public static final String Game_Play_Win_Level_90x5_6 = "90x5006";

    public static final String Game_Play_Win_Level_37x6_1 = "37x6001";
    public static final String Game_Play_Win_Level_37x6_2 = "37x6002";
    public static final String Game_Play_Win_Level_37x6_3 = "37x6003";
    public static final String Game_Play_Win_Level_37x6_4 = "37x6004";
    public static final String Game_Play_Win_Level_37x6_5 = "37x6005";

    public static final String Game_Play_Win_Level_Jacket_1 = "pick14plus1x1001";
    public static final String Game_Play_Win_Level_Jacket_2 = "pick14plus1x1002";
    public static final String Game_Play_Win_Level_Jacket_3 = "pick14plus1x1003";
    public static final String Game_Play_Win_Level_Jacket_4 = "pick14plus1x1004";
    public static final String Game_Play_Win_Level_Jacket_5 = "pick14plus1x1005";

    public static final String Game_Play_Win_Level_49x6_1 = "49x6001";
    public static final String Game_Play_Win_Level_49x6_2 = "49x6002";
    public static final String Game_Play_Win_Level_49x6_3 = "49x6003";
    public static final String Game_Play_Win_Level_49x6_4 = "49x6004";
    public static final String Game_Play_Win_Level_49x6_5 = "49x6005";
    public static final String Game_Play_Win_Level_49x6_6 = "49x6006";
    public static final String Game_Play_Win_Level_49x6_7 = "49x6007";
    public static final String Game_Play_Win_Level_49x6_8 = "49x6008";
    public static final String Game_Play_Win_Level_49x6_9 = "49x6009";

    public static final String Game_Play_kl8_x1 = "选一";
    public static final String Game_Play_kl8_x2 = "选二";
    public static final String Game_Play_kl8_x3 = "选三";
    public static final String Game_Play_kl8_x4 = "选四";
    public static final String Game_Play_kl8_x5 = "选五";
    public static final String Game_Play_kl8_x6 = "选六";
    public static final String Game_Play_kl8_x7 = "选七";
    public static final String Game_Play_kl8_x8 = "选八";
    public static final String Game_Play_kl8_x9 = "选九";
    public static final String Game_Play_kl8_x10 = "选十";

    public static final String Game_Play_id_k3_hezhi = "hezhi";//快3 和值 玩法id
    public static final String Game_Play_id_k3_3tonghao_tong = "sth_t";//快3 三同号通选 玩法id
    public static final String Game_Play_id_k3_3tonghao_dan = "sth_d";//快3 三同号单选 玩法id
    public static final String Game_Play_id_k3_3butonghao = "sbth";//快3 三不同号  玩法id
    public static final String Game_Play_id_k3_3lianhao_tong = "slh_t";//快3 三连号通选 玩法id
    public static final String Game_Play_id_k3_2tonghao_fu = "eth_f";//快3 二同号复选 玩法id
    public static final String Game_Play_id_k3_2tonghao_dan = "eth_d";//快3 二同号单选 玩法id
    public static final String Game_Play_id_k3_2butonghao = "ebth";//快3 二不同号 玩法id  暂无从玩法

    public static final String Game_Play_id_kl8_x1 = "kl8_x1";// 快乐8 选一 玩法id
    public static final String Game_Play_id_kl8_x2 = "kl8_x2";// 快乐8 选二 玩法id
    public static final String Game_Play_id_kl8_x3 = "kl8_x3";// 快乐8 选三 玩法id
    public static final String Game_Play_id_kl8_x4 = "kl8_x4";// 快乐8 选四 玩法id
    public static final String Game_Play_id_kl8_x5 = "kl8_x5";// 快乐8 选五 玩法id
    public static final String Game_Play_id_kl8_x6 = "kl8_x6";// 快乐8 选六 玩法id
    public static final String Game_Play_id_kl8_x7 = "kl8_x7";// 快乐8 选七 玩法id
    public static final String Game_Play_id_kl8_x8 = "kl8_x8";// 快乐8 选八 玩法id
    public static final String Game_Play_id_kl8_x9 = "kl8_x9";// 快乐8 选九 玩法id
    public static final String Game_Play_id_kl8_x10 = "kl8_x10";// 快乐8 选十 玩法id

    // 投注方式
    public static final String BET_MODE_SINGLE = "01";// 单式
    public static final String BET_MODE_DOUBLE = "02";//复式
    public static final String BET_MODE_DRAG_DOUBLE = "03";//胆拖复式
    public static final String BET_MODE_COMPOUND = "04";//混合

    // 数据源
    public static final String DATA_SOURCE_ANDROID_APP_END = "3";

    //    接口 interfaceCode
    public static final String ifc_print_notice_k3 = "k3Printer";
    public static final String ifc_print_notice_kl8 = "kl8Printer";
    public static final String ifc_print_notice_36x7 = "36Selection7Printer";
    public static final String ifc_print_notice_37x6 = "37Selection6Printer";
    public static final String ifc_print_notice_49x6 = "49Selection6Printer";
    public static final String ifc_print_notice_90x5 = "90Selection5Printer";
    public static final String ifc_print_notice_pl5 = "pl5Printer";

    public static final String ifc_time_calibration = "timeCalibration";
    public static final String ifc_k3_history_query = "quick3HistoryQuery";
    public static final String ifc_kl8_history_query = "happiness8HistoryQuery";
    public static final String ifc_pl5_history_query = "array5HistoryQuery";
    public static final String ifc_36x7_history_query = "36Selection7CurrentQuery";
    public static final String ifc_37x6_history_query = "historyQuery";
    public static final String ifc_90x5_history_query = "historyQuery";

    //    广播
    public static final String action_print_notice_k3 = "action_k3_query_printer_state";
    public static final String action_print_notice_kl8 = "action_kl8_query_printer_state";
    public static final String action_print_notice_36x7 = "action_36x7_query_printer_state";
    public static final String action_print_notice_37x6 = "action_37x6_query_printer_state";
    public static final String action_print_notice_90x5 = "action_90x5_query_printer_state";
    public static final String action_print_notice_pl5 = "action_pl5_query_printer_state";

    //    多语言
    public static final String ENGLISH = "ENGLISH";
    public static final String CHINESE = "CHINESE";

    // 状态
    public static final int invalidation = Color.rgb(153,153,153);    //失效 取消
    public static final int dispatched = Color.rgb(255, 102, 0);      //待派送 支出
    public static final int chupiao_fail = Color.rgb(253,8,8);        //失败
    public static final int success = Color.rgb(0,146,61);            //已领取 成功 收入
    public static final int in_delivery = Color.rgb(146, 204, 170);   //派送中 （表示过程中）

}
