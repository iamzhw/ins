package com.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cableInspection.model.PointModel;
import com.system.constant.AppType;
import com.system.constant.SysSet;

@SuppressWarnings("all")
public class Triangle {
	// 三边的长度
	public Double a = 0d;
	public Double b = 0d;
	public Double c = 0d;

	public String bfb(Double s) {
		NumberFormat num = NumberFormat.getPercentInstance();
		num.setMaximumIntegerDigits(3);
		num.setMaximumFractionDigits(2);
		return num.format(s);
	}

	public String gj(List<PointModel> list) {
		Double lon = 0.0;
		for (int i = 0; i < list.size() - 1; i++) {
			Double hh = MapDistance.getDistance(
					Double.valueOf(list.get(i).getLatitude()),
					Double.valueOf(list.get(i).getLongitude()),
					Double.valueOf(list.get(i + 1).getLatitude()),
					Double.valueOf(list.get(i + 1).getLongitude()));
			lon = lon + hh;
		}
		Double kime = lon / 1000;
		DecimalFormat df = new DecimalFormat("0.00");
		return String.valueOf(df.format(kime));
	}
	
	public double gj2(List<PointModel> list) {
		Double lon = 0.0;
		for (int i = 0; i < list.size() - 1; i++) {
			Double hh = MapDistance.getDistance(
					Double.valueOf(list.get(i).getLatitude()),
					Double.valueOf(list.get(i).getLongitude()),
					Double.valueOf(list.get(i + 1).getLatitude()),
					Double.valueOf(list.get(i + 1).getLongitude()));
			lon = lon + hh;
		}
		return lon / 1000;
	}

	public int arrivalPoints(List<PointModel> list1, List<PointModel> list2) {
		// 到位数量
		int sums = 0;
		// 应到设备
		// int sums1=list1.size();
		for (PointModel mapList : list1) {
			Double px1 = Double.valueOf(mapList.getLatitude());
			Double py1 = Double.valueOf(mapList.getLongitude());
			// 设备距离轨迹起点距离
			Double aa1;
			// 设备距离轨迹终点距离
			Double aa2;
			// 4分钟轨迹线段长度
			Double aa3;
			// 轨迹距离关键点距离
			Double jul;
			//
			int inaccuracy = Integer.valueOf(SysSet.CONFIG.get(AppType.LXXJ)
					.get(SysSet.INACCURACY));// 签到误差距离
			int locate_span = Integer.valueOf(SysSet.CONFIG.get(AppType.LXXJ)
					.get(SysSet.LOCATE_SPAN));// 定位时间间隔
			for (int i = 0; i < list2.size() - 1; i++) {
				aa1 = MapDistance.getDistance(px1, py1,
						Double.valueOf(list2.get(i).getLatitude()),
						Double.valueOf(list2.get(i).getLongitude()));
				aa2 = MapDistance.getDistance(px1, py1,
						Double.valueOf(list2.get(i + 1).getLatitude()),
						Double.valueOf(list2.get(i + 1).getLongitude()));
				aa3 = MapDistance.getDistance(
						Double.valueOf(list2.get(i).getLatitude()),
						Double.valueOf(list2.get(i).getLongitude()),
						Double.valueOf(list2.get(i + 1).getLatitude()),
						Double.valueOf(list2.get(i + 1).getLongitude()));
				
				// 判断时速有没有超过20
				if ((aa3 / 1000) / (locate_span / 3600) > 20) {
					continue;
				}
				
				// 若其中有一边小于50米，则改点到位
				if (aa1 < inaccuracy || aa2 < inaccuracy) {
					sums = sums + 1;
					continue;
				}
				if ((aa1 > aa2 && aa1 > aa3) || (aa2 > aa1 && aa2 > aa3)) {
					// 距离关键点距离jul为最短边 aa2或aa1上面已判断过都大于50米
				} else {
					Triangle tr1 = new Triangle(aa1, aa2, aa3);
					jul = tr1.getArea() * 2 / aa3;
					if (jul < inaccuracy) {
						sums = sums + 1;
						continue;
					}
				}
			}
		}
		return sums;
	}

