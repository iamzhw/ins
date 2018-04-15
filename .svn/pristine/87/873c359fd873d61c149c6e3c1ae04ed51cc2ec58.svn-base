package com.util;

import java.util.List;
import java.util.Map;


public class MapDistance {

	private static double EARTH_RADIUS = 6370996.81;

	private static Double rad(double d) {
		//return new BigDecimal(d).multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180.0));
		return d * Math.PI / 180.0;
	}

	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 0d;
		s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		//s = Math.round(s * 10000) / 10000;
		return s;
	}
	/*public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		BigDecimal radLat1 = rad(lat1);
		BigDecimal radLat2 = rad(lat2);
		BigDecimal a = radLat1.subtract(radLat2);
		BigDecimal b = rad(lng1).subtract(rad(lng2));
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a.divide(new BigDecimal(2)).doubleValue()), 2)
				+ Math.cos(radLat1.doubleValue()) * Math.cos(radLat2.doubleValue())
				* Math.pow(Math.sin(b.divide(new BigDecimal(2)).doubleValue()), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}*/
	
	/**
	 * 计算点3到点1和点2的距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @param lat3
	 * @param lng3
	 * @return 点到线段的距离
	 */
	public static double getPointLineDistance(double lat1, double lng1, double lat2, double lng2, double lat3,
			double lng3, Map<String, String> matchLabel) {

		double a = Math.abs(getDistance(lat1, lng1, lat2, lng2));
		double b = Math.abs(getDistance(lat1, lng1, lat3, lng3));
		double c = Math.abs(getDistance(lat3, lng3, lat2, lng2));

		double ans = 0;
		if (c + b == a) {// 点在线段上
			ans = 0;
			return ans;
		}
		if (a <= 0.00001) {// 不是线段，是一个点
			ans = b;
			return ans;
		}
		if (c * c >= a * a + b * b) { // 组成直角三角形或钝角三角形，点1为直角或钝角
			if (c > 300) {
				matchLabel.put("site", "1");// 轨迹1离设备点比较近,且轨迹2离设备点大于300米
			}
			ans = b;
			return ans;
		}
		if (b * b >= a * a + c * c) {// 组成直角三角形或钝角三角形，点2为直角或钝角
			if (b > 300) {
				matchLabel.put("site", "2");// 轨迹2离设备点比较近,且轨迹1离设备点大于300米
			}
			ans = c;
			return ans;
		}
		// 组成锐角三角形，则求三角形的高
		double p0 = (a + b + c) / 2;// 半周长
		double s = Math.sqrt(p0 * (p0 - a) * (p0 - b) * (p0 - c));// 海伦公式求面积
		ans = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
		return ans;
	}
	
	public static boolean pointInPolygon(List<Map<String, Object>> polySides, double x, double y) {

		double[] polyY = new double[polySides.size()];
		double[] polyX = new double[polySides.size()];
		for (int k = 0; k < polySides.size(); k++) {
			polyX[k] = Double.parseDouble(polySides.get(k).get("X").toString());
			polyY[k] = Double.parseDouble(polySides.get(k).get("Y").toString());
		}

		int j = polySides.size() - 1;
		boolean oddNodes = false;

		for (int i = 0; i < polySides.size(); i++) {
			if ((polyY[i] < y && polyY[j] >= y || polyY[j] < y && polyY[i] >= y) 
					&& (polyX[i] <= x || polyX[j] <= x)) {
				if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i]) * (polyX[j] - polyX[i]) < x) {
					oddNodes = !oddNodes;
				}
			}
			j = i;
		}

		return oddNodes;
	}
}