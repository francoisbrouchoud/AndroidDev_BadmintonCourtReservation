package ch.brouchoud.androiddev_badmintoncourtreservation.util;

/**
 * Listener of the async event tasks. Used as callback on operation involving the DB.
 */
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
