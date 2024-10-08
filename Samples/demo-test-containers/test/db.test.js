// Ejemplo de pruebas de integración con PostgreSQL usando TestContainers
const {Client} = require("pg");
const {PostgreSqlContainer} = require("@testcontainers/postgresql");

describe("PostgreSQL Test using TestContainers", () => {
    let container;
    let client;

    // Antes de las pruebas levantamos el contenedor
    beforeAll(async () => {
        container = await new PostgreSqlContainer()
            .withDatabase("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")
            .start();

        // Crear el cliente para interactuar con la base de datos
        client = new Client({
            host: container.getHost(),
            port: container.getPort(),
            user: container.getUsername(),
            password: container.getPassword(),
            database: container.getDatabase(),
        });

        await client.connect();
        console.log("Connected to PostgreSQL");
    });

    // Después de las pruebas cerramos el cliente y detenemos el contenedor
    afterAll(async () => {
        console.log("Closing PostgreSQL connection");
        if (client) {
            await client.end(); // Cerrar la conexión
        }
        if (container) {
            await container.stop(); // Detener el contenedor
        }
    });

    // Ejemplo de prueba para verificar que podemos realizar una consulta
    it("should insert and retrieve a record", async () => {
        // Crear una tabla
        console.log("Creating table users");
        await client.query(`
            CREATE TABLE users
            (
                id   SERIAL PRIMARY KEY,
                name VARCHAR(100)
            );
        `);

        // Insertar un registro
        console.log("Inserting a record");
        await client.query("INSERT INTO users (name) VALUES ($1)", ["John Doe"]);

        // Consultar el registro
        console.log("Querying the record");
        const res = await client.query("SELECT * FROM users WHERE name = $1", ["John Doe"]);

        // Verificar que el registro fue insertado correctamente
        console.log("Asserting the record");
        console.log(res.rows);
        expect(res.rows.length).toBe(1);
        expect(res.rows[0].name).toBe("John Doe");
    });
});
