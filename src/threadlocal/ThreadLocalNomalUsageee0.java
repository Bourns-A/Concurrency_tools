package threadlocal;

//避免重复传参
public class ThreadLocalNomalUsageee0 {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User user = new User("superman");
        UserContextHolder.holder.set(user);
        new Service2().process();

    }
}

class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("service2"+ user.name);
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("service3"+ user.name);
    }
}

class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}

class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}