# 🛒 Price Comparator - Spring Boot App

Aplicație backend pentru compararea prețurilor produselor, evidențierea reducerilor și analiză de trenduri istorice.

## 🚀 Cum rulezi aplicația

1. Clonează acest repository:

```bash
git clone https://github.com/robertsamata/PriceComparator.git
cd PriceComparator
```

2. Pornește serverul Spring Boot:

```bash
./mvnw spring-boot:run
```

Serverul va porni la: [http://localhost:8080](http://localhost:8080)

---

## 📁 Structura fișierelor CSV

### 🔹 Produse – `src/main/resources/data/lidl_2025-05-08.csv`

```csv
id;name;category;brand;quantity;unit;price
P001;lapte zuzu;lactate;Zuzu;1;l;9.90;RON
P002;iaurt grecesc;lactate;Lidl;0.4;kg;11.50;RON
```

### 🔹 Reduceri – `src/main/resources/data/lidl_discounts_2025-05-08.csv`

```csv
product_id;product_name;brand;package_quantity;package_unit;product_category;from_date;to_date;percentage_of_discount
P001;lapte zuzu;Zuzu;1;l;lactate;2025-05-01;2025-05-07;10
P008;brânză telemea;Pilos;0.3;kg;lactate;2025-05-01;2025-05-07;15
```

### 🔹 Istoric prețuri – `src/main/resources/data/price_history.csv`

```csv
product_id;product_name;brand;category;store;date;price
12345;Milka Chocolate;Milka;Snacks;Lidl;2025-05-01;5.99
12345;Milka Chocolate;Milka;Snacks;Lidl;2025-05-08;5.49
```

---

## 📦 API-uri disponibile

### 🧺 `POST /api/basket/split`  
Împarte coșul de cumpărături în produse cu reducere și fără reducere.

#### 🔸 Request body (application/json)
```json
{
  "shoppingDate": "2025-05-01",
  "items": [
    { "productName": "lapte zuzu", "quantity": 2 },
    { "productName": "iaurt grecesc", "quantity": 1 }
  ]
}
```

### 📈 `GET /products`
Returnează toate produsele.

### 🧺 `POST /products/add`  
Adaugă un produs.

#### 🔸 Request body (application/json)
```json
{
  "id": "P020",
  "name": "morcovi",
  "category": "legume și fructe",
  "brand": "Lidl",
  "quantity": 1.0,
  "unit": "kg",
  "price": 5.50
}
```
### 🗑️ `DEL /products/delete/{productId}`  
Elimină un produs.
### 📈 `GET /api/discounts`
Returnează produsele cu discount.

### 📈 `GET /products/discounts/new`
Returnează toate discount-urile care au început azi sau în ultimele 1-2 zile (adică cele mai noi discount-uri recente).

### 📈 `GET /products/discounts/top`
Returnează lista celor 5 produse care au cele mai mari discounturi.

### 🧺 `POST /api/discounts/add`
Adaugă un discount nou al unui produs.
```json
{
  "productId": "P001",
  "fromDate": "2025-05-01",
  "toDate": "2025-05-07",
  "percentage": 20
}
```
### 📈 `GET /api/price/history?productId=12345&store=Lidl&category=Snacks&brand=Milka`  
Returnează istoricul prețurilor pentru un produs, filtrabil după magazin, brand sau categorie.

### 🧠 `GET /products/{productName}/recommendation`  
Din lista de produse, selectează toate produsele care:
Nu au același nume cu produsul original (ca să nu se recomande produsul original ca substitut).
Au aceeași categorie sau același brand ca produsul original.
Sortează aceste produse după valoarea lor per unitate, în ordine crescătoare (de la cel mai ieftin per unitate la cel mai scump).
Returnează lista rezultată.
### 🔔 `POST /api/alerts/`  
Setează o alertă de preț pentru un produs.

#### 🔸 Request

```json
{
  "productName": "ulei floarea-soarelui",
  "targetPrice": 4.99
}
```
### 📈 `GET /alerts/triggered`
Returnează lista cu toate alertele trigger-uite.

---

## 🧪 Cum testezi cu Postman

1. Deschide Postman
2. Creează un request:
- `POST http://localhost:8080/api/basket/split`
- `GET http://localhost:8080/products`
- `POST http://localhost:8080/products/add`
- `DEL http://localhost:8080/products/delete/P020`
- `GET http://localhost:8080/api/discounts`
- `POST http://localhost:8080/api/discounts/add`
- `GET http://localhost:8080/products/discounts/new`
- `GET http://localhost:8080/api/price/history?productId=12345&store=Lidl&category=Snacks&brand=Milka`
- `GET http://localhost:8080/products/brânză telemea/recommendations`
- `POST http://localhost:8080/alerts`
- `GET http://localhost:8080/alerts/triggered`

3. Verifică răspunsul JSON.

---

## 🛠 Tehnologii folosite

- Java 17  
- Spring Boot  
- Maven  
- OpenCSV  
- Postman  

---

## 👤 Autor

**Robert Samata**  
[GitHub Profile](https://github.com/robertsamata)