	public List<Double> arrivalPointsList(List<PointModel> list1, List<PointModel> list2) {
		// 到位数量
		List<Double> pointList = new ArrayList<Double>();
		// 应到设备
		double k = 0.0000;
		double temp = 0.0000;
		Double px1 = 0.0000;
		Double py1 = 0.0000;
		// 设备距离轨迹起点距离
		Double aa1 = 0d;
		// 设备距离轨迹终点距离
		Double aa2 = 0d;
		// 4分钟轨迹线段长度
		Double aa3 = 0d;
		// 轨迹距离关键点距离
		Double jul = 0d;
		// int sums1=list1.size();
		Double px2 = 0.0;
		Double py2 = 0.0;
		Double px3 = 0.0;
		Double py3 = 0.0;
		for (PointModel mapList : list1) {
			px1 = Double.valueOf(mapList.getLatitude());
			py1 = Double.valueOf(mapList.getLongitude());
			
			//
			int inaccuracy = Integer.valueOf(SysSet.CONFIG.get(AppType.LXXJ)
					.get(SysSet.INACCURACY));// 签到误差距离
			int locate_span = Integer.valueOf(SysSet.CONFIG.get(AppType.LXXJ)
					.get(SysSet.LOCATE_SPAN));// 定位时间间隔
			for (int i = 0; i < list2.size() - 1; i++) {
				//如果上传两个连续相同的点，则继续遍历下一个点
				if(list2.get(i).getLatitude().equals(list2.get(i + 1).getLatitude())&&
						list2.get(i).getLongitude().equals(list2.get(i + 1).getLongitude()))
				{
					continue;
				}
				px2=Double.valueOf(list2.get(i).getLatitude());
				py2=Double.valueOf(list2.get(i).getLongitude());
				px3=Double.valueOf(list2.get(i + 1).getLatitude());
				py3=Double.valueOf(list2.get(i + 1).getLongitude());
				aa1 = MapDistance.getDistance(px1, py1,px2,py2);
				aa2 = MapDistance.getDistance(px1, py1,px3,py3);
				aa3 = MapDistance.getDistance(px2,py2,px3,py3);
				
				
				// 判断时速有没有超过20
//				k =(aa3 / 1000) ;
//				temp = Double.valueOf(locate_span)/3600;
//				//double speed = k/temp;
//				if (k/temp > 60) {
//					continue;
//				}
				
				// 若其中有一边小于50米，则改点到位
				if (aa1 <= inaccuracy || aa2 <= inaccuracy) {
					pointList.add(aa3);
					break;
				}
				/*if ((aa1 > aa2 && aa1 > aa3) || (aa2 > aa1 && aa2 > aa3)) {
					// 距离关键点距离jul为最短边 aa2或aa1上面已判断过都大于50米
				} 
				else */
				//如果是钝角三角形，直接不到位
				if(aa3 < 0000000.1 || (aa2 * aa2 + aa3 * aa3 - aa1 * aa1)/(2*aa2*aa3)<0 ||
						(aa1 * aa1 + aa3 * aa3 - aa2 * aa2)/(2*aa1*aa3)<0)
				{
					continue;
				}
				else {
					jul = new Triangle(aa1, aa2, aa3).getArea() * 2 / aa3;
					if (jul < inaccuracy) {
						pointList.add(aa3);
						break;
					}
				}
			}
		}
		return pointList;
	}

	// 看护到位率算法
	public List<PointModel> keepArrivalRate(List<Map> PlanPoints,int iningcuracy,List<PointModel> upPointsList) 
	{
		List<PointModel> arrivalRatePoints = new ArrayList<PointModel>();
		Double distance;
		for (int i = 0; i < upPointsList.size(); i++) {
			for (Map map : PlanPoints) {
				distance =MapDistance.getDistance(Double.valueOf(map.get("LATITUDE").toString()),
						Double.valueOf(map.get("LONGITUDE").toString()),
						Double.valueOf(upPointsList.get(i).getLatitude()),
						Double.valueOf(upPointsList.get(i).getLongitude()));
				if(distance<=iningcuracy)
				{
					arrivalRatePoints.add(upPointsList.get(i));
					break;
				}
			}
		}
		return arrivalRatePoints;
	}

	public Double getA() {
		return a;
	}

	public void setA(Double a) {
		this.a = a;
	}

	public Double getB() {
		return b;
	}

	public void setB(Double b) {
		this.b = b;
	}

	public Double getC() {
		return c;
	}

	public void setC(Double c) {
		this.c = c;
	}

	public Triangle(Double a, Double b, Double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Triangle() {
	}

	/**
	 * 得到周长
	 * 
	 * @return
	 */
	public Double getPerimeter() {
		Double l = 0d;
		l = a + b + c;
		return l;
	};

	/**
	 * 得到面积
	 * 
	 * @return
	 */
	public Double getArea() {
		Double s = 0d;
		Double p = getPerimeter() / 2;
		s = Math.sqrt(p * (p - a) * (p - b) * (p - c));
		return s;
	}

	/**
	 * 根据时间返回固定格式 的时间如1h20min
	 * 
	 * @return
	 */
	public String changTimeFormat(int minutes) {
		StringBuffer temp = new StringBuffer();
		// 大于一个小时
		if(minutes/60>0){
			temp.append(minutes / 60).append("h").append(
					minutes % 60 != 0 ? minutes % 60 : 0).append("min");
		}
		// 小于一个小时
		else
		{
			temp.append(minutes % 60 != 0 ? minutes % 60 : 0).append("min");
		}
		return temp.toString();
	}
}
