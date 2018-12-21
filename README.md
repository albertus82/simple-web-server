# Simple Web Server

[![Build Status](https://travis-ci.org/albertus82/simple-web-server.svg?branch=master)](https://travis-ci.org/albertus82/simple-web-server)
[![Build status](https://ci.appveyor.com/api/projects/status/github/albertus82/simple-web-server?branch=master&svg=true)](https://ci.appveyor.com/project/albertus82/simple-web-server)

## Minimum requirements

* [Java](https://www.java.com) 8 (JRE 1.8)

## Getting started

```Shell
git clone https://github.com/albertus82/simple-web-server
cd simple-web-server
mvn clean package
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
