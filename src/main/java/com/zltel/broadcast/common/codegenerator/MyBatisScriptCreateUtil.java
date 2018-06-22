package com.zltel.broadcast.common.codegenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * mybatis XML增删改查 脚本数据工具类
 * 
 * @junit {@link MyBatisScriptCreateUtilTest}
 */
public class MyBatisScriptCreateUtil {
    private static final String THIS_GET_DAO = "   this.getDao().";

    private MyBatisScriptCreateUtil() {}

    private static final String PUBLIC_VOID = "public void ";
    private static final String CLASSNAME_REPLACE = "#CLASSNAME#";
    private static final String FIELD_REPLACE = "#FIELD#";

    private static final String SCRIPT_SELECT_START =
            "<select id=\"#ID#\" resultType=\"#CLASSNAME#\" parameterType=\"#CLASSNAME#\">";
    private static final String SCRIPT_SELECT_END = "</select>";

    private static final String SCRIPT_UPDATE_START = "<update id=\"#ID#\"   parameterType=\"#CLASSNAME#\">";
    private static final String SCRIPT_UPDATE_END = "</update>";

    private static final String SCRIPT_DELETE_START = "<delete id=\"#ID#\"   parameterType=\"#CLASSNAME#\">";
    private static final String SCRIPT_DELETE_END = "</delete>";

    private static final String SCRIPT_INSERT_START = "<insert id=\"#ID#\"   parameterType=\"#CLASSNAME#\">";
    private static final String SCRIPT_INSERT_END = "</insert>";

    private static final String SELECT_TEMPLE = "select * from #TABLE# ";
    private static final String UPDATE_TEMPLE = "update #TABLE# ";
    private static final String DELETE_TEMPLE = "delete from #TABLE# ";
    private static final String INSERT_TEMPLE = "insert into #TABLE# ";

    private static final String WHERE_START = "<where>";
    private static final String WHERE_END = " </where>";

    private static final String IF_TEST_START = "<if test=\"#FIELD# != null\">";
    private static final String IF_TEST_END = "</if>";

    private static final String ID_LIST = "list";
    private static final String ID_INSERT = "save";
    private static final String ID_UPDATE = "update";
    private static final String ID_DELETE = "delete";

    /**
     * 返回 Where 脚本
     * 
     * @param c
     * @return
     */
    public static <T> StringBuilder createWhereScript(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        sb.append("<sql id=\"" + getWhereId(c.getSimpleName()) + "\">").append("\n");
        sb.append(" ").append(WHERE_START).append("\n");
        Set<String> fs = readFieldNames(c);
        for (String k : fs) {
            // if
            sb.append("  ").append(IF_TEST_START.replaceAll(FIELD_REPLACE, k)).append("\n");
            sb.append("    AND ").append(toDataBaseName(k)).append("=#{").append(k).append("} \n");
            sb.append("  ").append(IF_TEST_END).append("\n");
        }
        sb.append("").append(WHERE_END).append("\n");
        sb.append("</sql>").append("\n");
        return sb;
    }

    /** 生成 WHERE 参数条件 **/
    public static <T> StringBuilder createWhereParamsScript(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        sb.append("<sql id=\"" + getWhereId(c.getSimpleName()) + "\">").append("\n");
        sb.append(" ").append(WHERE_START).append("\n");
        Set<String> fs = readFieldNames(c);
        for (String k : fs) {
            // if
            sb.append("  ").append(IF_TEST_START.replaceAll(FIELD_REPLACE, k)).append("\n");
            sb.append("    AND ").append(toDataBaseName(k)).append("=#{").append(k).append("} \n");
            sb.append("  ").append(IF_TEST_END).append("\n");
        }
        sb.append("").append(WHERE_END).append("\n");
        sb.append("</sql>").append("\n");
        return sb;
    }

    public static String getId(String cn) {
        if (cn.length() <= 1) {
            return cn.toLowerCase();
        }
        return cn.substring(0, 1).toLowerCase() + cn.substring(1);
    }

    public static String getWhereId(String cn) {
        return cn + "Where";
    }

    public static String replaceTableName(String temple, String cn) {
        return temple.replaceAll("#TABLE#", cn);
    }

    /**
     * 创建 select 脚本
     * 
     * @param t
     * @return
     */
    public static <T> StringBuilder createSelectScript(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        String s1 = SCRIPT_SELECT_START.replaceAll(CLASSNAME_REPLACE, c.getName()).replaceAll("#ID#",
                ID_LIST + c.getSimpleName());
        sb.append(s1).append("\n");
        // select * from table
        sb.append(replaceTableName(SELECT_TEMPLE, c.getSimpleName())).append("\n");
        // where
        sb.append("<include refid=\"" + getWhereId(c.getSimpleName()) + "\" />").append("\n");
        sb.append(SCRIPT_SELECT_END).append("\n");
        return sb;
    }

