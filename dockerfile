FROM tomcat:10.0
RUN rm -rf /usr/local/tomcat/webapps/*
COPY build/libs/application.war /usr/local/tomcat/webapps/
EXPOSE 8080