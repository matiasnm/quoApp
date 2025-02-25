# NO SUBIR AL GIT:
**- CARPETA DE COMPILACION:**
/target

**- CARPETA OCULTA DE MAVEN:**
.mvn

**- CARPETA OCULTA DE IDE:**
.idea
.vscode

---

## COMANDOS cURL AL SERVER

### ENDPOINT /coins

```bash
$ CURL -X GET resulting-rattlesnake-matiasnm-db6f89e2.koyeb.app/coins
```

### ENDPOINT /login

```bash
curl -X POST "https://resulting-rattlesnake-matiasnm-db6f89e2.koyeb.app/login" -H "Content-Type: application/json" -d '{"username": "alice", "password": "password123"}'

{"jwTtoken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJxdW8iLCJzdWIiOiJhbGljZSIsImlkIjoxLCJleHAiOjE3NDA1MTc5NDJ9.UrRCDJRq_PaAIAEOjOJB-ssyHtt1QyKo3MnVJ10pad4"}
```

### ENDPOINT /users/1

```bash
curl -X GET "https://resulting-rattlesnake-matiasnm-db6f89e2.koyeb.app/users/1" -H "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJxdW8iLCJzdWIiOiJhbGljZSIsImlkIjoxLCJleHAiOjE3NDA1MTc5NDJ9.UrRCDJRq_PaAIAEOjOJB-ssyHtt1QyKo3MnVJ10pad4" -d '{"username": "alice", "password": "password123"}'

;14d0ed15-6d0b-4711-80ab-dbdf2a89e1af{"username":"alice","mail":"alice@example.com"}
```

## BASE DE DATOS (AIVEN.COM) 

- alice   → Contraseña: password123

- bob     → Contraseña: securePass!

- eve     → Contraseña: mySecret#

## ENDPOINT LOCALES

### Conseguir TOKEN

POST localhost:8080/login

body json:
{
    "username": "alice",
    "password": "password123"
}

### GET usuario

GET localhost:8080/users/1