package edu.pwr.db.model;

import java.util.List;

public class ClientItem extends Item{
    private int id;
    private String name;
    private String surname;
    private String address;

    public void setId(int id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public void setAddress(String address){
        this.address=address;
    }

    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getAddress(){
        return address;
    }
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + address;
    }
}
