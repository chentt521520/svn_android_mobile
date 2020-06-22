package com.jc.lottery.bean;

import android.text.TextUtils;

import com.jc.lottery.R;
import com.jc.lottery.content.Constant;

import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * @ Create_time: 2019/1/17 on 9:59.
 * @ description：开奖中奖详情
 * @ author: vchao  blog: http://blog.csdn.net/zheng_weichao
 */
public class OpenWinDetailBean implements IMultiItem {
    private int betNum;
    private String winLevel;
    private String gameAlias;
    private String winMoney;

    public OpenWinDetailBean(String gameAlias, Resp_open_result_detail.DrawDetailsBean.LevelListBean bean) {
        this.gameAlias = gameAlias;
        this.winLevel = bean.getWinLevel();
        this.winMoney = bean.getWinMoney();
        this.betNum = bean.getBetNum();
    }

    public OpenWinDetailBean(int betNum, String winLevel, String gameAlias, String winMoney) {
        this.betNum = betNum;
        this.winLevel = winLevel;
        this.gameAlias = gameAlias;
        this.winMoney = winMoney;
    }

    public int getBetNum() {
        return betNum;
    }

    public void setBetNum(int betNum) {
        this.betNum = betNum;
    }

    public String getWinLevel() {
        return winLevel;
    }

    public void setWinLevel(String winLevel) {
        this.winLevel = winLevel;
    }

    public String getGameAlias() {
        return gameAlias;
    }

    public void setGameAlias(String gameAlias) {
        this.gameAlias = gameAlias;
    }

    public String getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(String winMoney) {
        this.winMoney = winMoney;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_open_win_detail;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        int palyNameRes = getPalyNameRes(gameAlias, winLevel);
        if (palyNameRes != 0) {
            holder.setText(R.id.tv_item_open_win_playname, palyNameRes);
        }
        holder.setText(R.id.tv_item_open_win_num, "" + betNum);
        holder.setText(R.id.tv_item_open_win_money, winMoney);
    }

    private int getPalyNameRes(String gameAlias, String winLevel) {
        int play_name_temp = 0;
        if (TextUtils.equals(getGameAlias(), Constant.GAME_ALIAS_K3)) {
            switch (winLevel) {
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
                default:
                    break;
            }
        } else if (TextUtils.equals(getGameAlias(), Constant.GAME_ALIAS_37X6) || TextUtils.equals(getGameAlias(), Constant.GAME_ALIAS_90X5) ||TextUtils.equals(getGameAlias(), Constant.Jackpot)) {
            switch (winLevel) {
                case Constant.Game_Play_Win_Level_90x5_1:
                case Constant.Game_Play_Win_Level_37x6_1:
                case Constant.Game_Play_Win_Level_Jacket_1:
                    play_name_temp = R.string.winlevel_1;
                    break;
                case Constant.Game_Play_Win_Level_90x5_2:
                case Constant.Game_Play_Win_Level_37x6_2:
                case Constant.Game_Play_Win_Level_Jacket_2:
                    play_name_temp = R.string.winlevel_2;
                    break;
                case Constant.Game_Play_Win_Level_90x5_3:
                case Constant.Game_Play_Win_Level_37x6_3:
                case Constant.Game_Play_Win_Level_Jacket_3:
                    play_name_temp = R.string.winlevel_3;
                    break;
                case Constant.Game_Play_Win_Level_90x5_4:
                case Constant.Game_Play_Win_Level_37x6_4:
                case Constant.Game_Play_Win_Level_Jacket_4:
                    play_name_temp = R.string.winlevel_4;
                    break;
                case Constant.Game_Play_Win_Level_90x5_5:
                case Constant.Game_Play_Win_Level_37x6_5:
                case Constant.Game_Play_Win_Level_Jacket_5:
                    play_name_temp = R.string.winlevel_5;
                    break;
                default:
                    break;
            }
        } else if (TextUtils.equals(getGameAlias(), Constant.GAME_ALIAS_49X6)) {
            switch (winLevel) {
                case Constant.Game_Play_Win_Level_49x6_1:
                    play_name_temp = R.string.winlevel_1;
                    break;
                case Constant.Game_Play_Win_Level_49x6_2:
                    play_name_temp = R.string.winlevel_2;
                    break;
                case Constant.Game_Play_Win_Level_49x6_3:
                    play_name_temp = R.string.winlevel_3;
                    break;
                case Constant.Game_Play_Win_Level_49x6_4:
                    play_name_temp = R.string.winlevel_4;
                    break;
                case Constant.Game_Play_Win_Level_49x6_5:
                    play_name_temp = R.string.winlevel_5;
                    break;
                case Constant.Game_Play_Win_Level_49x6_6:
                    play_name_temp = R.string.winlevel_6;
                    break;
                case Constant.Game_Play_Win_Level_49x6_7:
                    play_name_temp = R.string.winlevel_7;
                    break;
                case Constant.Game_Play_Win_Level_49x6_8:
                    play_name_temp = R.string.winlevel_8;
                    break;
                case Constant.Game_Play_Win_Level_49x6_9:
                    play_name_temp = R.string.winlevel_9;
                    break;
                default:
                    break;
            }
        }
        return play_name_temp;
    }

    @Override
    public int getSpanSize() {
        return 0;
    }
}
