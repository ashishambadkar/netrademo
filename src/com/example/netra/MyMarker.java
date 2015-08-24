package com.example.netra;


public class MyMarker
{
    private String mLabel;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;
    private String mRemark;
    

    public MyMarker(String label, String icon, Double latitude, Double longitude,String remark )
    {
        this.mLabel = label;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
        this.mRemark = "dsnvfsd";//remark;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public String getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(String micon)
    {
        this.mIcon = micon;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
    
    public String getRemark()
    {
        return mRemark;
    		
    }

    public void setRemark(String remark)
    {
        this.mRemark = remark;
    }
    
    public boolean UpdateMarkerData(String label, String icon, Double latitude, Double longitude,String remark )
    {
    	this.mLabel = label;
    	this.mIcon = icon;
    	this.mLatitude = latitude;
    	this.mLongitude = longitude;
    	this.mRemark = remark;
		return true;
    	
    }

}