package terminalUI;

public final class TerminalUtils {

	private TerminalUtils(){}
	
	public static final int WIDTH = 62;
	
	public static void separator() {
		System.out.println("-".repeat(WIDTH));
	}
	
	public static void blank() {
		System.out.println();
	}
	
}
