
HAY 3 USUARIOS EN LA BD 

alice   → Contraseña: password123
bob     → Contraseña: securePass!
eve     → Contraseña: mySecret#

1) Conseguir TOKEN

POST localhost:8080/login
body json:
{
    "username": "alice",
    "password": "password123"
}

2) Leer usuario de la BD

GET localhost:8080/users/1