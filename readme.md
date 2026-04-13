# 🛡️ Gestor de Pólizas - API REST

## 📌 Descripción

API desarrollada en **Spring Boot** para la gestión de pólizas y riesgos, implementando reglas de negocio y desacoplamiento mediante arquitectura basada en puertos y adaptadores.

---

## 🧱 Arquitectura

El proyecto sigue principios de arquitectura limpia:

* **Domain** → reglas de negocio
* **Application** → servicios (casos de uso)
* **Infrastructure** → adaptadores (HTTP, base de datos, integración externa)

---

## ⚙️ Tecnologías

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database
* Maven

---

## Herramientas

## Dentro del proyecto se tiene una colección de postman que ayudara a validar las peticiones

## 📡 Endpoints principales

---

### 🧾 Crear póliza

**POST**

```
http://localhost:8080/polizas
```

#### Ejemplos válidos

**1️⃣ Póliza INDIVIDUAL**

```json
{
  "tipo": "INDIVIDUAL",
  "canonMensual": 1000000,
  "prima": 12000000,
  "mesesVigencia": 12,
  "riesgos": [
    {
      "descripcion": "Robo",
      "estado": "ACTIVO"
    }
  ]
}
```

---

**2️⃣ Póliza COLECTIVA**

```json
{
  "tipo": "COLECTIVA",
  "canonMensual": 1000000,
  "prima": 12000000,
  "mesesVigencia": 12,
  "riesgos": [
    {
      "descripcion": "Robo",
      "estado": "ACTIVO"
    },
    {
      "descripcion": "Daño",
      "estado": "ACTIVO"
    }
  ]
}
```

---

### ❌ Caso inválido

Intento de crear póliza **INDIVIDUAL con múltiples riesgos**:

```json
{
  "tipo": "INDIVIDUAL",
  "canonMensual": 1000000,
  "prima": 12000000,
  "mesesVigencia": 12,
  "riesgos": [
    {
      "descripcion": "Robo",
      "estado": "ACTIVO"
    },
    {
      "descripcion": "Daño",
      "estado": "ACTIVO"
    }
  ]
}
```

👉 Resultado: **Excepción de negocio**

---

## 🔍 Consultar pólizas

**GET**

```
http://localhost:8080/polizas
```

### Filtros disponibles

| Tipo       | Estado    |
| ---------- | --------- |
| INDIVIDUAL | ACTIVA    |
| COLECTIVA  | CANCELADA |
| INDIVIDUAL | RENOVADA  |

### Ejemplos

```
http://localhost:8080/polizas?tipo=COLECTIVA&estado=ACTIVA
http://localhost:8080/polizas?tipo=INDIVIDUAL&estado=ACTIVA
http://localhost:8080/polizas?tipo=COLECTIVA&estado=CANCELADA
http://localhost:8080/polizas?tipo=INDIVIDUAL&estado=RENOVADA
```

---

## 📦 Riesgos

### 🔎 Obtener riesgos por póliza

**GET**

```
http://localhost:8080/polizas/{id}/riesgos
```

---

### ➕ Agregar riesgo

**POST**

```
http://localhost:8080/polizas/{id}/riesgos
```

#### Ejemplo

```json
{
  "descripcion": "Nuevo riesgo",
  "estado": "ACTIVO"
}
```

#### Valor del IPC usado 5.56%

---

### ⚠️ Restricción importante

* ❌ Pólizas **INDIVIDUALES** NO pueden tener más de un riesgo
* ✅ Solo pólizas **COLECTIVAS** pueden agregar múltiples riesgos

#### Ejemplos de prueba

```
http://localhost:8080/polizas/1/riesgos
http://localhost:8080/polizas/2/riesgos
http://localhost:8080/polizas/3/riesgos
```

👉 Las pólizas individuales generarán excepción por regla de negocio

---

### ❌ Cancelar riesgo

**POST**

```
http://localhost:8080/riesgos/{id}/cancelar
```

---

## 🔄 Operaciones sobre pólizas

### 🔁 Renovar póliza

**POST**

```
http://localhost:8080/polizas/{id}/renovar
```

---

### ❌ Cancelar póliza

**POST**

```
http://localhost:8080/polizas/{id}/cancelar
```

---

## 🧠 Reglas de negocio

* Una póliza **INDIVIDUAL** solo puede tener **un riesgo**
* Una póliza **COLECTIVA** puede tener múltiples riesgos
* No se puede renovar una póliza cancelada
* Cancelar una póliza cancela todos sus riesgos
* No se pueden agregar riesgos a pólizas individuales

---

## 🔌 Integración con CORE (Mock)

**Endpoint:**

```
POST /core-mock/evento
```

**Header requerido para toda la aplicación:**

```
x-api-key: 123456
```

👉 Se utiliza para simular eventos hacia un sistema externo.

---

## 👨‍💻 Autor

Stiven Usmer Joel Bustos
Ingeniero de sistemas
