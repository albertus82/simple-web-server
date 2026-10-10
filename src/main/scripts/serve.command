java -Xms${java.xms}m -Xmx${java.xmx}m -XX:MaxMetaspaceSize=${java.MaxMetaspaceSize}m -XX:ReservedCodeCacheSize=${java.ReservedCodeCacheSize}m -jar "`dirname $0`/${artifactId}.jar" $1 $2 
