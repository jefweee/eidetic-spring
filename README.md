# eidetic-spring

Application to allow for the searching for books by a textual description of their book cover.

## Tech

### Database

Data is stored in a docker container running Mongo.

To obtain database container (if not already downloaded):
docker pull mongo

To start database container:
docker run -d -p 27017:27017 --name mongodb mongo