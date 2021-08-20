set -e

mongo <<EOF
use $MONGO_EIDETIC_DB_DATABASE

db.createUser({
  user: '$MONGO_EIDETIC_DB_USERNAME',
  pwd:  '$MONGO_EIDETIC_DB_PASSWORD',
  roles: [{
    role: 'readWrite',
    db: '$MONGO_EIDETIC_DB_DATABASE'
  }]
})

db.createCollection('Books')

EOF