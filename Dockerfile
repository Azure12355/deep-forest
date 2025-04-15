# Dockerfile for DeepForest Spring Boot Backend (JDK 17)

# --- Base Image ---
# 使用 eclipse-temurin:17-jre-alpine 作为基础镜像，包含 Java 17 JRE 且体积小
#FROM eclipse-temurin:17-jre-alpine
FROM eclipse-temurin:17-jre-jammy

# --- Metadata ---
LABEL maintainer="Lytton Yang <azure12355@gmail.com>"
LABEL description="DeepForest Spring Boot Backend Application v1.0.0"

# --- Environment Variables ---
WORKDIR /app
# 设置时区为上海，解决时间相关问题
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# --- Application Code ---
# 定义 JAR 文件参数，默认值需要与你实际构建的 JAR 文件名一致！
ARG JAR_FILE=target/deep-forest-0.0.1-SNAPSHOT.jar

# 将构建好的 JAR 文件复制到容器中，并重命名为 app.jar
COPY ${JAR_FILE} app.jar

# --- Runtime Configuration ---
# 暴露 Spring Boot 应用监听的端口 (假设是 8101，根据你的 application.yml 调整)
EXPOSE 8101

# --- Entrypoint ---
# 使用 exec 格式启动 Java 应用
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 如果你需要传递特定的 Spring Profiles 或其他 JVM 参数，可以在这里添加
# 例如，强制使用 prod profile:
# ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
# 例如，设置 JVM 内存限制:
# ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "/app/app.jar"]