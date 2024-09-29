db = db.getSiblingDB('integrationtest-db'); // Cambia 'mydatabase' al nombre de tu base de datos

db.createUser({
    user: "admin",
    pwd: "password",
    roles: [{ role: "readWrite", db: "integrationtest-db" }]
});

db.auth_users.insertMany([
    { email: "test@mail.com", password: "password1" }
]);


db.users.insertMany([
    { email: "test@mail.com", password: "password1" }
]);