package data;

class User {
    private String email;
    private String password;
    private String pid;
    private boolean isActivated;
    
    public User(String email, String password, String pid) {
        this.email = email;
        this.password = password;
        this.pid = pid;
        this.isActivated = false;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getPid() {
        return pid;
    }
    
    public boolean isActivated() {
        return isActivated;
    }
    
    public void setActivated(boolean activated) {
        this.isActivated = activated;
    }
    
    @Override
    public String toString() {
        return "AccountData{email='" + email + "', isActivated=" + isActivated + "}";
    }
}