# Description

This should take cron expression like `0 */6 0 * * /usr/bin/find`
and output a table like the one below.
```
minute        0
hour          0 6 12 18
day of month  1
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   0 1 2 3 4 5 6
command       /usr/bin/find
```

It supports * , - and / expressions. It's currently not supporting specials (like @yearly, @daily, etc) *and ? in 
expressions* (this is since it is only used in day of week / day of month, and I was getting out of scope/time)

# Build and Run Instructions

Project is using gradle and kotlin, you will need to have JDK11 installed for this.

Run `./gradlew clean build` to run the tests and build artifacts.

You can run the app itself in a few ways. You can open it in intelliJ and run Application.class 
(might need to add arguments to run configuration though)

Easier way would be to run it through gradle:
`./gradlew run --args="'0 0 * * * /usr/bin/find'"`

You can also build and run jar file like this:
```
./gradlew clean build
java -jar build/libs/cronparser-1.0-SNAPSHOT-all.jar '0 0 * * * /usr/bin/find'
```