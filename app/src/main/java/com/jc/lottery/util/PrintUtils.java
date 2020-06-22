package com.jc.lottery.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gprinter.command.EscCommand;
import com.gprinter.command.LabelCommand;
import com.jc.lottery.R;
import com.jc.lottery.base.application.LotteryApplication;
import com.jc.lottery.bean.Resp_safetyCode;
import com.jc.lottery.bean.TicketListBean;
import com.jc.lottery.bean.resp.Resp_Order_Success;
import com.jc.lottery.bean.type.Group;
import com.jc.lottery.bean.type.Payment;
import com.jc.lottery.content.Constant;

import java.util.Vector;


/**
 * @ Create_time: 2018/9/6 on 17:02.
 * @ description：打印工具类
 * @ author: vchao
 */
public class PrintUtils {
    /**
     * 发送票据
     * （应该是不用了）
     *
     * @param group    彩票组
     * @param multiple 倍数
     */
    public static boolean sendReceiptWithResponse(Context context, String orderNo, String term_no, String winning_time, int money, Group<Payment> group, String multiple, int printid) {
        try {
//        打印票据
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            esc.addPrintAndFeedLines((byte) 1);
            // 设置打印居中
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);

            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.print_logo);
            // 打印图片
            esc.addRastBitImage(b, 450, 0);

            // 设置为倍高倍宽
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
            // 打印文字
            esc.addPrintAndLineFeed();
            esc.addText(group.getType() + "\n");
            esc.addPrintAndLineFeed();
            // 取消倍高倍宽
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

//            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
            // 设置打印左对齐
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            // 打印文字
            esc.addText(String.format(context.getString(R.string.qici_no), term_no));// 奖期
            esc.addText(winning_time + context.getString(R.string.open_draw) + "\n");

//            String currentTimeStr = com.tianyi.lottery.util.TimeUtils.getCurrentTimeStr();
//            esc.addText("打印时间：" + currentTimeStr + "\n");
            esc.addText("1100115-124564-789779-467813 841613\n");

            esc.addText(context.getString(R.string.line_fenge) + "\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            try {
                Integer.parseInt(multiple);
                esc.addText("       " + multiple + context.getString(R.string.bei) + "                     ");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                esc.addText("       " + 1 + context.getString(R.string.bei) + "                     ");
            }
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.RIGHT);
            esc.addText(context.getString(R.string.heji) + money + context.getString(R.string.price_unit) + "\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            for (Payment payment : group) {
                // 打印文字
                esc.addText("       " + payment.getZuheType() + "   ");// 和值
                esc.addText(payment.getMumber() + "\n");
                LogUtils.e(payment.getType() + "");
                LogUtils.e(payment.getZuheType() + "");
                LogUtils.e(payment.getMumber() + "");
                LogUtils.e(payment.getBetting() + "");
                LogUtils.e(payment.getIsRandomSelection() + "");
            }
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addText(context.getString(R.string.line_fenge) + "\n");
            esc.addText(context.getString(R.string.thankyoubuy) + money + context.getString(R.string.price_unit) + " \n");
            esc.addText(context.getString(R.string.ticket_bottom_tip1) + "\n");
            esc.addText(context.getString(R.string.ticket_bottom_tip2) + "\n");
            esc.addText(context.getString(R.string.ticket_bottom_tip3) + "\n");
            esc.addText(context.getString(R.string.ticket_bottom_tip4) + "\n");

		/*
         * QRCode命令打印 此命令只在支持QRCode命令打印的机型才能使用。 在不支持二维码指令打印的机型上，则需要发送二维条码图片
		 */
            // 打印文字
