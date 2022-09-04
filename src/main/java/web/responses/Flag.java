package web.responses;

import com.google.gson.Gson;

public final class Flag {

    private Flag(Boolean flag) {
        this.flag = flag;
    }

    private final Boolean flag;

    public static final String TRUE = new Gson().toJson(new Flag(true));

    public static final String FALSE = new Gson().toJson(new Flag(true));

    @Deprecated
    public static String getFlagTrue() {
        return new Gson().toJson(new Flag(true));
    }

    @Deprecated
    public static String getFlagFalse() {
        return new Gson().toJson(new Flag(false));
    }
}
