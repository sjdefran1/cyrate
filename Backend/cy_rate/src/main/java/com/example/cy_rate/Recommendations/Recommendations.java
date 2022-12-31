package com.example.cy_rate.Recommendations;
import com.example.cy_rate.Business.Business;
import com.example.cy_rate.User.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;

@Entity(name = "Recommendations")
public class Recommendations {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "recid")
private int recid;

@ManyToOne
@JoinColumn(name = "uid", referencedColumnName = "userId")
private User user;

@ManyToOne
@JoinColumn(name = "bid", referencedColumnName = "busId")
private Business business;

public Recommendations(){

}

public int getRecId()
{
    return recid;
}

public void setRecId(int recid)
{
    this.recid = recid;
}

public Business getBusiness()
{
    return business;
}

public void setBusiness(Business bus)
{
    this.business = bus;
}

public User getUser()
{
    return user;
}

public void setUser(User u)
{
    this.user = u;
}

public String toString()
{
    return business.getBusName();
}
}
