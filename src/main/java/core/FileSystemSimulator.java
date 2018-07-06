package core;

import pojo.Bus;
import pojo.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileSystemSimulator {
    private static Scanner scanner = new Scanner(System.in);

    public static void openShell() {
        while (true) {
            System.out.println("请输入用户名");
            String userName = scanner.nextLine();
            System.out.println("请输入密码");
            String password = scanner.nextLine();
            Login(new User(userName, password));
        }
    }

    public static void Login(User user) {
        ArrayList<User> users = Bus.getUsers();
        for (User existUser: users) {
            if (existUser.getUserName().equals(user.getUserName()) && existUser.getPassword().equals(user.getPassword())) {
                System.out.println(user.getUserName() + "登陆成功");
                showCLI();
                break;
            }
        }
    }

    public static void showCLI() {//command line interface
        showHelp();
        while (true) {
            System.out.println("请输入你想要进行的操作");
            String operation = scanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(operation," ");

        }
    }

    public static void showHelp(){
        System.out.println("create/delete/open/lose/read/write + filename来执行相关的操作，输入bye保存当前现状并退出");
        showAdvancedOperation();
    }

    public static void showAdvancedOperation(){
        System.out.println("ls 显示当前目录");
    }
}
