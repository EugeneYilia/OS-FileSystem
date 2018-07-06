package pojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Bus {
    private static ArrayList<User> users = new ArrayList<User>();

    static {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File("document/users.txt")));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                String userName = stringTokenizer.nextToken();
                String password = stringTokenizer.nextToken();
                users.add(new User(userName, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
