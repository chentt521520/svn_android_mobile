package com.jc.lottery.util;

import android.content.Context;
import android.text.TextUtils;

import com.jc.lottery.R;
import com.jc.lottery.content.Constant;

/**
 * @ Create_time: 2018/9/18 on 21:44.
 * @ description：
 * @ author: vchao
 */
public class TransfromUtils {

    /**
     * 游戏别名 --->  游戏名
     *
     * @param game_alias
     * @return
     */
    public static String GameAlias2Name(String game_alias, String default_value) {
        String re_value = "";
        switch (game_alias) {
            case Constant.GAME_ALIAS_36X7:
                re_value = Constant.Game_Name_36x7;
                break;
            case Constant.GAME_ALIAS_K3:
                re_value = Constant.Game_Name_k3;
                break;
            case Constant.GAME_ALIAS_KL8:
                re_value = Constant.Game_Name_kl8;
                break;
            case Constant.GAME_ALIAS_PL5:
                re_value = Constant.Game_Name_pl5;
                break;
            case Constant.GAME_ALIAS_37X6:
                re_value = Constant.Game_Name_37x6;
                break;
            case Constant.GAME_ALIAS_90X5:
                re_value = Constant.Game_Name_90x5;
                break;
            default:
                re_value = default_value;
                break;

        }
        return re_value;
    }

    public static String GameAlias2Name(Context context, String game_alias, String default_value) {
        String re_value = "";
        switch (game_alias) {
            case Constant.GAME_ALIAS_36X7:
                re_value = context.getString(R.string.s36x7name);
                break;
            case Constant.GAME_ALIAS_K3:
                re_value = context.getString(R.string.kuai3_name);
                break;
            case Constant.GAME_ALIAS_KL8:
                re_value = context.getString(R.string.game_name_kl8);
                break;
            case Constant.GAME_ALIAS_PL5:
                re_value = context.getString(R.string.game_name_pl5);
                break;
            case Constant.GAME_ALIAS_37X6:
                re_value = Constant.Game_Name_37x6;
                re_value = context.getString(R.string.s37x6name);
                break;
            case Constant.GAME_ALIAS_90X5:
                re_value = context.getString(R.string.s90x5name);
                break;
            default:
                re_value = default_value;
                break;

        }
        return re_value;
    }

    /**
     * 游戏名 --->  游戏别名
     *
     * @param game_name
     * @param default_value
     * @return
     */
    public static String GameName2Alias(String game_name, String default_value) {
        String re_value = "";
        switch (game_name) {
            case Constant.Game_Name_36x7:
                re_value = Constant.GAME_ALIAS_36X7;
                break;
            case Constant.Game_Name_k3:
                re_value = Constant.GAME_ALIAS_K3;
                break;
            case Constant.Game_Name_kl8:
                re_value = Constant.GAME_ALIAS_KL8;
                break;
            case Constant.Game_Name_pl5:
                re_value = Constant.GAME_ALIAS_PL5;
                break;
            case Constant.Game_Name_37x6:
                re_value = Constant.GAME_ALIAS_37X6;
                break;
            case Constant.Game_Name_90x5:
                re_value = Constant.GAME_ALIAS_90X5;
                break;
            default:
                re_value = default_value;
                break;
        }
        return re_value;
    }

    /**
     * 玩法名称 --> 别名
     *
     * @param play_name
     * @param default_value
     * @return
     */
    public static String PlayName2Alias(String play_name, String default_value) {
        String re_value = "";
        if (play_name.contains("任")) {
            play_name = play_name.replace("任", "");
        }
        switch (play_name) {
            case Constant.Game_Play_k3_he:
                re_value = Constant.Game_Play_id_k3_hezhi;
                break;
            case Constant.Game_Play_k3_sth_dan:
                re_value = Constant.Game_Play_id_k3_3tonghao_dan;
                break;
            case Constant.Game_Play_k3_sth_tong:
                re_value = Constant.Game_Play_id_k3_3tonghao_tong;
                break;
            case Constant.Game_Play_k3_sbth:
                re_value = Constant.Game_Play_id_k3_3butonghao;
                break;
            case Constant.Game_Play_k3_slh:
                re_value = Constant.Game_Play_id_k3_3lianhao_tong;
                break;
            case Constant.Game_Play_k3_eth_dan:
                re_value = Constant.Game_Play_id_k3_2tonghao_dan;
                break;
            case Constant.Game_Play_k3_eth_fu:
                re_value = Constant.Game_Play_id_k3_2tonghao_fu;
                break;
            case Constant.Game_Play_k3_ebth:
                re_value = Constant.Game_Play_id_k3_2butonghao;
                break;

            case Constant.Game_Play_kl8_x1:
                re_value = Constant.Game_Play_id_kl8_x1;
                break;
            case Constant.Game_Play_kl8_x2:
                re_value = Constant.Game_Play_id_kl8_x2;
                break;
            case Constant.Game_Play_kl8_x3:
                re_value = Constant.Game_Play_id_kl8_x3;
                break;
            case Constant.Game_Play_kl8_x4:
                re_value = Constant.Game_Play_id_kl8_x4;
                break;
            case Constant.Game_Play_kl8_x5:
                re_value = Constant.Game_Play_id_kl8_x5;
                break;
            case Constant.Game_Play_kl8_x6:
                re_value = Constant.Game_Play_id_kl8_x6;
                break;
            case Constant.Game_Play_kl8_x7:
                re_value = Constant.Game_Play_id_kl8_x7;
                break;
            case Constant.Game_Play_kl8_x8:
                re_value = Constant.Game_Play_id_kl8_x8;
                break;
            case Constant.Game_Play_kl8_x9:
                re_value = Constant.Game_Play_id_kl8_x9;
                break;
            case Constant.Game_Play_kl8_x10:
                re_value = Constant.Game_Play_id_kl8_x10;
                break;
            default:
                re_value = default_value;
                break;
        }
        return re_value;
    }

