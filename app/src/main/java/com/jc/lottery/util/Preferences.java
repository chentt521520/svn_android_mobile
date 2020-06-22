/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jc.lottery.util;

import android.content.SharedPreferences.Editor;

import java.io.IOException;

public class Preferences {

    public static final String PREFS_DEVICE_TOKEN = "com.caiso.lottery.devicetoken";
    public static final String PREFS_NAME = "com.caiso.lottery";
    public static final String PREFS_TIME = "com.caiso.lottery.time";
//    public static String hostJava = "http://47.104.231.60:8888";
    public static final String PREFERENCE_PASSWORD = "password";
    public static final String PREFERENCE_USER_TYPE = "usertype";
    private static final String PREFERENCE_USER_NAME = "username";
    public static String key = "CP22d359#5f56!174Mc_5cde2CS";
    // TODO PHP接口  测试
//    public static String host = "http://chu.mvschina.com";
    public static String host = "http://47.104.231.60/caipiao/";
    // TODO Java接口 测试
//    public static String hostJava = "http://47.104.128.232:8080";
    public static String hostJava = "http://172.16.160.24:8080";
//    public static String hostJava = "http://192.168.31.149:8080";
    // 支付方式
    public static String host3 = "http://chu.mvschina.com";
    public static String url_feedback = host + "/actForPhone/feedback.php";
    public static String software_update = host + "/actForPhone/softUpdate.php";
    public static String pay_info = host3 + "/customer/xzzfZfbAppReq.aspx?";// 获取支付信息
    public static String pay_H5_info = host3 + "/customer/xzzfZfbH5Req.aspx?";// 获取支付信息
    public static String reg_url = host + "/actForPhone/regForPhone.php";// 用户注册
    //    public static String forgot_password = host + "/actForPhone/updatePassword.php";// 忘记密码
    public static String forgot_password = host + "/actForPhone/updatePersonalCenterForPhone.php";// 修改后 忘记密码、个人资料、昵称、密码、邮箱
    public static String qr_code = host + "/actForPhone/ajaxExtendLink.php?";// 获取二维码
    public static String get_kuai3_url = host + "/actForPhone/ajaxTermForPhone.php";// 获取北京快3开奖历史
    public static String send_code_url = host + "/actForPhone/duanxinyanzhengForPhone.php";// 发送短信验证码
    public static String consulting_url = host + "/actForPhone/ajaxInfoForPhone.php";// http://10.0.0.111:8000/lotteryInfo/index.htm
    public static String lottery_url = host + "/actForPhone/ajaxTermForPhone.php";// 购彩信息
    // public static String cz_url =
    // "http://phone.caipiao.so/actForPhone/requestChargeForPhone.php";
    // public static String cz_url = host + "/Alipay/returnurl.php";
    public static String cz_url = host + "/actForPhone/requestChargeForPhone.php";
    public static String uesdetail_url = host + "/actForPhone/ajaxUesDetailForPhone.php";// 用户明细接口
    public static String Login_url = host + "/actForPhone/loginForPhone.php";// 登录接口
    public static String daigou_url = host + "/actForPhone/daigouForPhone2.php";// 我的代购接口
    public static String touzhu_url = host + "/actForPhone/buyIndex.php";// 投注接口（支付）
    public static String ajaxbangding_url = host + "/actForPhone/ajaxbangdingForPhone.php";// 绑定银行卡
    // 接口
    public static String tixian_url = host + "/actForPhone/tixianForPhone.php";// 提现接口
    public static String touzhuixinxi_url = host + "/actForPhone/exactMessageOfTouZhuForPhone.php";
    public static String customerMaterial_url = host + "/actForPhone/customerMaterialForPhone.php";
    public static String chongzhi_url = host + "/actForPhone/chargeForPhone.php";// 充值接口
    public static String ajaxcusMaterial_url = host + "/actForPhone/ajaxcusMaterialForPhone.php";
    public static String buy_lottery_detail_url = host + "/actForPhone/buy_lottery_detailForPhone2.php";// 单注的代购详情接口
    public static String buy_lottery_zhuihao_url = host + "/actForPhone/zhuihaoAPI.php";// 追号
    public static String buy_lottery_zhuihaoxiangqing_url = host + "/actForPhone/zhuihaoxiangqingForPhone.php";// 追号详情
    public static String buy_lottery_duanxinyanzhengForPhone = host + "/actForPhone/duanxinyanzhengForPhone.php";// 发短信接口
    public static String ajaxHistory_url = host + "/actForPhone/sshc.php?action=ajaxHistoryMatchInfoForPhone";
    public static String buy_url_recharges = host + "/actForPhone/chargeForPhoneUnionPay.php";
    public static String auth_realname_url = host + "/actForPhone/authRealNameForPhone.php";// 实名认证
    public static String recharges_records_url = host + "/actForPhone/chargeRecordsForPhone.php";// 充值记录
    public static String alipay_login_userinfo_query = "https://mapi.alipay.com/gateway.do";// 客户端手机登录用户共享
    public static String alipay_wappay_url = "http://wappaygw.alipay.com/service/rest.htm";// 支付宝wap充值
    public static String alipay_wappay_notfiy_url = "http://phone.caipiao.so/actForPhone/requestChargeForPhoneWap.php";// 支付宝wap充值异步通知地址
    public static String ajax_contact = host + "/actForPhone/ajaxContactForPhone.php";// 绑定联系方式
    public static String SelectOpenPrizeTime = host + "/actForPhone/selectOpenPrizeTime.php";//获取双色球/3D等期号截止日期信息
    public static String CreateOrder = host + "/actForPhone/CreateOrder.php";//生成订单
    public static String ajaxIsPayLottery = host + "/actForPhone/ajaxIsPayLottery.php";//判断3D是否在售
    // 微信登录
    public static String access_token = "https://api.weixin.qq.com/sns/oauth2/access_token";// 微信登录接口 获取access_token
    public static String userinfo = "https://api.weixin.qq.com/sns/userinfo?";// 微信登录接口 获取Userinfo
    // 支付宝支付
    public static String alipay = "http://hys.free.ngrok.cc/ty_server/pay/ailpay.shtml";
    public static String ajaxIndexInfo = host + "/actForPhone/ajaxIndexInfo.php";//首页
    public static String ajaxPublic = host + "/actForPhone/ajaxPublic.php";//公告列表
    public static String userShare = host + "/actForPhone/userShare.php";//分享接口
    public static String deleteForOrder = host + "/actForPhone/deleteForOrder.php";//删除订单接口
    public static String uploadHeader = host + "/actForPhone/uploadHeader.php";//上传头像接口
    public static String betting = hostJava + "/api/order/betting.shtml";//生成订单
    public static String h5_pay = hostJava + "/pay/h5_pay.shtml";//混合支付
    public static String balance = hostJava + "/pay/order/balance.shtml";//余额、彩金支付
    public static String app_pay = hostJava + "/pay/app_pay.shtml";//微信、支付宝支付
    public static String app_pecharge = hostJava + "/pay/app_pecharge.shtml";//充值
    public static String list = hostJava + "/api/store_manage/list.shtml";//出票位置查找
    public static String myfriend = hostJava + "/api/friend/myfriend.shtml";//我的好友列表
    public static String trend_chart = hostJava + "/api/trend_chart.shtml";//3D近期开奖
    public static String content = hostJava + "/termcount/content_z.shtml";//双色球近期开奖
//    public static String trend_chart_z ="http://192.168.31.100:8080/ty_server/api/trend_chart_z.shtml";//走势图
    public static String detail = hostJava + "/capital/detail.shtml";//用户资金明细
    //    public static String registration ="http://192.168.31.151:8080/ty_server/registration.html";//分享H5页面
//    public static String registration =hostJava+"registration.html";//分享H5页面
    public static String registration = hostJava + "/registration.html";//分享H5页面
    public static String trend_chart_z = hostJava + "/api/trend_chart_z.shtml";//走势图
    public static String scan = hostJava + "/code/scan.shtml";//扫码请求
    /**
     * 扫码后传送接口 TODO
     */
    public static String scanedSendDataToTv = host + "/actForPhone/Scancode.php";
    /**
     * 查看状态/actForPhone/selScancode.php
     */

