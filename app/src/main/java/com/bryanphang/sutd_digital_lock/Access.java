package com.bryanphang.sutd_digital_lock;

public class Access {
    public String id;
    public String findname;
    public String locknum;
    public String datetimefrom;
    public String datetimeto;
    public String accesspassword;


    public Access(String id, String findname, String locknum, String datetimefrom, String datetimeto, String accesspassword) {
        this.id = id;
        this.findname= findname;
        this.locknum = locknum;
        this.datetimefrom = datetimefrom;
        this.datetimeto = datetimeto;
        this.accesspassword = accesspassword;
    }
}
