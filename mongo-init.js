db = db.getSiblingDB('integrationtest-db'); // Cambia 'mydatabase' al nombre de tu base de datos

db.createUser({
    user: "admin",
    pwd: "password",
    roles: [{ role: "readWrite", db: "integrationtest-db" }]
});

db.mycollection.insertMany([
    { name: "item1", value: 100 },
    { name: "item2", value: 200 },
    { name: "item3", value: 300 }
]);