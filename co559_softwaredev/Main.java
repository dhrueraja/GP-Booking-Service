package MainPackage;

public class Main {

    public static void main(String[] args) throws Exception{
        Registration c  = new Registration();
        connect dao = new connect();
        dao.setUpDatabase();
        Login login = new Login();
    }
}