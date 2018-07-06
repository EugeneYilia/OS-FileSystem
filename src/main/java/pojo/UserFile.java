package pojo;

public class UserFile {
    private String userName;
    private String fileName;
    private String protectionBits;
    private String fileLength;
    private boolean isOpen;

    public UserFile() {
    }

    public UserFile(String fileName, String protectionBits, String fileLength, String userName, boolean isOpen) {
        this.fileName = fileName;
        this.protectionBits = protectionBits;
        this.fileLength = fileLength;
        this.userName = userName;
        this.isOpen = isOpen;
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getFileName() {
        return fileName;
    }

    public String getProtectionBits() {
        return protectionBits;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setProtectionBits(String protectionBits) {
        this.protectionBits = protectionBits;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
