package pojo;

public class User {
    private String userName;
    private String password;
    private int openFileNumber;//打开文件数量
    private int saveFileNumber;

    public User() {
    }

    public User(String userName, String password, int openFileNumber,int saveFileNumber) {
        this.userName = userName;
        this.password = password;
        this.openFileNumber = openFileNumber;
        this.saveFileNumber = saveFileNumber;
    }

    public void setSaveFileNumber(int saveFileNumber) {
        this.saveFileNumber = saveFileNumber;
    }

    public int getSaveFileNumber() {
        return saveFileNumber;
    }

    public int getOpenFileNumber() {
        return openFileNumber;
    }

    public void setOpenFileNumber(int openFileNumber) {
        this.openFileNumber = openFileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
