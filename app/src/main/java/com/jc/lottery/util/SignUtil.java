package com.jc.lottery.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jc.lottery.bean.TicketListBean;
import com.jc.lottery.bean.req.Fast3CommitRequest;
import com.jc.lottery.bean.req.kuai3order;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author syl 2018-03-02 签名工具类
 */
public class SignUtil {
	
	// 测试签名方法
	public static void main(String[] args) throws UnsupportedEncodingException {

		JSONObject data = new JSONObject();
		JSONObject orderInfo = new JSONObject();
		orderInfo.put("gameId", "195");
		orderInfo.put("gameAlias", "kl8");
		
		JSONArray ticketList = new JSONArray();
		JSONObject ticketMap = new JSONObject();
		ticketMap.put("ticket", "04 05 06 07");
		ticketMap.put("eachTotalMoney", "200");
		ticketMap.put("eachBetMode", "01");
		ticketList.add(ticketMap);
//		JSONObject ticketMap1 = new JSONObject();
//		ticketMap1.put("ticket", "2 9 8 2 7");
//		ticketMap1.put("eachTotalMoney", "200");
//		ticketMap1.put("eachBetMode", "01");
//		ticketList.add(ticketMap1);
		orderInfo.put("ticketList", ticketList);
		
		JSONArray drawList = new JSONArray();
		JSONObject drawMap = new JSONObject();
		drawMap.put("drawId", "3595");
		drawMap.put("drawNumber", "2018099");
		drawList.add(drawMap);
//		JSONObject drawMap1 = new JSONObject();
//		drawMap1.put("drawId", "3594");
//		drawMap1.put("drawNumber", "2018098");
//		drawList.add(drawMap1);
		orderInfo.put("drawList", drawList);
		
		orderInfo.put("terminalId", "22");
		orderInfo.put("terminal", "100202");
		orderInfo.put("multiDraw", "1");
		orderInfo.put("betDouble", "1");
		orderInfo.put("noteNumber", "1");
		orderInfo.put("totalMoney", "2");
		orderInfo.put("betMode", "01");
		orderInfo.put("drawId", "7803");
		orderInfo.put("drawNumber", "908552");

		orderInfo.put("gameIdAdd", "4");
		orderInfo.put("frisbeeStatus", "00");

		data.put("orderInfo", orderInfo);

		sign(data);

	}

	// 拼接json键值对 md5加密 签名方法
	@SuppressWarnings("rawtypes")
	public static String sign(JSONObject data) {
		
		JSONObject orderInfo = data.getJSONObject("orderInfo");
		Map<String, Object> packageParams = JSON.parseObject(orderInfo.toJSONString());

		//排序
		Map<String, Object> map = new TreeMap<String, Object>();
		for (String key : packageParams.keySet()) {
			map.put(key, packageParams.get(key));
		}
		
		StringBuffer sb = new StringBuffer();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			
			String v = "";
			if (k.equals("ticketList") || k.equals("drawList")) {
				JSONArray list = (JSONArray) entry.getValue();
				v = list.toJSONString();
			}else {
				v = String.valueOf(entry.getValue());
			}

			if (null != v && !"".equals(v) && !"sign".equals(k)&& !"key".equals(k)) {				
				sb.append(k + "=" + v + "&");
			}
		}

