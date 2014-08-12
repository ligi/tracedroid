tracedroid
==========

why?
----

This work is inspired by [remote stacktrace for Android][1]. After looking at the sourcecode i decided to do a total
 rewrite with these things in mind:

 - main: Option to send the trace in other ways - e.g. share via email - pros:
  1. no need for internet permission
  2. having a email adress of the sender to contact the user after e.g. the bugfix
  3. no problem when there is no internet at the moment
  4. user is able to write some more info into the stacktrace mail
  5. no need for extra server backend
 - modularize ( diffrent send modules )
 - small core ( only collecting stacktraces to files - nothing else )
 - sending asyncronous to collecting - being able to send stacktraces when the user is more in the mood ( e.g. in wlan range )
 - attach Logging info

how?
----

 - add the tracedroid.jar in your classpath
 - in your code add:

```java
TraceDroid.init(this);
TraceDroidEmailSender.sendStackTraces("your@email.org", this);
```

if you want logs to be attached to the stacktrace you need to import the Log class from dtracedroid that way:

```java
import org.ligi.tracedroid.logging.Log;
```

and then use like you would use the android Log class

license
-------

This library is released under Apache 2 license. Feel free to use it. Pull requests are welcome.


    Copyright 2013 ligi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: http://code.google.com/p/android-remote-stacktrace/
