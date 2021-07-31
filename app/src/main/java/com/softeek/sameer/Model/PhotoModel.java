package com.softeek.sameer.Model;

public class PhotoModel {

    String id,owner,secret,server,title;
    int farm,ispublic,isfriend,isfamily;

    public PhotoModel(String id,String owner,String secret,String server,int farm,String title,
                      int ispublic,int isfriend,int isfamily) {

        this.id=id;
        this.owner=owner;
        this.secret=secret;
        this.server=server;
        this.farm=farm;
        this.title=title;
        this.ispublic=ispublic;
        this.isfriend=isfriend;
        this.isfamily=isfamily;

    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public String getTitle() {
        return title;
    }

    public int getFarm() {
        return farm;
    }

    public int getIspublic() {
        return ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }
}
