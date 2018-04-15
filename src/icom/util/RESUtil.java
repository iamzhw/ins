package icom.util;

import org.apache.ibatis.annotations.Case;

public class RESUtil {
	
	public static void main(String[] args) {
		String areaId="3";
		String res=getRes(areaId);
		System.out.println(res);
	}
	
	public static String getRes(String areaId){
		int area=Integer.valueOf(areaId);
		String res="";
		switch (area) {
		case 3:
			res="resnj";
			break;
		case 4:
			res="reszj";
			break;
		case 15:
			res="reswx";
			break;
		case 20:
			res="ressz";
			break;
		case 26:
			res="resnt";
			break;
		case 33:
			res="resyz";
			break;
		case 39:
			res="resyc";
			break;
		case 48:
			res="resxz";
			break;
		case 60:
			res="resha";
			break;
		case 63:
			res="reslyg";
			break;
		case 69:
			res="rescz";
			break;
		case 79:
			res="restz";
			break;
		case 84:
			res="ressq";
			break;
		}
		return res;
	}

}
