package com.example.inforpet;

public class Company {

    private String opnSvcId;    //개발서비스ID  ex) "02_03_06_P" (primary key)
    private int opnSfTeamCode;  //개밤자치단체코드  (primary key)
    private String mgtNo;          //관리번호 (primary key)
    private String trdStateGbn; //영업상태구분코드  ex) 01 = 영업/정상, 02, 03 = 폐업, 04 = 말소/중지
    private String siteTel;     //소재지 전화
    private String siteWhlAddr; //소재지 전체주소
    private String rdnWhlAddr;  //도로명 주소
    private String bplcNm;      //사업장명
    private double x;            //x
    private double y;            //y

    public Company(String opnSvcId, int opnSfTeamCode, String mgtNo, String trdStateGbn, String siteTel, String siteWhlAddr, String rdnWhlAddr, String bplcNm, double x, double y) {
        this.opnSvcId = opnSvcId;
        this.opnSfTeamCode = opnSfTeamCode;
        this.mgtNo = mgtNo;
        this.trdStateGbn = trdStateGbn;
        this.siteTel = siteTel;
        this.siteWhlAddr = siteWhlAddr;
        this.rdnWhlAddr = rdnWhlAddr;
        this.bplcNm = bplcNm;
        this.x = x;
        this.y = y;
    }

    public String getOpnSvcId() {
        return opnSvcId;
    }

    public void setOpnSvcId(String opnSvcId) {
        this.opnSvcId = opnSvcId;
    }

    public int getOpnSfTeamCode() {
        return opnSfTeamCode;
    }

    public void setOpnSfTeamCode(int opnSfTeamCode) {
        this.opnSfTeamCode = opnSfTeamCode;
    }

    public String getMgtNo() {
        return mgtNo;
    }

    public void setMgtNo(String mgtNo) {
        this.mgtNo = mgtNo;
    }

    public String getTrdStateGbn() {
        return trdStateGbn;
    }

    public void setTrdStateGbn(String trdStateGbn) {
        this.trdStateGbn = trdStateGbn;
    }

    public String getSiteTel() {
        return siteTel;
    }

    public void setSiteTel(String siteTel) {
        this.siteTel = siteTel;
    }

    public String getSiteWhlAddr() {
        return siteWhlAddr;
    }

    public void setSiteWhlAddr(String siteWhlAddr) {
        this.siteWhlAddr = siteWhlAddr;
    }

    public String getRdnWhlAddr() {
        return rdnWhlAddr;
    }

    public void setRdnWhlAddr(String rdnWhlAddr) {
        this.rdnWhlAddr = rdnWhlAddr;
    }

    public String getBplcNm() {
        return bplcNm;
    }

    public void setBplcNm(String bplcNm) {
        this.bplcNm = bplcNm;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
