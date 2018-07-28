package com.zltel.broadcast.um.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zltel.broadcast.common.tree.TreeNode;

/**
 * xml工具
 * @author 张毅
 * @since jdk1.8.0_172
 * time: 2018.7.17
 */
public class XMLUtil {
	/**
	 * 初始化组织默认积分结构
	 */
	public static List<TreeNode<Map<String, String>>> getOrgIntegralConstitutes() {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(XMLUtil.class.getResourceAsStream("/orgIntegralConstitute.xml"));
			Element ele = document.getRootElement();
			List<TreeNode<Map<String, String>>> treeNodes = new ArrayList<>();
			getOrgIntegralConstitutesNodes(ele, treeNodes);
			return treeNodes;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 遍历所有节点
	 * @param ele
	 */
	@SuppressWarnings("rawtypes")
	public static void getOrgIntegralConstitutesNodes(Element ele, List<TreeNode<Map<String, String>>> treeNodes) {
		for(Iterator i = ele.elementIterator(); i.hasNext();){	//遍历根节点下的子节点
            Element employee = (Element) i.next();
            TreeNode<Map<String, String>> treeNode = new TreeNode<>();
            Map<String, String> integralInfo = new HashMap<>();
            for(Iterator j = employee.elementIterator(); j.hasNext();){	//遍历子节点下的内容
                Element node=(Element) j.next();
                if ("childrens".equals(node.getName())) {	//则为子节点的子节点
                	List<TreeNode<Map<String, String>>> treeChildrenNodes = new ArrayList<>();
                	treeNode.setChildren(treeChildrenNodes);
                	getOrgIntegralConstitutesNodes(node, treeNode.getChildren());
                } else {
                	integralInfo.put(node.getName(), node.getText());
                }
                treeNode.setData(integralInfo);
            }
            treeNodes.add(treeNode);
        }
	}
}
