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
    private static final String script_select_start =
            "<select id=\"#ID#\" resultType=\"#CLASSNAME#\" parameterType=\"#CLASSNAME#\">";
    private static final String script_select_end = "</select>";

    private static final String script_update_start =
            "<update id=\"#ID#\"   parameterType=\"#CLASSNAME#\">";
    private static final String script_update_end = "</update>";

    private static final String script_delete_start =
            "<delete id=\"#ID#\"   parameterType=\"#CLASSNAME#\">";
    private static final String script_delete_end = "</delete>";

    private static final String script_insert_start =
            "<insert id=\"#ID#\"   parameterType=\"#CLASSNAME#\">";
    private static final String script_insert_end = "</insert>";

    private static final String select_temple = "select * from #TABLE# ";
    private static final String update_temple = "update #TABLE# ";
    private static final String delete_temple = "delete from #TABLE# ";
    private static final String insert_temple = "insert into #TABLE# ";

    private static final String where_start = "<where>";
    private static final String where_end = " </where>";

    private static final String if_test_start = "<if test=\"#FIELD# != null\">";
    private static final String if_test_end = "</if>";

    private static final String id_list = "list";
    private static final String id_insert = "save";
    private static final String id_update = "update";
    private static final String id_delete = "delete";

    /**
     * 返回 Where 脚本
     * 
     * @param c
     * @return
     */
    public static <T> StringBuffer createWhereScript(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        sb.append("<sql id=\"" + getWhereId(c.getSimpleName()) + "\">").append("\n");
        sb.append(" ").append(where_start).append("\n");
        Set<String> fs = _fields(c);
        for (String k : fs) {
            // if
            sb.append("  ").append(if_test_start.replaceAll("#FIELD#", k)).append("\n");
            sb.append("    AND ").append(toDataBaseName(k)).append("=#{").append(k).append("} \n");
            sb.append("  ").append(if_test_end).append("\n");
        }
        sb.append("").append(where_end).append("\n");
        sb.append("</sql>").append("\n");
        return sb;
    }

    /** 生成 WHERE 参数条件 **/
    public static <T> StringBuffer createWhereParamsScript(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        sb.append("<sql id=\"" + getWhereId(c.getSimpleName()) + "\">").append("\n");
        sb.append(" ").append(where_start).append("\n");
        Set<String> fs = _fields(c);
        for (String k : fs) {
            // if
            sb.append("  ").append(if_test_start.replaceAll("#FIELD#", k)).append("\n");
            sb.append("    AND ").append(toDataBaseName(k)).append("=#{").append(k).append("} \n");
            sb.append("  ").append(if_test_end).append("\n");
        }
        sb.append("").append(where_end).append("\n");
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
    public static <T> StringBuffer createSelectScript(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        String s1 = script_select_start.replaceAll("#CLASSNAME#", c.getName()).replaceAll("#ID#",
                id_list + c.getSimpleName());
        sb.append(s1).append("\n");
        // select * from table
        sb.append(replaceTableName(select_temple, c.getSimpleName())).append("\n");
        // where
        sb.append("<include refid=\"" + getWhereId(c.getSimpleName()) + "\" />").append("\n");
        sb.append(script_select_end).append("\n");
        return sb;
    }

    /**
     * 创建 Update 脚本
     * 
     * @param t
     * @return
     */
    public static <T> StringBuffer createUpdateScript(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        String s1 = script_update_start.replaceAll("#CLASSNAME#", c.getSimpleName())
                .replaceAll("#ID#", id_update + c.getSimpleName());
        sb.append(s1).append("\n");
        sb.append(replaceTableName(update_temple, c.getSimpleName())).append("\n");

        Set<String> fs = _fields(c);
        sb.append(" <set>\n");
        for (String k : fs) {
            // if
            sb.append("  ").append(if_test_start.replaceAll("#FIELD#", k)).append("\n");
            sb.append("     ").append(toDataBaseName(k)).append("=#{").append(k).append("},\n");
            sb.append("  ").append(if_test_end).append("\n");
        }
        sb.append(" </set>\n");
        sb.append("where 1!=1 \n");
        sb.append(script_update_end).append("\n");
        return sb;
    }

    public static <T> StringBuffer createDeleteScript(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        String s1 = script_delete_start.replaceAll("#CLASSNAME#", c.getSimpleName())
                .replaceAll("#ID#", id_delete + c.getSimpleName());
        sb.append(s1).append("\n");
        sb.append(replaceTableName(delete_temple, c.getSimpleName())).append("\n");
        sb.append("<include refid=\"" + getWhereId(c.getSimpleName()) + "\" />").append("\n");
        sb.append(script_delete_end).append("\n");
        return sb;
    }

    public static <T> StringBuffer createInsertScript(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        String s1 = script_insert_start.replaceAll("#CLASSNAME#", c.getSimpleName())
                .replaceAll("#ID#", id_insert + c.getSimpleName());
        sb.append(s1).append("\n");
        sb.append(replaceTableName(insert_temple, c.getSimpleName())).append("\n");
        Set<String> fs = _fields(c);
        sb.append("(").append("\n");
        // 去掉最后一个 ,
        sb.append("<trim suffixOverrides=\",\"> ").append("\n");
        for (String k : fs) {
            // if
            sb.append("  ").append(if_test_start.replaceAll("#FIELD#", k)).append("\n");
            sb.append("     ").append(toDataBaseName(k)).append(",\n");
            sb.append("  ").append(if_test_end).append("\n");
        }
        sb.append("</trim>").append("\n");
        sb.append(")").append("\n");
        sb.append("VALUES").append("\n");
        sb.append("(").append("\n");
        sb.append("<trim suffixOverrides=\",\"> ").append("\n");
        for (String k : fs) {
            // if
            sb.append("  ").append(if_test_start.replaceAll("#FIELD#", k)).append("\n");
            sb.append("     ").append("#{").append(k).append("},\n");
            sb.append("  ").append(if_test_end).append("\n");
        }
        sb.append("</trim>").append("\n");

        sb.append(")").append("\n");
        sb.append(script_insert_end).append("\n");
        return sb;
    }


    /**
     * 创建接口
     * 
     * @param c
     * @return
     */
    public static <T> StringBuffer createInterface(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        String sn = c.getSimpleName();
        sb.append("public List<" + sn + "> ").append(id_list + sn)
                .append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        sb.append("public void ").append(id_insert + sn)
                .append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        sb.append("public void ").append(id_update + sn)
                .append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        sb.append("public void ").append(id_delete + sn)
                .append("(" + sn + " " + sn.toLowerCase() + ");").append("\n");
        return sb;
    }

    /**
     * 创建接口实现
     * 
     * @param c
     * @return
     */
    public static <T> StringBuffer createInterfaceImpl(Class<T> c) {
        StringBuffer sb = new StringBuffer();
        String sn = c.getSimpleName();
        String mn = id_list + sn;
        String param = sn.toLowerCase();
        sb.append("/* getDao() Method need*/").append("\n\n");
        sb.append("public List<" + sn + "> ").append(mn)
                .append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append("   return this.getDao().").append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        mn = id_insert + sn;
        sb.append("public void ").append(id_insert + sn)
                .append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append("   this.getDao().").append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        mn = id_update + sn;
        sb.append("public void ").append(id_update + sn)
                .append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append("   this.getDao().").append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        mn = id_delete + sn;
        sb.append("public void ").append(id_delete + sn)
                .append("(" + sn + " " + sn.toLowerCase() + "){").append("\n");
        sb.append("   this.getDao().").append(mn).append("(" + param + ")").append(";\n");
        sb.append("}").append("\n");
        return sb;
    }

    /**
     * 获取当前类所有 实例变量
     * 
     * @param c
     * @return
     */
    public static <T> Set<String> _fields(Class<T> c) {
        Set<String> fields = new HashSet<>();
        if (c == Object.class) {
            return null;
        }
        // 处理父类
        Set<String> rs = _fields(c.getSuperclass());
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
        StringBuffer sb = new StringBuffer();
        boolean f = true;
        for (char c : fn.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (!f) {
                    sb.append("_");
                }
            }
            sb.append(Character.toLowerCase(c));
            f = false;
        }
        return sb.toString();
    }
}
