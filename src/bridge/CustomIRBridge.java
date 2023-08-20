package bridge;

import codegen.irnodes.IrNode;

public class CustomIRBridge {
    static {
        System.loadLibrary("JavaBridge");
    }

    public static native void sendIRNode(byte[] serializedTree);

}
