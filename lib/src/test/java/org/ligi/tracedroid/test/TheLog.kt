package org.ligi.tracedroid.test

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.ligi.tracedroid.logging.BufferedLogTree

class TheLog {
    @Test
    fun should_forget() {
        val tested = BufferedLogTree(1)
        tested.i("forget me")
        tested.i("foo")
        assertThat(tested.buffer).doesNotContain("forget")
    }

    @Test
    fun should_rotate_proper() {
        val tested = BufferedLogTree(2)
        tested.i("forget me")
        tested.i("foo")
        tested.i("bar")
        assertThat(tested.buffer.first).matches(".*foo")
        assertThat(tested.buffer.last).matches(".*bar")
    }

    @Test
    fun should_limit_size() {
        val tested = BufferedLogTree(2)
        tested.i("fyolo")
        tested.i("foo")
        tested.i("bar")
        assertThat(tested.buffer.size).isEqualTo(2)
    }
}