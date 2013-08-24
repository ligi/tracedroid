package org.ligi.tracedroid.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ligi.tracedroid.logging.Log;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Runner.class)
public class TheLog {

    @Test
    public void should_forget() {
	    Log.setLogCacheSize(1);
        Log.i("forget me");
        Log.i("foo");

        assertThat(Log.getCachedLog()).doesNotContain("forget");
    }

    @Test
    public void should_rotate_proper() {
        Log.setLogCacheSize(2);
        Log.i("forget me");
        Log.i("foo");
        Log.i("bar");

        assertThat(Log.getCachedLog()).matches("(?s).*bar.*foo.*");
    }

}
