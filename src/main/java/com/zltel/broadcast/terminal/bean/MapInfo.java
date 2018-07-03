package com.zltel.broadcast.terminal.bean;

import java.util.List;

public class MapInfo {
    String icon;
    String title;
    String[] position;   
    List<String> content;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String[] getPosition() {
        return position;
    }

    public void setPosition(String[] strings) {
        this.position = strings;
    }
    
  
    


}
