@echo off

echo =========================
echo DOCKER COMPOSE DOWN
echo =========================
docker compose down

echo .
echo =========================
echo BUILDING MAVEN PROJECT
echo =========================
call .\mvnw clean package -DskipTests

echo.
echo =========================
echo REMOVING OLD IMAGE
echo =========================
docker rmi spring-app

echo.
echo =========================
echo BUILDING DOCKER IMAGE
echo =========================
docker build -t spring-app .

echo.
echo =========================
echo STARTING DOCKER COMPOSE
echo =========================
docker compose up -d --build