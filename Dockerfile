FROM amd64/amazoncorretto:17

# 작업 디렉터리 설정
WORKDIR /app

# 컨테이너 한글 설정
RUN apt-get update && apt-get install -y locales
RUN locale-gen ko_KR.UTF-8
ENV LC_ALL ko_KR.UTF-8

# 애플리케이션 JAR 파일 복사
COPY ./build/libs/lequuServer-0.0.1-SNAPSHOT.jar /app/lequuServer.jar

# AWS CLI 설치를 위한 필수 패키지 설치
RUN yum install -y unzip groff less

# AWS CLI 다운로드 및 설치
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
    unzip awscliv2.zip && \
    ./aws/install

# Build 시점에 설정할 환경 변수
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_DEFAULT_REGION=ap-northeast-2
ARG AWS_OUTPUT_FORMAT=None

# 환경 변수 설정
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV AWS_DEFAULT_REGION=${AWS_DEFAULT_REGION}
ENV AWS_OUTPUT_FORMAT=${AWS_OUTPUT_FORMAT}

# 컨테이너 실행 시 Java 애플리케이션 실행
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "lequuServer.jar"]
