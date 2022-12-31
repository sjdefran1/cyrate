package com.example.cy_rate.Business;

// JPA stuff
import javax.persistence.*;

//@Hidden
import io.swagger.v3.oas.annotations.Hidden;

@Entity
public class Business {
    
    //----Business information-----//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private int busId;
    private String busName;
    private String busType;
    private String phone;
    private String photoUrl;
    private String hours;
    private String location;
    private int ownerId;
    private String menuLink;
    private String priceGauge;

    private String isCoffee;

    private String isBar;

    private String isRestaurant;
    
    //-------review's stuff--------//

    private int reviewSum;
    private int reviewCount;
    
    // @OneToMany(mappedBy = "business")   
    // private List<Review> reviews;
    
    
    public Business()
    {
        /* 
        this.busType = "";
        this.busName = "";
        this.photoUrl = "";
        this.hours = "";
        this.location = "";
        this.ownerId = 0;
        this.menuLink = "";
        this.priceGauge = "";
        this.reviewCount = 0;
        this.reviewSum = 0;
        */
    }

    public Business(String busType, String busName, String phone, String photoUrl,
                    String hours, String location, int ownerId, String menuLink,
                    String priceGauge, int reviewCount, int reviewSum,
                    String isCoffee, String isBar, String isRestaurant){
        this.busType = busType;
        this.busName = busName;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.hours = hours;
        this.location = location;
        this.ownerId = ownerId;
        this.menuLink = menuLink;
        this.priceGauge = priceGauge;
        this.reviewCount = reviewCount;
        this.reviewSum = reviewSum;
        this.isCoffee = isCoffee;
        this.isBar = isBar;
        this.isRestaurant = isRestaurant;
    }

    //---------- Getter Setter's ----------// 
    public String getBusName()
    {
        return busName;
    }
    
    public void setBusName(String bname)
    {
        this.busName = bname;
    }
    
    public int getBusId(){
        return busId;
    }

    public void setBusId(int busId){
        this.busId = busId;
    }

    public String getBusType(){
        return busType;
    }

    public void setBusType(String type){
        this.busType = type;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }

    public void setPhotoUrl(String url)
    {
        this.photoUrl = url;
    }

    public String getHours()
    {
        return hours;
    }

    public void setHours(String hours)
    {
        this.hours = hours;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(int id)
    {
        this.ownerId = id;
    }

    public String getMenuLink()
    {
        return menuLink;
    }

    public void setMenuLink(String link)
    {
        this.menuLink = link;
    }

    public String getPriceGauge()
    {
        return priceGauge;
    }

    public void setPriceGauge(String est)
    {
        this.priceGauge = est;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getIsCoffee() {
        return isCoffee;
    }

    public void setIsCoffee(String isCoffee) {
        this.isCoffee = isCoffee;
    }

    public String getIsBar() {
        return isBar;
    }

    public void setIsBar(String isBar) {
        this.isBar = isBar;
    }

    public String getIsRestaurant() {
        return isRestaurant;
    }

    public void setIsRestaurant(String isRestaurant) {
        this.isRestaurant = isRestaurant;
    }

    // Review Class Getter and setters for one to many relation
    // public void addReview(Review review)
    // {
    //     this.reviews.add(review);
    // }   

    // public List<Review> getReviews()
    // {
    //     return reviews;
    // }

    // public void setReviews(List<Review> givenReviews)
    // {
    //     reviews = givenReviews;
    // }

    // Review utility
    public int getReviewCount()
    {
        return reviewCount;
    }

    public void setReviewCount(int count)
    {
        this.reviewCount = count;
    }

    public int getReviewSum()
    {
        return reviewSum;
    }

    public void setReviewSum(int sum)
    {
        this.reviewSum = sum;
    }

    public int reviewAVG()
    {
        //avoid dividing by 0
        if(reviewCount == 0)
            return 0;
        return reviewSum / reviewCount;
    }

    //---------- Utility --------------//


    @Override
    public String toString()
    {
        return busId + "\n" + busName + "\n" + busType 
        + "\n" + hours + "\n" + location + "\n" + priceGauge;
    }
}
