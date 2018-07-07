package core;

import org.fusesource.jansi.Ansi;
import pojo.Bus;
import pojo.User;
import pojo.UserFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import static org.fusesource.jansi.Ansi.ansi;

public class FileSystemSimulator {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void openShell() {
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("login")) {
                auth();
            } else {
                System.out.println("未登陆前无法执行该操作");
            }
        }
    }

    public static void auth() {
        while (true) {
            System.out.println("请输入用户名");
            String userName = scanner.nextLine();
            System.out.println("请输入密码");
            String password = scanner.nextLine();
            Login(new User(userName, password, 0, 0));
        }
    }

    public static void Login(User user) {
        ArrayList<User> users = Bus.getUsers();
        ArrayList<UserFile> userFiles = Bus.getUserFiles();
        ArrayList<UserFile> openFiles = Bus.getOpenFiles();
        boolean isLoginSuccessfullt = false;
        for (User existUser: users) {
            if (existUser.getUserName().equals(user.getUserName()) && existUser.getPassword().equals(user.getPassword())) {
                System.out.println(user.getUserName() + "登陆成功");
                isLoginSuccessfullt = true;
                currentUser = existUser;
                Bus.setRootDirectory("root\\" + user.getUserName());
                showCLI();
                break;
            }
        }
        if (!isLoginSuccessfullt) {
            System.out.println("账号或密码错误");
        }
    }

    public static void showCLI() {//command line interface
        ArrayList<User> users = Bus.getUsers();
        ArrayList<UserFile> userFiles = Bus.getUserFiles();
        ArrayList<UserFile> openFiles = Bus.getOpenFiles();
        showHelp();
        while (true) {
            System.out.println("请输入你想要进行的操作");
            String operation = scanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(operation, " ");
            int size = stringTokenizer.countTokens();
            String coreOperation = stringTokenizer.nextToken();
            String fileName = null;
            String nextOperation = null;
            File file = null;
            if (size == 2 && !coreOperation.equalsIgnoreCase("cd")) {
                fileName = stringTokenizer.nextToken();
                file = new File(Bus.getRootDirectory(), fileName);
            } else if (size == 2 && coreOperation.equalsIgnoreCase("cd")) {
                nextOperation = stringTokenizer.nextToken();
            }
            if (coreOperation.equalsIgnoreCase("bye")) {
                continue;
            } else if (coreOperation.equalsIgnoreCase("ls") || coreOperation.equalsIgnoreCase("dir")) {
                try {
                    File directory = new File(new File("").getCanonicalPath() + File.separator + Bus.getRootDirectory());
                    File[] fileList = directory.listFiles();
                    for (int i = 0; i < fileList.length; i++) {
                        if (fileList[i].isFile()) {
                            //System.out.println("文件:" + fileList[i]);
                            System.out.println(ansi().eraseScreen().fg(Ansi.Color.RED).bg(Ansi.Color.DEFAULT).a("文件:" + fileList[i]));
                        } else if (fileList[i].isDirectory()) {
//                            System.out.println("文件夹:" + fileList[i]);
                            System.out.println(ansi().eraseScreen().fg(Ansi.Color.MAGENTA).bg(Ansi.Color.DEFAULT).a("文件夹:" + fileList[i]));
                        }
                    }
                    System.out.print(ansi().eraseScreen().fg(Ansi.Color.DEFAULT).a(""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (coreOperation.equalsIgnoreCase("pwd")) {
                try {
                    File directory = new File("");//参数为空
                    String filePath = directory.getCanonicalPath() + File.separator + Bus.getRootDirectory();
                    System.out.println(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (coreOperation.equalsIgnoreCase("create")) {//判断是否含有.决定创建文件还是文件夹
                if (file.exists()) {
                    System.out.println("文件已存在");
                    continue;
                }
                try {
                    if (fileName.contains(".")) {
                        boolean isCreateSuccessfully = file.createNewFile();
                        if (isCreateSuccessfully) {
                            PrintWriter printWriter = new PrintWriter(new FileWriter(new File("document/record.txt"), true));
                            printWriter.println(currentUser.getUserName() + " " + fileName + " " + 111 + " " + 0 + " " + false);
                            printWriter.flush();
                            printWriter.close();
                            userFiles.add(new UserFile(fileName, String.valueOf(111), String.valueOf(0), currentUser.getUserName(), false));
                            System.out.println("文件创建成功");
                        } else {
                            System.out.println("文件创建失败");
                        }
                    } else {
                        boolean isCreateSuccessfully = file.mkdirs();
                        if (isCreateSuccessfully) {
                            PrintWriter printWriter = new PrintWriter(new FileWriter(new File("document/record.txt"), true));
                            printWriter.println(currentUser.getUserName() + " " + fileName + " " + 111 + " " + 0 + " " + false);
                            printWriter.flush();
                            printWriter.close();
                            userFiles.add(new UserFile(fileName, String.valueOf(111), String.valueOf(0), currentUser.getUserName(), false));
                            System.out.println("文件夹创建成功");
                        } else {
                            System.out.println("文件夹创建失败");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (coreOperation.equalsIgnoreCase("delete")) {
                if (!file.exists()) {
                    System.out.println("文件不存在");
                    continue;
                }
                boolean isDeleteSuccessfully = file.delete();
                if (isDeleteSuccessfully) {
                    UserFile deleteFile = null;
                    for (UserFile userFile: userFiles) {
                        if (userFile.getFileName().equalsIgnoreCase(fileName) && userFile.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                            deleteFile = userFile;
                        }
                    }
                    userFiles.remove(deleteFile);

                    System.out.println("文件删除成功");
                    try {
                        PrintWriter printWriter = new PrintWriter(new FileWriter(new File("document/record.txt")));
                        for (UserFile userFile: userFiles) {
                            printWriter.println(userFile.getUserName() + " " + userFile.getFileName() + " " + 111 + " " + userFile.getFileLength() + " " + false);
                            printWriter.flush();
                        }
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("文件删除失败");
                }
            } else if (coreOperation.equalsIgnoreCase("open")) {//执行
                if (!file.exists()) {
                    System.out.println("文件不存在");
                    continue;
                }
                if (currentUser.getOpenFileNumber() == 5) {
                    System.out.println("用户已经打开了五个文件");
                    continue;
                }

                boolean isOpened = false;
                for (UserFile userFile: openFiles) {
                    if (userFile.getFileName().equalsIgnoreCase(fileName) && userFile.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                        System.out.println("文件已经打开过");
                        isOpened = true;
                        break;
                    }
                }
                if (isOpened) {
                    continue;
                }

                for (UserFile userFile: userFiles) {
                    if (userFile.getFileName().equalsIgnoreCase(fileName) && userFile.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                        String permission = userFile.getProtectionBits().substring(2);
                        if (permission.equalsIgnoreCase("0")) {
                            System.out.println("用户没有执行权限");
                            break;
                        } else if (permission.equalsIgnoreCase("1")) {
                            if (userFile.getIsOpen() == true) {
                                System.out.println("文件已经打开");
                                break;
                            } else {
                                userFile.setIsOpen(true);
                                openFiles.add(userFile);
                                currentUser.setOpenFileNumber(currentUser.getOpenFileNumber() + 1);
                                try {
                                    String fileType = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3);
                                    if (fileType.equals("exe") || fileType.equals("bat")) {
                                        Runtime.getRuntime().exec(file.getAbsolutePath());
                                        System.out.println("成功执行" + fileType + "文件");
                                    } else {
                                        System.out.println("暂时不支持该文件类型");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                }
            } else if (coreOperation.equalsIgnoreCase("close")) {//√
                if (!file.exists()) {
                    System.out.println("文件不存在");
                    continue;
                }
                for (UserFile userFile: userFiles) {
                    if (userFile.getFileName().equalsIgnoreCase(fileName) && userFile.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                        if (userFile.getIsOpen() == false) {
                            System.out.println("文件并未打开");
                            break;
                        } else {
                            openFiles.remove(userFile);
                            userFile.setIsOpen(false);
                            currentUser.setOpenFileNumber(currentUser.getOpenFileNumber() - 1);
                            System.out.println("成功关闭文件");
                            break;
                        }
                    }
                }
            } else if (coreOperation.equalsIgnoreCase("read") || coreOperation.equalsIgnoreCase("cat")) {//读  √
                if (!file.exists()) {
                    System.out.println("文件不存在");
                    continue;
                }
                if (currentUser.getOpenFileNumber() == 5) {
                    System.out.println("用户已经打开了五个文件");
                    continue;
                }
                boolean isPermitted = false;
                for (UserFile userFile: userFiles) {
                    if (userFile.getFileName().equalsIgnoreCase(fileName) && userFile.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                        String permission = userFile.getProtectionBits().substring(0, 1);
                        if (permission.equalsIgnoreCase("0")) {
                            System.out.println("用户没有读权限");
                            isPermitted = false;
                            break;
                        } else if (permission.equalsIgnoreCase("1")) {
                            if (userFile.getIsOpen() == true) {
                                System.out.println("文件已经打开");
                                isPermitted = false;
                                break;
                            }
                            userFile.setIsOpen(true);
                            openFiles.add(userFile);
                            currentUser.setOpenFileNumber(currentUser.getOpenFileNumber() + 1);
                            isPermitted = true;
                            break;
                        }
                    }
                }
                if (!isPermitted) {
                    continue;
                }
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
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
            } else if (coreOperation.equalsIgnoreCase("write")) {//写        √
                if (!file.exists()) {
                    System.out.println("文件不存在");
                    continue;
                }
                if (currentUser.getOpenFileNumber() == 5) {
                    System.out.println("用户已经打开了五个文件");
                    continue;
                }
                if (currentUser.getSaveFileNumber() == 10) {
                    System.out.println("用户已经保存了十个文件");
                    continue;
                }
                boolean isPermitted = false;
                for (UserFile userFile: userFiles) {
                    if (userFile.getFileName().equalsIgnoreCase(fileName) && userFile.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                        String permission = userFile.getProtectionBits().substring(1, 2);
                        if (permission.equalsIgnoreCase("0")) {
                            System.out.println("用户没有写权限");
                            isPermitted = false;
                            break;
                        } else if (permission.equalsIgnoreCase("1")) {
                            if (userFile.getIsOpen() == true) {
                                System.out.println("文件已经打开");
                                isPermitted = false;
                                break;
                            }
                            userFile.setIsOpen(true);
                            openFiles.add(userFile);
                            currentUser.setOpenFileNumber(currentUser.getOpenFileNumber() + 1);
                            currentUser.setSaveFileNumber(currentUser.getSaveFileNumber() + 1);
                            isPermitted = true;
                            break;
                        }
                    }
                }
                if (!isPermitted) {
                    continue;
                }
                System.out.println("输入A表示追加文件");
                String input = scanner.nextLine();
                boolean isAppend;
                if (input.equalsIgnoreCase("A")) {
                    isAppend = true;
                } else {
                    isAppend = false;
                }
                PrintWriter printWriter = null;
                try {
                    printWriter = new PrintWriter(new FileWriter(file, isAppend));
                    System.out.println("请输入你想要写入到文件中的内容");
                    String fileInput = scanner.nextLine();
                    printWriter.println(fileInput);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    printWriter.close();
                }
            } else if (coreOperation.equalsIgnoreCase("cd")) {
                if (nextOperation.equalsIgnoreCase("/")) {
                    File directory = new File("");//参数为空
                    Bus.setRootDirectory("root");
                    System.out.println("目录设置成功");
                } else if (nextOperation.equalsIgnoreCase("$Eugene") || nextOperation.equals("$me")) {//回到用户对应的自己的目录
                    File directory = new File("");//参数为空
                    Bus.setRootDirectory("root/" + currentUser.getUserName());
                    System.out.println("目录设置成功");
                } else if (nextOperation.equals(".")) {

                } else if (nextOperation.equals("..")) {
                    //System.out.println(Bus.getRootDirectory());
                    if (Bus.getRootDirectory().equals("root")) {
                        System.out.println("已经是在根目录，无法再回退了");
                        continue;
                    } else {
                        Bus.setRootDirectory(Bus.getRootDirectory().substring(0, Bus.getRootDirectory().lastIndexOf("\\")));
                    }
                } else {
                    if (nextOperation.startsWith("/")) {
                        Bus.setRootDirectory(nextOperation);
                    } else {
                        Bus.setRootDirectory(Bus.getRootDirectory() + File.separator + nextOperation);
                    }
                }
            } else if (coreOperation.equalsIgnoreCase("showFiles")) {
                try {
                    File directory = new File(new File("").getCanonicalPath() + File.separator + "root" + File.separator + currentUser.getUserName());
                    File[] fileList = directory.listFiles();
                    for (int i = 0; i < fileList.length; i++) {
                        if (fileList[i].isFile()) {
                            System.out.println("文件:" + fileList[i]);
                        } else if (fileList[i].isDirectory()) {
                            System.out.println("文件夹:" + fileList[i]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showHelp() {
        System.out.println("create/delete/open/lose/read/write/cat + filename来执行相关的操作，输入bye保存当前现状并退出");
        showAdvancedOperation();
    }

    public static void showAdvancedOperation() {
        System.out.println("ls/dir显示当前目录中的文件  pwd显示当前所在的目录  cd /进行所有用户全局目录  cd me进入用户自己的目录    showFiles显示自己的根目录的用户文件");
    }
}