    public static String selScancode = host + "/actForPhone/selScancode.php";
    /**
     * 获取视频数据接口/actForPhone/video.php
     */
    public static String selVedioData = host + "/actForPhone/video.php";

    //    public static Login loginUser(CaiSoLottery caiSoLottery, String mUsername, String mPassword, String mUserType,String mRealName, String mCredent, String email, String mobile, Editor editor, boolean isChecked)
//    public static Login loginUser(CaiSoLottery caiSoLottery, String phone, String password, String userType, String realname, String credent_no, String email, String userName, Editor editor, String device_token, String from)
//            throws LotteryCredentialsException, LotteryException, IOException {
//
//        Login login = caiSoLottery.setCredentials(phone, password, userType, realname, credent_no, email, userName, device_token, from);
//        if (login.getStatus().equals("_0000")) {
////            storeUser(editor, phone, password, userType, login);
//        }
//
//        return login;
//    }

//    public static Login loginWxUser(CaiSoLottery caiSoLottery, String openid, String unionid, String userType, String sex, String nickname, String province, String city, String headimgurl, Editor editor, String device_token, String from)
//            throws LotteryCredentialsException, LotteryException, IOException {
//
//        Login login = caiSoLottery.setWxCredentials(openid, unionid, userType, sex, nickname, province, city, headimgurl, device_token, from);
//        if (login.getStatus().equals("_0000")) {
////            storeWxUser(editor, openid, unionid, userType, sex, nickname, province, city, headimgurl, login);
//        }
//
//        return login;
//    }

//    public static void storeUser(final Editor editor, String mUsername, String mPassword, String mUserType, Login login)
//            throws LotteryException {
//        if (mUsername != null && mPassword != null) {
//            editor.putString(PREFERENCE_USER_NAME, mUsername);
//            editor.putString(PREFERENCE_PASSWORD, mPassword);
//            editor.putString(PREFERENCE_USER_TYPE, mUserType);
//            editor.putString("userId", login.getUserId());
//            editor.putString("lotterybalance", login.getLotterybalance());
//            editor.putString("allwinmoney", login.getAllWinMoney());
//
//            editor.commit();
////            if (!editor.commit()) {
////                throw new LotteryException("store info failed!");
////            }
//
//        } else {
//            editor.putString(PREFERENCE_USER_NAME, null);
//            editor.putString(PREFERENCE_PASSWORD, null);
//            editor.putString(PREFERENCE_USER_TYPE, null);
//            if (!editor.commit()) {
//                throw new LotteryException("store info failed!");
//            }
//        }
//    }

//    public static void storeWxUser(final Editor editor, String openid, String unionid, String userType, String sex, String nickname, String province, String city, String headimgurl, Login login)
//            throws LotteryException {
//        if (openid != null) {
//            editor.putString("username", openid);
//            editor.putString("password", "");
//            editor.putString("usertype", "1");
//            editor.putString("lotterybalance", login.getLotterybalance());
//            editor.putString("allwinmoney", login.getAllWinMoney());
//            editor.putString("headimgurl", headimgurl);
//            editor.putString("nickname", nickname);
//            editor.putString("sex", sex);
//            editor.putString("city", city);
//            editor.putString("province", province);
//            editor.putString("userId", login.getUserId());
//            editor.putString("lotterybalance", login.getLotterybalance());
//            editor.putString("allwinmoney", login.getAllWinMoney());
//
//            editor.commit();
//
////            if (!editor.commit()) {
////                throw new LotteryException("store info failed!");
////            }
//
//        } else {
//            editor.putString(PREFERENCE_USER_NAME, null);
//            editor.putString(PREFERENCE_PASSWORD, null);
//            editor.putString(PREFERENCE_USER_TYPE, null);
//            if (!editor.commit()) {
//                throw new LotteryException("store info failed!");
//            }
//        }
//    }

}

