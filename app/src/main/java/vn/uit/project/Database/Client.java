package vn.uit.project.Database;

public class Client {
    String name;
    String username;
    String password;
    int age;

    public Client(String username, String password,String name, int age) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return age;
    }
}
