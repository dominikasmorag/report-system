@echo off

echo =========================
echo DOCKER COMPOSE DOWN
echo =========================
docker compose down

echo .
echo =========================
echo DOCKER PRUNE
echo =========================
docker image prune -a

echo .
echo =========================
echo BUILDING MAVEN PROJECT
echo =========================
call .\mvnw clean package

echo.
echo =========================
echo BUILDING DOCKER APP IMAGE
echo =========================
docker build -t backend-image .

echo.
echo =========================
echo BUILDING DOCKER NGINX IMAGE
echo =========================
cd nginx
docker build -t nginx-image .

echo.
echo =========================
echo STARTING DOCKER COMPOSE
echo =========================
cd ..
docker compose up -d --build