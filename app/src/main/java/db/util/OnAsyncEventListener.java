package db.util;

public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
