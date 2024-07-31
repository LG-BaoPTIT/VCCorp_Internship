FROM openjdk:8
EXPOSE 9009
ADD target/auth-service.jar auth-service.jar
ENTRYPOINT ["java","-jar","/auth-service.jar"]

## Sử dụng một base image với JDK 8 và Maven đã cài đặt sẵn
#FROM maven:3.8.1-jdk-8
#
## Thêm thư mục của ứng dụng vào container
#ADD . /app
#
## Thiết lập thư mục làm việc
#WORKDIR /app
#
## Build ứng dụng bằng Maven
#RUN mvn clean install
#
## Expose cổng 9009
#EXPOSE 9009
#
## Chạy ứng dụng khi container khởi động
#ENTRYPOINT ["java", "-jar", "target/auth-service.jar"]
