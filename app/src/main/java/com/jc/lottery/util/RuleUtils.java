package com.jc.lottery.util;

import android.text.TextUtils;

import com.jc.lottery.bean.VictoryRuleBean;
import com.jc.lottery.bean.resp.Resp_ruleInfo;

import java.util.List;

/**
 * @ Create_time: 2019/1/31 on 12:19.
 * @ description：
 * @ author: vchao  blog: http://blog.csdn.net/zheng_weichao
 */
public class RuleUtils {
    public static void init90x5Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.s90x5_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.s90x5_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.s90x5_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
                Config.s90x5_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.s90x5_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.s90x5_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.s90x5_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.s90x5_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }


    public static void init37x6Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.s37x6_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.s37x6_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.s37x6_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
                Config.s37x6_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.s37x6_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.s37x6_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.s37x6_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.s37x6_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }

    public static void init49x6Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.s49x6_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.s49x6_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.s49x6_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            }
//            else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
//                Config.s49x6_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
//            }
            else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.s49x6_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.s49x6_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.s49x6_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.s49x6_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }

    public static void init36x7Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.s36x7_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.s36x7_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.s36x7_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
                Config.s36x7_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.s36x7_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.s36x7_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.s36x7_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.s36x7_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }

    public static void initPL5Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.PL5_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.PL5_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.PL5_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
                Config.PL5_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.PL5_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.PL5_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.PL5_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.PL5_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }

    public static void initKL8Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.KL8_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.KL8_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.KL8_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
                Config.KL8_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.KL8_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.KL8_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.KL8_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.KL8_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }

    public static void initK3Rule(List<Resp_ruleInfo.RuleListBean> ruleList) {
        for (Resp_ruleInfo.RuleListBean ruleListBean : ruleList) {
            if (TextUtils.equals(ruleListBean.getRuleNum(), "R001")) {
                Config.K3_R001_NoteNum_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R002")) {
                Config.K3_R002_NoteMultiple_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R003")) {
                Config.K3_R003_NotePeriod_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R004")) {
                Config.K3_R004_NoteBonus_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R005")) {
                Config.K3_R005_NoteMoney_max = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R006")) {
                Config.K3_R006_Time_End_sale = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R007")) {
                Config.K3_R007_NoteMoney_min = Integer.parseInt(ruleListBean.getNumber());
            } else if (TextUtils.equals(ruleListBean.getRuleNum(), "R008")) {
                Config.K3_R008_NoteRefund_Time_max = Integer.parseInt(ruleListBean.getNumber());
            }
        }
    }

    public static void victoryRule(List<VictoryRuleBean> ruleList) {
        for (VictoryRuleBean victoryRuleBean : ruleList) {
            if (TextUtils.equals(victoryRuleBean.getRule_num(), "R001")) {
                Config.victory_R001_NoteNum_max = Integer.parseInt(victoryRuleBean.getRule_value());
            } else if (TextUtils.equals(victoryRuleBean.getRule_num(), "R002")) {
                Config.victory_R002_NoteMultiple_max = Integer.parseInt(victoryRuleBean.getRule_value());
            } else if (TextUtils.equals(victoryRuleBean.getRule_num(), "R003")) {
                Config.victory_R003_NoteMoney_max = Integer.parseInt(victoryRuleBean.getRule_value());
            } else if (TextUtils.equals(victoryRuleBean.getRule_num(), "R004")) {
                Config.victory_R004_NoteRefund_Time_max = Integer.parseInt(victoryRuleBean.getRule_value());
            } else if (TextUtils.equals(victoryRuleBean.getRule_num(), "R005")) {
                Config.victory_R005_Note_max = Integer.parseInt(victoryRuleBean.getRule_value());
            }
        }
    }
}
