package com.example.native_new.android.androidbasejava.crashreport;


import timber.log.Timber;

/**
 * Not a real crash reporting library!
 */
public final class FakeCrashLibrary {
    public static void log(int priority, String tag, String message) {
        // add log entry to circular buffer.
        Timber.i("priority:%s - tag:%s - message:%s", priority, tag, message);
    }

    public static void logWarning(Throwable t) {
        // report non-fatal warning.
        t.printStackTrace();
    }

    public static void logError(Throwable t) {
        t.printStackTrace();
        // report non-fatal error.
    }

    private FakeCrashLibrary() {
        throw new AssertionError("No instances.");
    }
}