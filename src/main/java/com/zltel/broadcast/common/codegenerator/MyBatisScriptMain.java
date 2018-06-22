package com.zltel.broadcast.common.codegenerator;
/**
 * @junit {@link MyBatisScriptMainTest}
 */
public class MyBatisScriptMain {
    private MyBatisScriptMain() {}
	/**
	 * 生成 CRUD 脚本 配置
	 * 
	 * @param c
	 * @return
	 */
	public static final <T> StringBuilder createCRUDScript(Class<T> c) {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(createMsg("保存 " + c.getSimpleName())).append("\n");
		sb.append(MyBatisScriptCreateUtil.createInsertScript(c)).append("\n");
		sb.append(createMsg("删除 " + c.getSimpleName())).append("\n");
		sb.append(MyBatisScriptCreateUtil.createDeleteScript(c)).append("\n");
		sb.append(createMsg("更新 " + c.getSimpleName())).append("\n");
		sb.append(MyBatisScriptCreateUtil.createUpdateScript(c)).append("\n");
		sb.append(createMsg("查询 " + c.getSimpleName())).append("\n");
		sb.append(MyBatisScriptCreateUtil.createSelectScript(c)).append("\n");
		sb.append(createMsg("查询条件 " + c.getSimpleName())).append("\n");
		sb.append(MyBatisScriptCreateUtil.createWhereScript(c)).append("\n\n");
		return sb;
	}

	public static final String createMsg(String msg) {
		return "<!-- " + msg + " -->";
	}

	/**
	 * @param c
	 * @return
	 */
	public static final <T> StringBuilder createInterface(Class<T> c) {
		StringBuilder sb = new StringBuilder();
		sb.append(MyBatisScriptCreateUtil.createInterface(c)).append("\n");
		return sb;
	}

	public static final <T> StringBuilder createInterfaceImpl(Class<T> c) {
		return MyBatisScriptCreateUtil.createInterfaceImpl(c);
	}

}
