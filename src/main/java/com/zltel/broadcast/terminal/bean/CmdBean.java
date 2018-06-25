package com.zltel.broadcast.terminal.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送命令 bean <br/>
 * <table>
 * <thead>
 * <tr>
 * <th>名称（commandName）</th>
 * <th>类指令参数（command）</th>
 * <th>指令（commandContent）</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>开始播放</td>
 * <td>play</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>开始播放</td>
 * <td>play</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>开始播放</td>
 * <td>play</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>停止播放</td>
 * <td>play</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>音量+</td>
 * <td>volume</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>音量-</td>
 * <td>volume</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>静音</td>
 * <td>volume</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td>取消静音</td>
 * <td>volume</td>
 * <td>3</td>
 * </tr>
 * <tr>
 * <td>截屏</td>
 * <td>screenshot</td>
 * <td>600|600</td>
 * </tr>
 * <tr>
 * <td>打开播放器</td>
 * <td>ocscreen</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>关闭播放器</td>
 * <td>ocscreen</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>打开显示屏</td>
 * <td>wakelock</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>关闭显示屏</td>
 * <td>wakelock</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>清空数据</td>
 * <td>cleardata</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>刷新页面</td>
 * <td>refresh</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>升级</td>
 * <td>upgrade</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>关机</td>
 * <td>shutdown</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>重启</td>
 * <td>reboot</td>
 * <td>1</td>
 * </tr>
 * </tbody>
 * </table>
 * @author wangch
 *
 **/
public class CmdBean {
    @NotNull(message = "操作终端不能为空")
    private int screenId;
    @NotBlank(message = "操作命令不能为空")
    private String commandName;
    @NotBlank(message = "操作命令不能为空")
    private String command;
    @NotBlank(message = "操作命令不能为空")
    private String commandContent;


    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandContent() {
        return commandContent;
    }

    public void setCommandContent(String commandContent) {
        this.commandContent = commandContent;
    }


}
