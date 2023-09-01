package bridge;

public class CustomIRBridge {
    static {
        System.loadLibrary("NodeBridge");
    }

    public static native void processBinaryTree(Object[] objects);

}
