Simple Web Server
=================

[![Build status](https://github.com/albertus82/simple-web-server/workflows/build/badge.svg)](https://github.com/albertus82/simple-web-server/actions)
[![Build status](https://ci.appveyor.com/api/projects/status/github/albertus82/simple-web-server?branch=master&svg=true)](https://ci.appveyor.com/project/albertus82/simple-web-server)

## Minimum requirements

* Java SE Development Kit 8
* [Apache Maven](https://maven.apache.org) 3.3.9

## Build

`mvn clean verify`

## Getting started

```Shell
git clone https://github.com/albertus82/simple-web-server
cd simple-web-server
mvn clean verify
cd target
```

* **Linux**
  ```Shell
  ./simple-web-server.sh 8080 ~/webserver/
  ```

* **Windows**
  ```Batchfile
  SimpleWebServer 8080 c:/webserver/
  ```