    /**
     * 玩法别名 --> 名称
     *
     * @param play_name
     * @param default_value
     * @return
     */
    public static String PlayAlias2Name(String play_name, String default_value) {
        String re_value = "";
        switch (play_name) {
            case Constant.Game_Play_id_k3_hezhi:
                re_value = Constant.Game_Play_k3_he;
                break;
            case Constant.Game_Play_id_k3_3tonghao_dan:
                re_value = Constant.Game_Play_k3_sth_dan;
                break;
            case Constant.Game_Play_id_k3_3tonghao_tong:
                re_value = Constant.Game_Play_k3_sth_tong;
                break;
            case Constant.Game_Play_id_k3_3butonghao:
                re_value = Constant.Game_Play_k3_sbth;
                break;
            case Constant.Game_Play_id_k3_3lianhao_tong:
                re_value = Constant.Game_Play_k3_slh;
                break;
            case Constant.Game_Play_id_k3_2tonghao_dan:
                re_value = Constant.Game_Play_k3_eth_dan;
                break;
            case Constant.Game_Play_id_k3_2tonghao_fu:
                re_value = Constant.Game_Play_k3_eth_fu;
                break;
            case Constant.Game_Play_id_k3_2butonghao:
                re_value = Constant.Game_Play_k3_ebth;
                break;

            case Constant.Game_Play_id_kl8_x1:
                re_value = Constant.Game_Play_kl8_x1;
                break;
            case Constant.Game_Play_id_kl8_x2:
                re_value = Constant.Game_Play_kl8_x2;
                break;
            case Constant.Game_Play_id_kl8_x3:
                re_value = Constant.Game_Play_kl8_x3;
                break;
            case Constant.Game_Play_id_kl8_x4:
                re_value = Constant.Game_Play_kl8_x4;
                break;
            case Constant.Game_Play_id_kl8_x5:
                re_value = Constant.Game_Play_kl8_x5;
                break;
            case Constant.Game_Play_id_kl8_x6:
                re_value = Constant.Game_Play_kl8_x6;
                break;
            case Constant.Game_Play_id_kl8_x7:
                re_value = Constant.Game_Play_kl8_x7;
                break;
            case Constant.Game_Play_id_kl8_x8:
                re_value = Constant.Game_Play_kl8_x8;
                break;
            case Constant.Game_Play_id_kl8_x9:
                re_value = Constant.Game_Play_kl8_x9;
                break;
            case Constant.Game_Play_id_kl8_x10:
                re_value = Constant.Game_Play_kl8_x10;
                break;
            default:
                re_value = default_value;
                break;
        }
        return re_value;
    }

    /**
     * 玩法 id 代号 --> 名称
     *
     * @param play_code
     * @param default_value
     * @return
     */
    public static String PlayCode2Name(Context context, String play_code, String default_value) {
        String play_alias = SPUtils.getKeyByValue(context, play_code, default_value);
        String re_value = PlayAlias2Name(play_alias, default_value);
        return re_value;
    }

    public static String NewPlayCode2Name(Context context, String play_code) {
        if (TextUtils.isEmpty(play_code)) {
            return "";
        }
        int play_name_temp = 0;
        switch (play_code) {
            case Constant.Game_Play_Code_k3_he:
                play_name_temp = R.string.play_name_he;
                break;
            case Constant.Game_Play_Code_k3_sth_dan:
                play_name_temp = R.string.play_name_3tong;
                break;
            case Constant.Game_Play_Code_k3_eth_dan:
                play_name_temp = R.string.play_name_2tongdan;
                break;
            case Constant.Game_Play_Code_k3_sbth:
                play_name_temp = R.string.play_name_3butong;
                break;
            case Constant.Game_Play_Code_k3_ebth:
                play_name_temp = R.string.play_name_2butong;
                break;
            case Constant.Game_Play_Code_k3_slh:
                play_name_temp = R.string.play_name_3lian_tong;
                break;
            case Constant.Game_Play_Code_k3_sth_tong:
                play_name_temp = R.string.play_name_3tongtong;
                break;
            case Constant.Game_Play_Code_k3_eth_fu:
                play_name_temp = R.string.play_name_2tongfu;
                break;
            case Constant.Game_Play_Win_Level_90x5_1:
                play_name_temp = R.string.arbitrary_choice_one;
                break;
            case Constant.Game_Play_Win_Level_90x5_2:
                play_name_temp = R.string.arbitrary_choice_two;
                break;
            case Constant.Game_Play_Win_Level_90x5_3:
                play_name_temp = R.string.arbitrary_choice_three;
                break;
            case Constant.Game_Play_Win_Level_90x5_4:
                play_name_temp = R.string.arbitrary_choice_four;
                break;
            case Constant.Game_Play_Win_Level_90x5_5:
                play_name_temp = R.string.arbitrary_choice_five;
                break;
            case Constant.Game_Play_Win_Level_90x5_6:
                play_name_temp = R.string.dantuofushi;
                break;
            default:
                play_name_temp = 0;
                break;
        }
        try {
            return context.getString(play_name_temp);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }


}
