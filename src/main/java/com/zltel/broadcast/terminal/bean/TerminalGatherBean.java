package com.zltel.broadcast.terminal.bean;

import java.util.List;

public class TerminalGatherBean {
    private TerminalGroup tg;
    private List<Integer> tbiss;
    private List<TerminalBasicInfo> tbis;

    private List<Integer> oids;

    public TerminalGroup getTg() {
        return tg;
    }

    public void setTg(TerminalGroup tg) {
        this.tg = tg;
    }

    public List<Integer> getOids() {
        return oids;
    }

    public void setOids(List<Integer> oids) {
        this.oids = oids;
    }

    public List<Integer> getTbiss() {
        return tbiss;
    }

    public void setTbiss(List<Integer> tbiss) {
        this.tbiss = tbiss;
    }

    public List<TerminalBasicInfo> getTbis() {
        return tbis;
    }

    public void setTbis(List<TerminalBasicInfo> tbis) {
        this.tbis = tbis;
    }


}
