package org.ligi.tracedroid.test;

import org.junit.runners.model.InitializationError;

import org.robolectric.RobolectricTestRunner;

/**
 * Use this runner instead of RobolectricTestRunner with @RunWith annotation.
 * background on why we need this:
 *  http://stackoverflow.com/questions/16623233/robolectriccontext-missing-for-gradle-based-project
 */
public class Runner extends RobolectricTestRunner {


    public Runner(final Class<?> testClass) throws InitializationError {
        super(testClass);
    }

}
