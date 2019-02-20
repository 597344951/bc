package com.zltel.broadcast.area_manage.util;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zltel.broadcast.area_manage.bean.MapAreaInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaLatLng;
import com.zltel.broadcast.area_manage.dao.MapAreaLatLngMapper;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;

import io.netty.util.internal.StringUtil;

/**
 * 判断设备在哪个区域里
 * @author 张毅
 * @since jdk1.8.0
 * Date: 2019.1.30
 */
@Component
public class LocationJudgment {
	
	
    private static MapAreaLatLngMapper mapAreaLatLngMapper;
    private static final double EARTH_RADIUS = 6371393;	//地球半径，用于根据坐标点计算出两点之间的距离
	
    @Resource
	public void setMapAreaLatLngMapper(MapAreaLatLngMapper mapAreaLatLngMapper) {
		LocationJudgment.mapAreaLatLngMapper = mapAreaLatLngMapper;
	}

    /**
     * 判断该点在哪个区域内
     * @param tbi	设备信息
     * @param mais	所有区域信息
     * @return
     * @throws AreaConflictException 设备所在区域冲突
     */
    public static MapAreaInfo isInPolygon(TerminalBasicInfo tbi, List<MapAreaInfo> mais) throws AreaConflictException {
        //设备坐标
    	if (StringUtil.isNullOrEmpty(tbi.getGis())) return null;
    	//gis保存经纬度信息，经度y坐标在前，纬度x在后
    	String[] tbiGis = tbi.getGis().split(",");
        Point2D.Double tbi_point = new Point2D.Double(Double.parseDouble(tbiGis[1]), Double.parseDouble(tbiGis[0]));
        
        List<MapAreaInfo> innerMapAreas = new ArrayList<>();	//该点在某个区域的集合，可能多个区域相交，点在公共区域里，而区域等级相同
        
        for (MapAreaInfo mai : mais) {
        	Map<String, Object> condition = new HashMap<>();
        	condition.put("infoId", mai.getId());
        	List<MapAreaLatLng> malls = mapAreaLatLngMapper.queryMapAreaLatLngs(condition);
        	
        	if ("rectangle".equals(mai.getShape())) {
        		//高德地图中没有斜的矩形，所以在高德中构造矩形只需要西南角和东北角的坐标信息
                //在数据库里只保存这两个点的信息，根据这两个点的坐标，拼成另外两个点的坐标信息
        		if (malls == null || malls.size() != 1) continue;
        		List<Point2D.Double> malls_point = new ArrayList<>();
        		//西北角坐标，横坐标是西南角横坐标，纵坐标是东北角纵坐标
        		Point2D.Double westnorth = new Point2D.Double(malls.get(0).getSouthwestLat().doubleValue(), 
        				malls.get(0).getNortheastLng().doubleValue());
        		//西南角坐标
        		Point2D.Double westsouth = new Point2D.Double(malls.get(0).getSouthwestLat().doubleValue(), 
        				malls.get(0).getSouthwestLng().doubleValue());
        		//东南角坐标，横坐标是东北角横坐标，纵坐标是西南角纵坐标
        		Point2D.Double eastsouth = new Point2D.Double(malls.get(0).getNortheastLat().doubleValue(), 
        				malls.get(0).getSouthwestLng().doubleValue());
        		//东北角坐标
        		Point2D.Double eastnorth = new Point2D.Double(malls.get(0).getNortheastLat().doubleValue(), 
        				malls.get(0).getNortheastLng().doubleValue());
        		malls_point.add(westnorth);
        		malls_point.add(westsouth);
        		malls_point.add(eastsouth);
        		malls_point.add(eastnorth);
        		if (check(tbi_point, malls_point)) {
        			innerMapAreas.add(mai);	//这个点在此区域里
        		}
        	} else if ("polygon".equals(mai.getShape())) {
        		if (malls == null || malls.size() == 0) continue;
        		List<Point2D.Double> malls_point = new ArrayList<>();
        		for (MapAreaLatLng mall : malls) {
        			Point2D.Double mall_point = new Point2D.Double(mall.getLat().doubleValue(), mall.getLng().doubleValue());
        			malls_point.add(mall_point);
				}
        		if (check(tbi_point, malls_point)) {
        			innerMapAreas.add(mai);	//这个点在此区域里
        		}
			} else if ("circle".equals(mai.getShape())) {
				if (malls == null || malls.size() != 1) continue;
				Point2D.Double circlePoint = new Point2D.Double(malls.get(0).getLat().doubleValue(), malls.get(0).getLng().doubleValue());
				Double line = getDistance(circlePoint, tbi_point);
				if (line <= malls.get(0).getRadius().doubleValue()) {
					//在圆里或在圆环上
					innerMapAreas.add(mai);	//这个点在此区域里
				}
			}
		}
        List<MapAreaInfo> finalMapAreas = null;	//保存优先级最高的区域，可能有两个或更多
        if (innerMapAreas != null && innerMapAreas.size() > 0) {
        	if (innerMapAreas.size() == 1) {
        		return innerMapAreas.get(0);
        	} else {
        		//该设备在多个区域内
        		finalMapAreas = new ArrayList<>();
        		int level = -1;
        		for (MapAreaInfo mai : innerMapAreas) {
					if (level == -1 || level >= mai.getAreaLevel()) {	//发现优先级更高或相同的区域
						level = mai.getAreaLevel();	
						finalMapAreas = new ArrayList<>();
						finalMapAreas.add(mai);
					}
				}
        		if (finalMapAreas != null && finalMapAreas.size() == 1) {
        			return finalMapAreas.get(0);
        		} else {
        			//该设备所处的地方在多个区域的相交点，且这些区域优先级一样，不知道把该设备划分到哪个区域
        			throw new AreaConflictException("该设备所处的地方在多个区域的相交点，且这些区域优先级一样，不知道把该设备划分到哪个区域");
        		}
        	}
        } else {
        	return null;
        }
    }
    
    /**
     * 通过AB点经纬度获取距离
     * @param pointA A点(经，纬)
     * @param pointB B点(经，纬)
     * @return 距离(单位：米)
     */
    public static double getDistance(Point2D pointA, Point2D pointB) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(pointA.getX()); // A经弧度
        double radiansAY = Math.toRadians(pointA.getY()); // A纬弧度
        double radiansBX = Math.toRadians(pointB.getX()); // B经弧度
        double radiansBY = Math.toRadians(pointB.getY()); // B纬弧度
 
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
        double acos = Math.acos(cos); // 反余弦值
        return EARTH_RADIUS * acos; // 最终结果
    }
 
    /**
     * 点是否在区域里
     * @param tbi_point 要判断的点
     * @param malls_point 区域坐标点，包括矩形和多边形
     * @return
     */
    private static boolean check(Point2D.Double tbi_point, List<Point2D.Double> malls_point) {
        GeneralPath peneralPath = new GeneralPath();
 
        Point2D.Double first = malls_point.get(0);
        // 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
        peneralPath.moveTo(first.x, first.y);
        malls_point.remove(0);
        for (Point2D.Double d : malls_point) {
            // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
            peneralPath.lineTo(d.x, d.y);
        }
        // 将几何多边形封闭
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        // 测试指定的 Point2D 是否在 Shape 的边界内。
        return peneralPath.contains(tbi_point);
    }
}
