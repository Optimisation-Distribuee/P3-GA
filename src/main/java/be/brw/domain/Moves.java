package be.brw.domain;

public enum Moves {
    RIGHT((byte)'R'),
    LEFT((byte)'L'),
    JUMP_RIGHT((byte)'+'),
    JUMP_LEFT((byte)'-'),
    FREEZE((byte)'/');

    private final byte text;

    Moves(final byte text) {
        this.text = text;
    }

    public static byte toByte(Moves m) {
        return switch (m) {
            case JUMP_LEFT, RIGHT, LEFT, JUMP_RIGHT, FREEZE -> m.text;
        };
    }

    public static Moves fromByte(byte c) {
        return switch (c) {
            case 'R' -> RIGHT;
            case 'L' -> LEFT;
            case '+' -> JUMP_RIGHT;
            case '-' -> JUMP_LEFT;
            default -> FREEZE;
        };
    }
}