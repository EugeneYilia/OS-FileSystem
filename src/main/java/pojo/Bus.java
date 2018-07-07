package pojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Bus {
    private static ArrayList<User> users = new ArrayList<User>();//存储所有的用户
    private static ArrayList<UserFile> userFiles = new ArrayList<UserFile>();//UFD存储所有的用户文件
    private static ArrayList<UserFile> openFiles = new ArrayList<UserFile>();//AFD存储打开的所有的用户文件
    private static String rootDirectory = "root";//全局用户目录

    static {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File("document/users.txt")));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                String userName = stringTokenizer.nextToken();
                String password = stringTokenizer.nextToken();
                users.add(new User(userName, password, 0, 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File("document/record.txt")));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                String userName = stringTokenizer.nextToken();
                String fileName = stringTokenizer.nextToken();
                String protectionBits = stringTokenizer.nextToken();
                String fileLength = stringTokenizer.nextToken();
                boolean isOpen = Boolean.parseBoolean(stringTokenizer.nextToken());
                userFiles.add(new UserFile(fileName, protectionBits, fileLength, userName, isOpen));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<UserFile> getOpenFiles() {
        return openFiles;
    }

    public static ArrayList<UserFile> getUserFiles() {
        return userFiles;
    }

    public static String getRootDirectory() {
        return rootDirectory;
    }

    public static void setRootDirectory(String rootDirectory) {
        Bus.rootDirectory = rootDirectory;
    }
}
