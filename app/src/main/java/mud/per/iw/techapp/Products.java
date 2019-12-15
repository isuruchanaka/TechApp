package mud.per.iw.techapp;



public class Products {
    private String puid;
    private  String pdesc;
    private  String psuid;
    public Products() {
    }

    public Products(String puid, String pdesc,String psuid) {
        this.puid = puid;
        this.pdesc = pdesc;
        this.psuid=psuid

    }

    public String getpuid() {
        return puid;
    }
    public String getpdesc() {
        return pdesc;
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