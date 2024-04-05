#!/bin/sh

echo "********************************************************"
echo "Starting CMS Service                           "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -Dspring.datasource.url=$DATASOURCE_URL                            \
      -Dspring.datasource.username=$DATASOURCE_USERNAME                  \
      -Dspring.datasource.password=$DATASOURCE_PASSWORD                  \
     -Dspring.profiles.active=$PROFILE                                   \
     -jar /usr/local/app/@project.build.finalName@.jar