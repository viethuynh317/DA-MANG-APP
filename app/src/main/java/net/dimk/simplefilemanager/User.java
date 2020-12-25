package net.dimk.simplefilemanager;

public class User {
    private String userName;
    private String password;
    private int Id;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public User() {
        this.userName = "";
        this.password = "";
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return userName + "\t\t\t\t\t" + password;
    }
}
