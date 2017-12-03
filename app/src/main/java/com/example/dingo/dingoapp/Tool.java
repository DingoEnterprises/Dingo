package com.example.bim.dingo;


public class Tool {
    private String _id;
    private String _toolname;


    public Tool() {
    }
    public Tool(String id, String toolname) {
        _id = id;
        _toolname = toolname;

    }
    public Tool(String toolname) {
        _toolname = toolname;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void set_toolName(String toolname) {
        _toolname = toolname;
    }
    public String getToolName() {
        return _toolname;
    }

}
