# XYZ_Hotel
**API** of a reservation system for a hotel.
# Setup the project
## Open the project
Open the project in your **IDE**.
## Docker
Install **Docker** if not already installed and **run** in a terminal :

    cd src/main/resources/
    docker compose up
**Check** if the containers are **running**.
## Run the project
Run the **XyzHotelApplication.java**.
## Add a data set
**Run** the **HTTP Requests** in the [setData.http](https://github.com/Scroude/xyz_hotel/blob/master/src/main/resources/http.http).
# Use the API Endpoints 

Go to the [SwaggerUI](http://localhost:8081/swagger-ui/index.html) to see all the endpoints.
Or go to [Front](http://localhost:8081/index.html) 
## List of all the endpoints
| Endpoints | Description  | Type |
|--|--|--|
| /api/currency/all | Get all the currencies  | GET(List(Currency)) |
| /api/currency/add | Add a **list** of currencies | POST(List(Currency)) |
| /api/wallet/{userId} | Get the wallet info of a user by the user ID | GET(userId) |
| /api/user/register | Register a new user and a new wallet | POST(User) |
| /api/user/login | Connect a user | GET(User) |
| /api/user/{userId} | Get the info of a user by his ID | GET(userId) |
| /api/user/{userId}/delete | Delete a user and his wallet by his ID | DELETE(userId) |
| /api/user/update | Update a user's info | PUT(User) |
| /api/user/updatePassword | Update a user's password | PUT(User) |
| /api/reservation/{userId}/all | Get all the reservation of a user by user ID | GET(userId) |
| /api/reservation/add | Add a new reservation if the user can pay half of the reservation's price | POST(Reservation) |
| /api/reservation/{reservationId}/update | Update the reservation by the reservation ID if the user can pay the other half, sets it to **complete** | PUT(reservationId) |
| /api/reservation/{reservationId}/delete | Refund the user and delete the reservation by it's ID | DELETE(reservationId) |
| /api/payment/add | Add a new payment | POST(Payment) |
| /api/payment/{walletId}/all | Get all the payments of a user by it's wallet ID | GET(walletId) |