//            esc.addText("订单号：" + orderNo + "\n");
            // 设置纠错等级
            esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31);
            // 设置qrcode模块大小
            esc.addSelectSizeOfModuleForQRCode((byte) 14);
            // 设置qrcode内容
            esc.addStoreQRCodeData(orderNo + "");
            esc.addPrintQRCode();// 打印QRCode
            esc.addPrintAndLineFeed();

            // 开钱箱
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            esc.addPrintAndFeedLines((byte) 2);
            // 加入查询打印机状态，打印完成后，此时会接收到GpCom.ACTION_DEVICE_STATUS广播
            esc.addQueryPrinterStatus();
            esc.addCutPaper();
            Vector<Byte> datas = esc.getCommand();
            // 发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[printid].sendDataImmediately(datas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送票据
     */
    public static boolean sendTicket(Context context, Resp_Order_Success resp_order_success, long end_time) {
        try {
//        打印票据
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            esc.addPrintAndLineFeed();
            // 设置打印居中
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.print_logo);
            // 打印图片
            esc.addRastBitImage(b, 420, 0);
            esc.addPrintAndLineFeed();

            String gameAlias = resp_order_success.getData().getOrderInfo().getGameAlias();
            String gamename = TransfromUtils.GameAlias2Name(context, gameAlias, "彩票");
            esc.addText(gamename + "\n");
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addPrintAndLineFeed();

            esc.addText(String.format(context.getString(R.string.qici_no), resp_order_success.getData().getOrderInfo().getDrawNumber()) + "    " + TimeUtils.getTime(context,end_time) + context.getString(R.string.open_draw) + "\n");
            esc.addText(resp_order_success.getOrderCode() + "\n");
            esc.addSetAbsolutePrintPosition((short) 10);
            esc.addText(context.getString(R.string.line_fenge) + "\n");

            esc.addText(resp_order_success.getData().getOrderInfo().getBetDouble() + context.getString(R.string.bei) + "    " + resp_order_success.getData().getOrderInfo().getMultiDraw() + context.getString(R.string.qi) + "         " + context.getString(R.string.heji) + resp_order_success.getData().getOrderInfo().getTotalMoney() + context.getString(R.string.price_unit) + " \n");

            String paly_name = TransfromUtils.NewPlayCode2Name(context, resp_order_success.getData().getOrderInfo().getGamePlayNum());
            if (!TextUtils.isEmpty(paly_name)) {
//            String paly_name = TransfromUtils.PlayCode2Name(context, resp_order_success.getData().getOrderInfo().getGamePlayNum(), "玩法");
                if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_KL8) &&
                        TextUtils.equals(resp_order_success.getData().getOrderInfo().getFrisbeeStatus(), "01")) {
                    esc.addText(paly_name + "-" + context.getString(R.string.play_name_happy_fly) + "\n");
                } else {
                    esc.addText(paly_name + "\n");
                }
                esc.addPrintAndLineFeed();
            }

            for (TicketListBean ticketListBean : resp_order_success.getData().getOrderInfo().getTicketList()) {
                String s = context.getString(R.string.danshi);
                if (TextUtils.equals(ticketListBean.getEachBetMode(), "01")) {
                    s = context.getString(R.string.danshi);
                } else if (TextUtils.equals(ticketListBean.getEachBetMode(), "02")) {
                    s = context.getString(R.string.fushi);
                } else if (TextUtils.equals(ticketListBean.getEachBetMode(), "03")) {
                    s = context.getString(R.string.dantuofushi);
                }
                switch (paly_name) {
                    case Constant.Game_Play_k3_sth_tong:
                        esc.addText(context.getString(R.string.play_name_3tongtong) + "      " + s + "      " + ticketListBean.getEachTotalMoney() + context.getString(R.string.price_unit) + "  \n");// 票号信息
                        break;
                    case Constant.Game_Play_k3_slh:
                        esc.addText(context.getString(R.string.play_name_3lian_tong) + "       " + s + "      " + ticketListBean.getEachTotalMoney() + context.getString(R.string.price_unit) + "  \n");// 票号信息
                        break;
                    default:
                        esc.addText(ticketListBean.getTicket() + "       " + s + "      " + ticketListBean.getEachTotalMoney() + context.getString(R.string.price_unit) + "  \n");// 票号信息
                        break;
                }

            }
            esc.addPrintAndLineFeed();
            esc.addText(context.getString(R.string.line_fenge) + "\n");
            esc.addText(context.getString(R.string.thankyoubuy) + resp_order_success.getData().getOrderInfo().getTotalMoney() + context.getString(R.string.price_unit) + " \n");
            esc.addText(context.getString(R.string.ticket_bottom_tip1) + "\n");
            esc.addText(context.getString(R.string.ticket_bottom_tip2) + "\n");
            esc.addText(context.getString(R.string.ticket_bottom_tip3) + "\n");
            esc.addText(context.getString(R.string.ticket_bottom_tip4) + "\n");

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            // 设置纠错等级
            esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31);
            // 设置qrcode模块大小
            esc.addSelectSizeOfModuleForQRCode((byte) 10);
            // 设置qrcode内容
            esc.addStoreQRCodeData(resp_order_success.getSafetyCode() + "");
            esc.addPrintQRCode();// 打印QRCode
            esc.addPrintAndLineFeed();

            // 开钱箱
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            esc.addPrintAndFeedLines((byte) 6);
            // 加入查询打印机状态，打印完成后，此时会接收到GpCom.ACTION_DEVICE_STATUS广播
            esc.addQueryPrinterStatus();
            esc.addCutPaper();
            Vector<Byte> datas = esc.getCommand();
            // 发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("StringFormatInvalid")
    public static boolean sendTicket(Context context, Resp_Order_Success resp_order_success, long end_time, String action) {
        try {
//        打印票据
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            esc.addPrintAndLineFeed();
            // 设置打印居中
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.print_logo);
            // 打印图片
            esc.addRastBitImage(b, 420, 0);
            esc.addPrintAndLineFeed();

            String gameAlias = resp_order_success.getData().getOrderInfo().getGameAlias();
            String gamename = TransfromUtils.GameAlias2Name(context, gameAlias, "彩票");
            String paly_name = TransfromUtils.NewPlayCode2Name(context, resp_order_success.getData().getOrderInfo().getGamePlayNum());

            if (!TextUtils.isEmpty(resp_order_success.getData().getOrderInfo().getGamePlayNum())) {
                if (!TextUtils.equals(paly_name, "玩法") && !TextUtils.equals(paly_name, "")) {
                    gamename = gamename + " " + paly_name;
                }
            }

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addText("Mobile Lotto\n" + "" + gamename + "\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            esc.addSetLeftMargin((short) 80);
            esc.addText("-------------------------------------\n");
            esc.addText(resp_order_success.getOrderCode() + " \n");
            esc.addText("-------------------------------------\n");
            esc.addText("Draw ID: " + resp_order_success.getData().getOrderInfo().getDrawNumber() + "\n");
            esc.addText("Close Date:\n" + TimeUtils.getTime(context,resp_order_success.getEndTime()) + "\n");
            esc.addText("Draw Date:\n" + TimeUtils.getTime(context,resp_order_success.getPrizeTime()) + "\n");
            esc.addText("Sale Date:\n" + TimeUtils.getTime(context,resp_order_success.getBuyTime()) + "\n");
            esc.addText("-------------------------------------\n");

            esc.addText("Numbers Played\n");
            esc.addText("-------------------------------------\n");
            esc.addSetLeftMargin((short) 150);
            for (TicketListBean ticketListBean : resp_order_success.getData().getOrderInfo().getTicketList()) {
                switch (paly_name) {
                    case Constant.Game_Play_k3_sth_tong:
                        esc.addText(context.getString(R.string.play_name_3tongtong) + "\n");// 票号信息
                        break;
                    case Constant.Game_Play_k3_slh:
                        esc.addText(context.getString(R.string.play_name_3lian_tong) + "\n");// 票号信息
                        break;
                    default:
                        String ticket = ticketListBean.getTicket();
                        ticket = ticket.replace(" ", ", ");
                        esc.addText(ticket + "\n");// 票号信息
                        break;
                }
            }
            esc.addSetLeftMargin((short) 80);

            esc.addText("-------------------------------------\n");
            esc.addText("Stake: " + MoneyUtil.getIns().GetMoney(FormatUtil.addComma(resp_order_success.getData().getOrderInfo().getTotalMoney())) + context.getString(R.string.price_unit) + "\n");
            esc.addText("-------------------------------------\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            // 设置纠错等级
            esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31);
            // 设置qrcode模块大小
            esc.addSelectSizeOfModuleForQRCode((byte) 10);
//            TODO 二维码打印内容 设置qrcode内容
            esc.addStoreQRCodeData(resp_order_success.getOrderCode() + "");
//            esc.addStoreQRCodeData(resp_order_success.getSafetyCode() + "");
            esc.addPrintQRCode();// 打印QRCode
            esc.addPrintAndLineFeed();

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            String safetyCode = resp_order_success.getSafetyCode();
            try {
//                Resp_safetyCode resp_safetyCode = new Gson().fromJson(safetyCode, Resp_safetyCode.class);
//                esc.addText(resp_safetyCode.getSafetyCode() + "" + "\n");
                esc.addText(safetyCode + "" + "\n");
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

            // 开钱箱
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            esc.addPrintAndFeedLines((byte) 6);
            // 加入查询打印机状态，打印完成后，此时会接收到GpCom.ACTION_DEVICE_STATUS广播
            esc.addQueryPrinterStatus();
            esc.addCutPaper();
            Vector<Byte> datas = esc.getCommand();
            // 发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);

            Intent intent = new Intent(action);
            LotteryApplication.getContext().sendBroadcast(intent);
            LogUtils.e("发送打印成功广播" + action);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("错误信息   " + e.getLocalizedMessage());
            return false;
        }
    }

    @SuppressLint("StringFormatInvalid")
    public static boolean sendJackTicket(Context context, Resp_Order_Success resp_order_success, long end_time, String action) {
        try {
//        打印票据
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            esc.addPrintAndLineFeed();
            // 设置打印居中
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.print_logo);
            // 打印图片
            esc.addRastBitImage(b, 420, 0);
            esc.addPrintAndLineFeed();

            String gameAlias = resp_order_success.getData().getOrderInfo().getGameAlias();
            String gamename = TransfromUtils.GameAlias2Name(context, gameAlias, "彩票");
            String paly_name = TransfromUtils.NewPlayCode2Name(context, resp_order_success.getData().getOrderInfo().getGamePlayNum());

            if (!TextUtils.isEmpty(resp_order_success.getData().getOrderInfo().getGamePlayNum())) {
                if (!TextUtils.equals(paly_name, "玩法") && !TextUtils.equals(paly_name, "")) {
                    gamename = gamename + " " + paly_name;
                }
            }

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addText("Mobile Lotto\n" + "" + gamename + "\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
            esc.addSetLeftMargin((short) 80);
            esc.addText("-------------------------------------\n");
            esc.addText(resp_order_success.getOrderCode() + " \n");
            esc.addText("-------------------------------------\n");
            esc.addText("Draw ID: " + resp_order_success.getData().getOrderInfo().getDrawNumber() + "\n");
            esc.addText("Close Date:\n" + TimeUtils.getTime(context,resp_order_success.getEndTime()) + "\n");
            esc.addText("Draw Date:\n" + TimeUtils.getTime(context,resp_order_success.getPrizeTime()) + "\n");
            esc.addText("Sale Date:\n" + TimeUtils.getTime(context,resp_order_success.getBuyTime()) + "\n");
            esc.addText("-------------------------------------\n");

            esc.addText("Numbers Played\n");
            esc.addText("-------------------------------------\n");
            esc.addSetLeftMargin((short) 150);
            for (TicketListBean ticketListBean : resp_order_success.getData().getOrderInfo().getTicketList()) {
                switch (paly_name) {
                    case Constant.Game_Play_k3_sth_tong:
                        esc.addText(context.getString(R.string.play_name_3tongtong) + "\n");// 票号信息
                        break;
                    case Constant.Game_Play_k3_slh:
                        esc.addText(context.getString(R.string.play_name_3lian_tong) + "\n");// 票号信息
                        break;
                    default:
                        String ticket = ticketListBean.getTicket();
                        ticket = ticket.replace(" ", ", ");
                        esc.addText(ticket + "\n");// 票号信息
                        break;
                }
            }
            esc.addSetLeftMargin((short) 80);

            esc.addText("-------------------------------------\n");
            esc.addText("Stake: " + MoneyUtil.getIns().GetMoney(FormatUtil.addComma(resp_order_success.getData().getOrderInfo().getTotalMoney())) + context.getString(R.string.price_unit) + "\n");
            esc.addText("-------------------------------------\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            // 设置纠错等级
            esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31);
            // 设置qrcode模块大小
            esc.addSelectSizeOfModuleForQRCode((byte) 10);
//            TODO 二维码打印内容 设置qrcode内容
            esc.addStoreQRCodeData(resp_order_success.getOrderCode() + "");
//            esc.addStoreQRCodeData(resp_order_success.getSafetyCode() + "");
            esc.addPrintQRCode();// 打印QRCode
            esc.addPrintAndLineFeed();

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            String safetyCode = resp_order_success.getSafetyCode();
            try {
//                Resp_safetyCode resp_safetyCode = new Gson().fromJson(safetyCode, Resp_safetyCode.class);
//                esc.addText(resp_safetyCode.getSafetyCode() + "" + "\n");
                esc.addText(safetyCode + "" + "\n");
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

            // 开钱箱
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            esc.addPrintAndFeedLines((byte) 6);
            // 加入查询打印机状态，打印完成后，此时会接收到GpCom.ACTION_DEVICE_STATUS广播
            esc.addQueryPrinterStatus();
            esc.addCutPaper();
            Vector<Byte> datas = esc.getCommand();
            // 发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);

            Intent intent = new Intent(action);
            LotteryApplication.getContext().sendBroadcast(intent);
            LogUtils.e("发送打印成功广播" + action);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("错误信息   " + e.getLocalizedMessage());
            return false;
        }
    }

    @SuppressLint("StringFormatInvalid")
    public static boolean sendNewestTicket(Context context, Resp_Order_Success resp_order_success, long end_time, String action) {
        try {
//        打印票据
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            esc.addPrintAndLineFeed();
            // 设置打印居中
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.print_logo);
            // 打印图片
            esc.addRastBitImage(b, 420, 0);
            esc.addPrintAndLineFeed();

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            String gameAlias = resp_order_success.getData().getOrderInfo().getGameAlias();
            String gamename = TransfromUtils.GameAlias2Name(context, gameAlias, "彩票");
            String paly_name = TransfromUtils.NewPlayCode2Name(context, resp_order_success.getData().getOrderInfo().getGamePlayNum());

            if (!TextUtils.isEmpty(resp_order_success.getData().getOrderInfo().getGamePlayNum())) {
                if (!TextUtils.equals(paly_name, "玩法") && !TextUtils.equals(paly_name, "")) {
                    gamename = gamename + " " + paly_name;
//                    gamename = paly_name;
                }
            }
            esc.addText("Mobile Lotto" + " " + gamename + "\n");
            esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addText("SERIAL NO: " + resp_order_success.getOrderCode() + " \n");
//            esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//            esc.addSetLeftMargin((short) 80);
            esc.addText("-------------------------------------\n");
//            esc.addText("_____________________________________\n\n");
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
            esc.addText("Number Played" + "\n");

            for (TicketListBean ticketListBean : resp_order_success.getData().getOrderInfo().getTicketList()) {
                switch (paly_name) {
                    case Constant.Game_Play_k3_sth_tong:
                        esc.addText(context.getString(R.string.play_name_3tongtong) + "\n");// 票号信息
                        break;
                    case Constant.Game_Play_k3_slh:
                        esc.addText(context.getString(R.string.play_name_3lian_tong) + "\n");// 票号信息
                        break;
                    default:
                        String ticket = ticketListBean.getTicket();
                        ticket = ticket.replace(" ", ", ");
                        esc.addText(ticket + "\n");// 票号信息
                        break;
                }
            }
            esc.addText("-------------------------------------\n");
//            esc.addText("_____________________________________\n\n");
            esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
            esc.addText("Draw ID: " + resp_order_success.getData().getOrderInfo().getDrawNumber() + "\n");
            esc.addText("Close Date:\n" + TimeUtils.getTime(context,resp_order_success.getEndTime()) + "\n");
            esc.addText("Draw Date:\n" + TimeUtils.getTime(context,resp_order_success.getPrizeTime()) + "\n");
            esc.addText("Sale Date:\n" + TimeUtils.getTime(context,resp_order_success.getBuyTime()) + "\n");
            esc.addText("-------------------------------------\n");
//            esc.addText("_____________________________________\n\n");

//            esc.addText("Numbers Played\n");
//            esc.addText("-------------------------------------\n");
//            esc.addSetLeftMargin((short) 150);

//            esc.addSetLeftMargin((short) 80);

//            esc.addText("-------------------------------------\n");
            esc.addText("Stake: " + MoneyUtil.getIns().GetMoney(FormatUtil.addComma(resp_order_success.getData().getOrderInfo().getTotalMoney())) + context.getString(R.string.price_unit) + "\n");
            String safetyCode = resp_order_success.getSafetyCode();
            try {
//                Resp_safetyCode resp_safetyCode = new Gson().fromJson(safetyCode, Resp_safetyCode.class);
//                esc.addText(resp_safetyCode.getSafetyCode() + "" + "\n");
                esc.addText(safetyCode + "" + "\n" + "\n");
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
//            esc.addText("-------------------------------------\n");

            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);

            // 设置纠错等级
            esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31);
            // 设置qrcode模块大小
            esc.addSelectSizeOfModuleForQRCode((byte) 10);
//            TODO 二维码打印内容 设置qrcode内容
            esc.addStoreQRCodeData(resp_order_success.getOrderCode() + "");
//            esc.addStoreQRCodeData(resp_order_success.getSafetyCode() + "");
            esc.addPrintQRCode();// 打印QRCode
            esc.addPrintAndLineFeed();

            esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

            // 开钱箱
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            esc.addPrintAndFeedLines((byte) 6);
            // 加入查询打印机状态，打印完成后，此时会接收到GpCom.ACTION_DEVICE_STATUS广播
            esc.addQueryPrinterStatus();
            esc.addCutPaper();
            Vector<Byte> datas = esc.getCommand();
            // 发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);

            Intent intent = new Intent(action);
            LotteryApplication.getContext().sendBroadcast(intent);
            LogUtils.e("发送打印成功广播" + action);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("错误信息   " + e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean sendTest() {
        try {
//        打印票据
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            esc.addPrintAndFeedLines((byte) 1);
            // 设置打印居中
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);

            esc.addText("123123123123123\n");
            esc.addText("ASDXZCZXCASDASDA\n");
            esc.addText("打印测试：测试测试测试\n","GBK");
            esc.addText("打印测试：测试测试测试\n");

            // 开钱箱
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            esc.addPrintAndFeedLines((byte) 2);
            // 加入查询打印机状态，打印完成后，此时会接收到GpCom.ACTION_DEVICE_STATUS广播
            esc.addQueryPrinterStatus();
            esc.addCutPaper();
            Vector<Byte> datas = esc.getCommand();
            // 发送数据
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].sendDataImmediately(datas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
