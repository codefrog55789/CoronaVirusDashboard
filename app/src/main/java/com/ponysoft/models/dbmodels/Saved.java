package com.ponysoft.models.dbmodels;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="saved")
public class Saved {
    @Column(name="id", isId = true, autoGen = true)
    private int id;
    @Column(name = "iid")
    private int iid;

    public Saved() {}

    public Saved(int iid) {
        this.iid = iid;
    }

    public int getIid() {
        return this.iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }
}
