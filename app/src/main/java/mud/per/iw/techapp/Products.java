package mud.per.iw.techapp;



public class Products {
    private String puid;
    private  String pdesc;
    private  String psuid;
    private  String unitid;
    private  String unit;
    public Products() {
    }

    public Products(String puid, String pdesc,String psuid,String unitid,String unit) {
        this.puid = puid;
        this.pdesc = pdesc;
        this.psuid=psuid;
        this.unitid=unitid;
        this.unit=unit;
    }

    public String getpuid() {
        return puid;
    }
    public String getpdesc() {
        return pdesc;
    }
    public String getunit() {
        return unit;
    }
    public String getunitid() {
        return unitid;
    }
    public String getpsuid() {
        return psuid;
    }
    public void setpuid(String puid) {
        this.puid = puid;
    }
    public void setpdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void setpsuid(String psuid) {
        this.psuid = psuid;
    }
}