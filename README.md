# eidetic-spring

Application to allow for the searching for books by a textual description of their book cover.

## Tech

### Database

Data is stored in a docker container running Mongo.
Docker Compose is used to specify details of the required Mongo database and to run the container.
All relevant files can be found in ./docker.

#### Running the Database
To spin up the Mongo database, ensure the Docker engine is running open a bash terminal in ./docker 
and run the following:

`docker-compose -f stack.yml up`

#### Database Configuration and Secrets
The following pieces of database configuration are sources from host machine environment vars:

| Eidetic Docker Compose Property | Host Environment Variable |
|:-------------|:-------------|
| MONGO_INITDB_ROOT_USERNAME | MONGO_EIDETIC_ROOT_USERNAME |
| MONGO_INITDB_ROOT_PASSWORD | MONGO_EIDETIC_ROOT_PASSWORD |
| MONGO_EIDETIC_DB_DATABASE | MONGO_EIDETIC_DB_NAME |
| MONGO_EIDETIC_DB_USERNAME | MONGO_EIDETIC_DB_USERNAME |
| MONGO_EIDETIC_DB_PASSWORD | MONGO_EIDETIC_DB_PASSWORD |