    /**
     * 创建 Update 脚本
     * 
     * @param t
     * @return
     */
    public static <T> StringBuilder createUpdateScript(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        String s1 = SCRIPT_UPDATE_START.replaceAll(CLASSNAME_REPLACE, c.getSimpleName()).replaceAll("#ID#",
                ID_UPDATE + c.getSimpleName());
        sb.append(s1).append("\n");
        sb.append(replaceTableName(UPDATE_TEMPLE, c.getSimpleName())).append("\n");

        Set<String> fs = readFieldNames(c);
        sb.append(" <set>\n");
        for (String k : fs) {
            // if
            sb.append("  ").append(IF_TEST_START.replaceAll(FIELD_REPLACE, k)).append("\n");
            sb.append("  ").append(toDataBaseName(k)).append("=#{").append(k).append("},\n");
            sb.append("  ").append(IF_TEST_END).append("\n");
        }
        sb.append(" </set>\n");
        sb.append("where 1!=1 \n");
        sb.append(SCRIPT_UPDATE_END).append("\n");
        return sb;
    }

    public static <T> StringBuilder createDeleteScript(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        String s1 = SCRIPT_DELETE_START.replaceAll(CLASSNAME_REPLACE, c.getSimpleName()).replaceAll("#ID#",
                ID_DELETE + c.getSimpleName());
        sb.append(s1).append("\n");
        sb.append(replaceTableName(DELETE_TEMPLE, c.getSimpleName())).append("\n");
        sb.append("<include refid=\"" + getWhereId(c.getSimpleName()) + "\" />").append("\n");
        sb.append(SCRIPT_DELETE_END).append("\n");
        return sb;
    }

    public static <T> StringBuilder createInsertScript(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        String s1 = SCRIPT_INSERT_START.replaceAll(CLASSNAME_REPLACE, c.getSimpleName()).replaceAll("#ID#",
                ID_INSERT + c.getSimpleName());
        sb.append(s1).append("\n");
        sb.append(replaceTableName(INSERT_TEMPLE, c.getSimpleName())).append("\n");
        Set<String> fs = readFieldNames(c);
        sb.append("(").append("\n");
        // 去掉最后一个 ,
        sb.append("<trim suffixOverrides=\",\"> ").append("\n");
        for (String k : fs) {
            // if
            sb.append("  ").append(IF_TEST_START.replaceAll(FIELD_REPLACE, k)).append("\n");
            sb.append("     ").append(toDataBaseName(k)).append(",\n");
            sb.append("  ").append(IF_TEST_END).append("\n");
        }
        sb.append("</trim>").append("\n");
        sb.append(")").append("\n");
        sb.append("VALUES").append("\n");
        sb.append("(").append("\n");
        sb.append("<trim suffixOverrides=\",\"> ").append("\n");
        for (String k : fs) {
            // if
            sb.append("  ").append(IF_TEST_START.replaceAll(FIELD_REPLACE, k)).append("\n");
            sb.append("     ").append("#{").append(k).append("},\n");
            sb.append("  ").append(IF_TEST_END).append("\n");
        }
        sb.append("</trim>").append("\n");

        sb.append(")").append("\n");
        sb.append(SCRIPT_INSERT_END).append("\n");
        return sb;
    }


    /**
     * 创建接口
     * 
     * @param c
     * @return
     */
    public static <T> StringBuilder createInterface(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        String sn = c.getSimpleName();
        sb.append("public List<" + sn + "> ").append(ID_LIST + sn).append("(" + sn + " " + sn.toLowerCase() + ");")
                .append("\n");
        sb.append(PUBLIC_VOID).append(ID_INSERT + sn).append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        sb.append(PUBLIC_VOID).append(ID_UPDATE + sn).append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        sb.append(PUBLIC_VOID).append(ID_DELETE + sn).append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        return sb;
    }

    /**
     * 创建接口实现
     * 
     * @param c
     * @return
     */
    public static <T> StringBuilder createInterfaceImpl(Class<T> c) {
        StringBuilder sb = new StringBuilder();
        String sn = c.getSimpleName();
        String mn = ID_LIST + sn;
        String param = sn.toLowerCase();
        sb.append("/* getDao() Method need*/").append("\n\n");
        sb.append("public List<" + sn + "> ").append(mn).append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append("   return this.getDao().").append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        mn = ID_INSERT + sn;
        sb.append(PUBLIC_VOID).append(ID_INSERT + sn).append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append(THIS_GET_DAO).append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        mn = ID_UPDATE + sn;
        sb.append(PUBLIC_VOID).append(ID_UPDATE + sn).append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append(THIS_GET_DAO).append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        mn = ID_DELETE + sn;
        sb.append(PUBLIC_VOID).append(ID_DELETE + sn).append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append(THIS_GET_DAO).append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        return sb;
    }

    /**
     * 获取当前类所有 实例变量
     * 
     * @param c
     * @return
     */
    public static <T> Set<String> readFieldNames(Class<T> c) {
        Set<String> fields = new HashSet<>();
        if (c == Object.class) {
            return fields;
        }
        // 处理父类
        Set<String> rs = readFieldNames(c.getSuperclass());
        if (rs != null) {
            fields.addAll(rs);
        }
        Field[] fs = c.getDeclaredFields();
        for (Field f : fs) {
            boolean isStatic = Modifier.isStatic(f.getModifiers());
            if (!isStatic) fields.add(f.getName());
        }
        return fields;
    }

    /**
     * 根据驼峰命名规则,转化为数据库中字段名称
     * 
     * @junit {@link MyBatisScriptCreateUtilTest#testToDataBaseName()}
     */
    public static String toDataBaseName(String fn) {
        StringBuilder sb = new StringBuilder();
        boolean f = true;
        for (char c : fn.toCharArray()) {
            if (Character.isUpperCase(c) && !f) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
            f = false;
        }
        return sb.toString();
    }
}