		String sign = MD5.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//		LogUtils.e("加密后结果 "+sign);
		return sign;
	}
	public static com.alibaba.fastjson.JSONObject getData(Fast3CommitRequest.DataBean.OrderInfoBean infoBean) {
		com.alibaba.fastjson.JSONObject data = new com.alibaba.fastjson.JSONObject();
		com.alibaba.fastjson.JSONObject orderInfo = new com.alibaba.fastjson.JSONObject();
		orderInfo.put("gameId", infoBean.getGameId());
		orderInfo.put("gameAlias", infoBean.getGameAlias());

		JSONArray ticketList = new JSONArray();

		List<Fast3CommitRequest.DataBean.OrderInfoBean.TicketListBean> list = infoBean.getTicketList();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Fast3CommitRequest.DataBean.OrderInfoBean.TicketListBean ticketListBean = list.get(i);
			com.alibaba.fastjson.JSONObject ticketMap = new com.alibaba.fastjson.JSONObject();
			ticketMap.put("ticket", ticketListBean.getTicket());
			ticketMap.put("eachTotalMoney", ticketListBean.getEachTotalMoney());
			ticketMap.put("eachBetMode", ticketListBean.getEachBetMode());
			ticketList.add(ticketMap);
		}
		orderInfo.put("ticketList", ticketList);
		orderInfo.put("terminalId", infoBean.getTerminalId());
		orderInfo.put("terminal", infoBean.getTerminal());
		orderInfo.put("multiDraw", infoBean.getMultiDraw());
		orderInfo.put("betDouble", infoBean.getBetDouble());
		orderInfo.put("noteNumber", infoBean.getNoteNumber());
		orderInfo.put("totalMoney", infoBean.getTotalMoney());
		orderInfo.put("betMode", infoBean.getBetMode());
		orderInfo.put("drawId", infoBean.getDrawId());
		orderInfo.put("drawNumber", infoBean.getDrawNumber());
		orderInfo.put("gamePlayId", infoBean.getGamePlayId());
		orderInfo.put("dataSource", infoBean.getDataSource());
		data.put("orderInfo", orderInfo);
		return data;
	}

	//    快3
	public static com.alibaba.fastjson.JSONObject getData(kuai3order.DataBean.OrderInfoBean infoBean) {
		com.alibaba.fastjson.JSONObject data = new com.alibaba.fastjson.JSONObject();
		com.alibaba.fastjson.JSONObject orderInfo = new com.alibaba.fastjson.JSONObject();
		orderInfo.put("gameAlias", infoBean.getGameAlias());
		JSONArray ticketList = new JSONArray();
		for (TicketListBean ticketListBean : infoBean.getTicketList()) {
			com.alibaba.fastjson.JSONObject ticketMap = new com.alibaba.fastjson.JSONObject();
			ticketMap.put("ticket", ticketListBean.getTicket());
			ticketMap.put("eachTotalMoney", ticketListBean.getEachTotalMoney());
			ticketMap.put("eachBetMode", ticketListBean.getEachBetMode());
			ticketList.add(ticketMap);
		}
		orderInfo.put("ticketList", ticketList);
		orderInfo.put("terminal", infoBean.getTerminal());
		orderInfo.put("thirdUserId", infoBean.getThirdUserId());
		orderInfo.put("thirdPartyCode", infoBean.getThirdPartyCode());
		orderInfo.put("multiDraw", infoBean.getMultiDraw());
		orderInfo.put("betDouble", infoBean.getBetDouble());
		orderInfo.put("noteNumber", infoBean.getNoteNumber());
		orderInfo.put("totalMoney", infoBean.getTotalMoney());
		orderInfo.put("betMode", infoBean.getBetMode());
		orderInfo.put("drawNumber", infoBean.getDrawNumber());
		orderInfo.put("gamePlayNum", infoBean.getGamePlayNum());
		orderInfo.put("dataSource", infoBean.getDataSource());
		orderInfo.put("additional", infoBean.getAdditional());
		data.put("orderInfo", orderInfo);
		return data;
	}

	//    排列5
//	public static com.alibaba.fastjson.JSONObject getData(pailie5order.DataBean.OrderInfoBean infoBean) {
//		com.alibaba.fastjson.JSONObject data = new com.alibaba.fastjson.JSONObject();
//		com.alibaba.fastjson.JSONObject orderInfo = new com.alibaba.fastjson.JSONObject();
//		orderInfo.put("gameAlias", infoBean.getGameAlias());
//
//		JSONArray ticketList = new JSONArray();
//		for (pailie5order.DataBean.OrderInfoBean.TicketListBean ticketListBean : infoBean.getTicketList()) {
//			com.alibaba.fastjson.JSONObject ticketMap = new com.alibaba.fastjson.JSONObject();
//			ticketMap.put("ticket", ticketListBean.getTicket());
//			ticketMap.put("eachTotalMoney", ticketListBean.getEachTotalMoney());
//			ticketMap.put("eachBetMode", ticketListBean.getEachBetMode());
//			ticketList.add(ticketMap);
//		}
//		orderInfo.put("ticketList", ticketList);
//		orderInfo.put("terminal", infoBean.getTerminal());
//		orderInfo.put("thirdUserId", infoBean.getThirdUserId());
//		orderInfo.put("thirdPartyCode", infoBean.getThirdPartyCode());
//		orderInfo.put("multiDraw", infoBean.getMultiDraw());
//		orderInfo.put("betDouble", infoBean.getBetDouble());
//		orderInfo.put("noteNumber", infoBean.getNoteNumber());
//		orderInfo.put("totalMoney", infoBean.getTotalMoney());
//		orderInfo.put("betMode", infoBean.getBetMode());
//		orderInfo.put("drawNumber", infoBean.getDrawNumber());
//		orderInfo.put("dataSource", infoBean.getDataSource());
//		orderInfo.put("additional", infoBean.getAdditional());
//		data.put("orderInfo", orderInfo);
//		return data;
//	}

	//   36选7
