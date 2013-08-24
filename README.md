[![Build Status](https://ligi.ci.cloudbees.com/job/tracedroid/badge/icon)](https://ligi.ci.cloudbees.com/job/tracedroid/)

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

license
-------

Tracedroid is licensed with the MIT license ( http://opensource.org/licenses/MIT ) - in short: do what you want with it and it would be nice if you mention the author/project somwhere when you use it. WITHOUT WARRANTY OF ANY KIND

[1]: http://code.google.com/p/android-remote-stacktrace/
