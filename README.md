# sbt-simple-multiProject


Simple sbt multi-project to demonstrate how to create and pack with [sbt-pack plugin](https://github.com/xerial/sbt-pack).

```
sbt pack
```

For code formatting [sbt-scalariform](https://github.com/sbt/sbt-scalariform) is used.

For code quality run:

```
sbt scalastyle
sbt scapegoat
```

For presentation layer [playframework](https://www.playframework.com/) is used.

Run project by:
```
sbt ~view/run
```

Alternative package project with playframework dist command:
```
sbt dist
```


How to test json services:

```curl --data '{"user":"anjo","comment":"HELLO WORLD"}' http://localhost:9000/  --header "Content-Type:application/json"```

```curl http://localhost:9000/anjo```
