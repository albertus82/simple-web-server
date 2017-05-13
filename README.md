# Simple Web Server

## Minimum requirements
* [Java](https://www.java.com) 8 (JRE 1.8)

## Getting started
```Shell
git clone https://github.com/Albertus82/SimpleWebServer.git
cd SimpleWebServer
mvn clean package
cd target
```

* **Linux**
  ```Shell
  chmod 755 simple-web-server.sh
  ./simple-web-server.sh 8080 ~/webserver/
  ```

* **Windows**
  ```Batchfile
  SimpleWebServer 8080 c:/webserver/
  ```
