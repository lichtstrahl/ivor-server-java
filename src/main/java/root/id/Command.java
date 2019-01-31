package root.id;

public class Command {
    private long id;
    private String cmd;

    public Command(long id, String cmd) {
        this.id = id;
        this.cmd = cmd;
    }

    public long getId() {
        return id;
    }

    public String getCmd() {
        return cmd;
    }
}
