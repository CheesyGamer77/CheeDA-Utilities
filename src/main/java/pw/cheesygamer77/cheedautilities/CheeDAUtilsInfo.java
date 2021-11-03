package pw.cheesygamer77.cheedautilities;

public class CheeDAUtilsInfo {
    public static final int MAJOR = 0;
    public static final int MINOR = 1;
    public static final int SERIAL = 0;
    public static final String VERSION = String.format("%s.%s.%s", MAJOR, MINOR, SERIAL);

    public static void main(String[] args) {
        System.out.println(VERSION);
    }
}