//	public static com.alibaba.fastjson.JSONObject getData(_36x7order.DataBean.OrderInfoBean infoBean) {
//		com.alibaba.fastjson.JSONObject data = new com.alibaba.fastjson.JSONObject();
//		com.alibaba.fastjson.JSONObject orderInfo = new com.alibaba.fastjson.JSONObject();
//		orderInfo.put("gameAlias", infoBean.getGameAlias());
//
//		JSONArray ticketList = new JSONArray();
//		for (_36x7order.DataBean.OrderInfoBean.TicketListBean ticketListBean : infoBean.getTicketList()) {
//			com.alibaba.fastjson.JSONObject ticketMap = new com.alibaba.fastjson.JSONObject();
//			ticketMap.put("ticket", ticketListBean.getTicket());
//			ticketMap.put("eachTotalMoney", ticketListBean.getEachTotalMoney());
//			ticketMap.put("eachBetMode", ticketListBean.getEachBetMode());
//			ticketList.add(ticketMap);
//		}
//		orderInfo.put("ticketList", ticketList);
//		orderInfo.put("terminal", infoBean.getTerminal());
//		orderInfo.put("thirdUserId", infoBean.getThirdUserId());
//		orderInfo.put("thirdPartyCode", infoBean.getThirdPartyCode());
//		orderInfo.put("multiDraw", infoBean.getMultiDraw());
//		orderInfo.put("betDouble", infoBean.getBetDouble());
//		orderInfo.put("noteNumber", infoBean.getNoteNumber());
//		orderInfo.put("totalMoney", infoBean.getTotalMoney());
//		orderInfo.put("betMode", infoBean.getBetMode());
//		orderInfo.put("compoundStatus", infoBean.getCompoundStatus());
//		orderInfo.put("drawNumber", infoBean.getDrawNumber());
//		orderInfo.put("dataSource", infoBean.getDataSource());
//		orderInfo.put("additional", infoBean.getAdditional());
//		data.put("orderInfo", orderInfo);
//		return data;
//	}

	//    快乐8
//	public static com.alibaba.fastjson.JSONObject getData(kuaile8order.DataBean.OrderInfoBean infoBean) {
//		com.alibaba.fastjson.JSONObject data = new com.alibaba.fastjson.JSONObject();
//		com.alibaba.fastjson.JSONObject orderInfo = new com.alibaba.fastjson.JSONObject();
//		orderInfo.put("gameAlias", infoBean.getGameAlias());
//
//		JSONArray ticketList = new JSONArray();
//		for (kuaile8order.DataBean.OrderInfoBean.TicketListBean ticketListBean : infoBean.getTicketList()) {
//			com.alibaba.fastjson.JSONObject ticketMap = new com.alibaba.fastjson.JSONObject();
//			ticketMap.put("ticket", ticketListBean.getTicket());
//			ticketMap.put("eachTotalMoney", ticketListBean.getEachTotalMoney());
//			ticketMap.put("eachBetMode", ticketListBean.getEachBetMode());
//			ticketList.add(ticketMap);
//		}
//		orderInfo.put("ticketList", ticketList);
//		orderInfo.put("terminal", infoBean.getTerminal());
//		orderInfo.put("thirdUserId", infoBean.getThirdUserId());
//		orderInfo.put("thirdPartyCode", infoBean.getThirdPartyCode());
//		orderInfo.put("multiDraw", infoBean.getMultiDraw());
//		orderInfo.put("betDouble", infoBean.getBetDouble());
//		orderInfo.put("noteNumber", infoBean.getNoteNumber());
//		orderInfo.put("totalMoney", infoBean.getTotalMoney());
//		orderInfo.put("betMode", infoBean.getBetMode());
//		orderInfo.put("drawNumber", infoBean.getDrawNumber());
//		orderInfo.put("frisbeeStatus", infoBean.getFrisbeeStatus());
//		orderInfo.put("gamePlayNum", infoBean.getGamePlayNum());
//		orderInfo.put("dataSource", infoBean.getDataSource());
//		orderInfo.put("additional", infoBean.getAdditional());
//		data.put("orderInfo", orderInfo);
//		return data;
//	}

}
