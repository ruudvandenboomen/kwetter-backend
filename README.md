# Build
docker build -t mypayara .

cd db/
docker build -t mysql .

# RUN

docker-compose up