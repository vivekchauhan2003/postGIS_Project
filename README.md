### ✅ **PostGIS Tutorial Index Table**

| Section No. | Title                                                 | Sub-Sections / Highlights                                                                               |
| ----------- | ----------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| 1           | ✅ What is PostGIS?                                    | Introduction, spatial types, indexes, functions                                                         |
| 2           | #1 To Mark a Point on Map                             | `ST_SetSRID`, `ST_MakePoint` example                                                                    |
| 3           | 🧠 What are `ST_DWithin`, `ST_SetSRID`, etc.?         | Definition of spatial functions                                                                         |
| 4           | 🔍 Examples of Common PostGIS Functions               | `ST_SetSRID`, `ST_MakePoint`, `ST_DWithin`, `ST_Distance`, `ST_Within`                                  |
| 5           | 🧾 Summary Table (Functions)                          | Summary of commonly used PostGIS functions                                                              |
| 6           | ✅ Step 1: Basic Spatial Concepts                      | Data types: `GEOMETRY`, `POINT`, `LINESTRING`, `POLYGON`, `GEOGRAPHY`                                   |
| 7           | ✅ Step 2: Creating a Table with Spatial Column        | `CREATE TABLE cities (...)` example                                                                     |
| 8           | ✅ Step 3: Insert Spatial Data                         | Example `INSERT` statements                                                                             |
| 9           | ✅ Step 4: View the Data                               | `ST_AsText(location)` example                                                                           |
| 10          | ✅ Phase 1: Basics – Geometry & Setup                  | `Point`, `LineString`, `Polygon`, `MultiPoint`, `MultiLineString`, `MultiPolygon`, `GeometryCollection` |
| 11          | A. Get Polygon JSON data for geojson.io               | `ST_AsGeoJSON(ST_MakePolygon(...))` example                                                             |
| 12          | B. Get Polygon shape on PostGIS map                   | `INSERT INTO regions (...)` with polygon geometry                                                       |
| 13          | ✅ Phase 2: Spatial Queries                            | Intro to queries using `ST_Distance`, `ST_DWithin`, `ST_AsText`                                         |
| 14          | ✅ ST\_AsText(geometry)                                | Syntax, example, analogy                                                                                |
| 15          | ✅ ST\_Distance(geometryA, geometryB)                  | Syntax, meters conversion, cab app example                                                              |
| 16          | ✅ ST\_DWithin(geometryA, geometryB, distance)         | Syntax, food delivery app example                                                                       |
| 17          | ✅ Combined Practical Example                          | Using `ST_Distance` + `ST_DWithin` with JOIN                                                            |
| 18          | 📘 Tips for Advanced Use                              | Nearby cities, order by distance                                                                        |
| 19          | 📌 Summary Table (Spatial Queries)                    | Table for `ST_AsText`, `ST_Distance`, `ST_DWithin`                                                      |
| 20          | ✅ Phase 3: Geography vs Geometry (Basics to Advanced) | Differences, usage guide, real-life comparisons                                                         |
| 21          | ✅ Geometry vs Geography – Creation                    | Syntax examples for both                                                                                |
| 22          | ✅ Real-Life Example – Distance Issue with Geometry    | Why geography gives better unit output                                                                  |
| 23          | ✅ Store a Column as Geography Instead of Geometry     | `CREATE TABLE` with `GEOGRAPHY`                                                                         |
| 24          | ✅ When to Use Geometry vs Geography                   | Comparison table of use cases                                                                           |
| 25          | ✅ Convert Geometry to Geography                       | Casting example using `geography()`                                                                     |
| 26          | ✅ Indexing for Geography                              | `CREATE INDEX USING GIST(...)`                                                                          |
| 27          | ✅ Limitations of Geography                            | Unsupported functions, casting issues                                                                   |
| 28          | 📌 Summary: When to Use What?                         | Decision table: `GEOGRAPHY` vs `GEOMETRY`                                                               |
---

## ✅ What is PostGIS?
PostGIS turns the PostgreSQL Database Management System into a spatial database by adding support for the three features: spatial types, spatial indexes, and spatial functions. Because it is built on PostgreSQL, PostGIS automatically inherits important “enterprise” features as well as open standards for implementation.

PostGIS is an **extension for PostgreSQL** that adds support for **geospatial data** like:

* Points (e.g., location of a city)
* Lines (e.g., roads)
* Polygons (e.g., country borders)
* Distance and location queries (e.g., find all cities within 50km)

### #1 To mark a point on map.
---
````sql
SELECT ST_SetSRID(ST_MakePoint(Longitude, Latitude), WGS_84_Geographic coordinate system);
-- Example : 
SELECT ST_SetSRID(ST_MakePoint(37.6173, 55.7558), 4326);
````
## 🧠 What are `ST_DWithin`, `ST_SetSRID`, etc.?

These are **PostGIS Spatial Functions** — special SQL functions that help you work with **geographic data** (maps, coordinates, distances, shapes).

---

## 🔍 Examples of Common PostGIS Functions:

### 1. **`ST_SetSRID(geometry, srid)`**

> 🧩 Assigns a **Spatial Reference ID** (like EPSG:4326) to a geometry object.

📌 **Real-life example:**

```sql
ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)
```

This tells PostGIS: “This point is on **Earth**, using the standard GPS coordinate system.”

---

### 2. **`ST_MakePoint(x, y)`**

> Creates a **geographic point** using X (longitude) and Y (latitude).

📌 Example:

```sql
ST_MakePoint(77.1025, 28.7041)
```

Creates a GPS point for **Delhi**.

---

### 3. **`ST_DWithin(geom1, geom2, distance)`**

> Checks if two geometries are **within a certain distance** of each other.

📌 **Real-life example:**

```sql
SELECT name FROM cities
WHERE ST_DWithin(location, ST_MakePoint(77.1, 28.7)::geography, 50000);
```

Finds all cities **within 50 km** of the given point.

🧠 You can think of this like: “Show me all delivery areas within 50 km of a user’s location.”

---

### 4. **`ST_Distance(geom1, geom2)`**

> Calculates the **distance between two points**.

📌 Example:

```sql
SELECT ST_Distance(
  ST_MakePoint(77.1025, 28.7041)::geography,
  ST_MakePoint(79.0270, 27.2256)::geography
);
```

Tells you how far apart two points are, in meters.

---

### 5. **`ST_Within(geom1, geom2)`**

> Returns **true if geom1 is completely inside geom2**.

📌 Example:

```sql
ST_Within(delivery_location, city_boundary)
```

Checks if a delivery location is **inside** the city boundary.

---

## 🧾 Summary Table:

| Function       | Purpose                               | Real-life Use Case               |
| -------------- | ------------------------------------- | -------------------------------- |
| `ST_SetSRID`   | Assigns coordinate system             | "This point is on Earth"         |
| `ST_MakePoint` | Makes a GPS point                     | Pin on map (like Google Maps)    |
| `ST_DWithin`   | Checks if 2 points are close          | "Cities within 50 km"            |
| `ST_Distance`  | Measures distance between two points  | "How far is A from B?"           |
| `ST_Within`    | Checks if one shape is inside another | "Is a house inside city limits?" |

---

Would you like hands-on SQL queries using these functions for practice?



## ✅ Step 1: Basic Spatial Concepts in PostGIS

### 🔸 1. **Spatial Data Types**

Here are the most common ones:

| Data Type    | Description                                                                   |
| ------------ | ----------------------------------------------------------------------------- |
| `GEOMETRY`   | Generic shape (point, line, polygon)                                          |
| `POINT`      | A single coordinate (like GPS point)                                          |
| `LINESTRING` | A line made of multiple points                                                |
| `POLYGON`    | Enclosed area (like a city boundary)                                          |
| `GEOGRAPHY`  | Like `GEOMETRY`, but uses real Earth curvature (used for distances in meters) |

---

## ✅ Step 2: Creating a Table with Spatial Column

Let's make a sample `cities` table that stores city name and location.

```sql
CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    location GEOMETRY(Point, 4326)
);
```

📌 Here, `4326` is the **SRID** (Spatial Reference ID) for GPS-style lat/lon (WGS 84).

---

## ✅ Step 3: Insert Spatial Data

Let’s add some cities with lat/lon values:

```sql
INSERT INTO cities (name, location)
VALUES
('Delhi', ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)),
('Mumbai', ST_SetSRID(ST_MakePoint(72.8777, 19.0760), 4326)),
('Kolkata', ST_SetSRID(ST_MakePoint(88.3639, 22.5726), 4326));
```

---

## ✅ Step 4: View the Data

```sql
SELECT id, name, ST_AsText(location) FROM cities;
```

You should see output like:

```
1 | Delhi   | POINT(77.1025 28.7041)
2 | Mumbai  | POINT(72.8777 19.076)
...
```
---

In PostGIS (and GIS in general), **geometry types** represent different shapes and features on the Earth's surface. These are defined by the **OGC Simple Features Specification** and are used to model points, lines, and areas. Let's break them down simply with real-world examples:

---
# ✅ Phase 1: Basics – Geometry & Setup
---
## 🔵 **1. Point**

* A single location defined by one pair of coordinates (x, y).
* **Example**: A street light, a water tap, or your current GPS location.

```sql
SELECT ST_AsText(ST_MakePoint(77.1025, 28.7041));  -- Delhi
-- Output: POINT(77.1025 28.7041)
```

---

## ➖ **2. LineString**

* A series of points connected by straight lines — used to represent linear features.
* **Example**: A road, river, or railway line.

```sql
SELECT ST_AsText(ST_MakeLine(ARRAY[
  ST_MakePoint(77.1, 28.7),
  ST_MakePoint(77.2, 28.8)
]));
-- Output: LINESTRING(77.1 28.7, 77.2 28.8)
```

---

## 🔷 **3. Polygon**

* A closed area defined by at least **4 points** (first and last point must be the same).
* **Example**: A park, building boundary, or a lake.

```sql
SELECT ST_AsText(ST_MakePolygon(ST_GeomFromText(
  'LINESTRING(0 0, 0 2, 2 2, 2 0, 0 0)'
)));
-- Output: POLYGON((0 0, 0 2, 2 2, 2 0, 0 0))
```

---

## 🟢 **4. MultiPoint**

* A collection of **multiple points**.
* **Example**: Locations of all ATMs in a city.

```sql
SELECT ST_AsText(ST_Collect(
  ST_MakePoint(77.1, 28.7),
  ST_MakePoint(77.2, 28.8)
));

-- or (below to view on map)

SELECT ST_SetSRID(ST_Collect(
  ST_MakePoint(77.1, 28.7),
  ST_MakePoint(77.2, 28.8)
), 4326);
```

---

## 🔶 **5. MultiLineString**

* A collection of multiple LineStrings.
* **Example**: A city's road network or multiple train tracks.

```sql
SELECT ST_AsText(ST_Collect(
  ST_GeomFromText('LINESTRING(0 0, 1 1)'),
  ST_GeomFromText('LINESTRING(2 2, 3 3)')
));
-- Output: MULTILINESTRING((0 0, 1 1), (2 2, 3 3))
-- or (below to view on map)

SELECT ST_SetSRID(ST_Collect(
  ST_GeomFromText('LINESTRING(0 0, 1 1)'),
  ST_GeomFromText('LINESTRING(2 2, 3 3)')
), 4326);
```

---

## 🟥 **6. MultiPolygon**

* A collection of multiple polygons.
* **Example**: A country with multiple islands, or a campus with separate land plots.

```sql
SELECT ST_AsText(ST_Collect(
  ST_GeomFromText('POLYGON((0 0, 0 1, 1 1, 1 0, 0 0))'),
  ST_GeomFromText('POLYGON((2 2, 2 3, 3 3, 3 2, 2 2))')
));
-- Output: MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)), ((2 2, 2 3, 3 3, 3 2, 2 2)))
-- or (below to view on map)

SELECT ST_SetSRID(ST_Collect(
  ST_GeomFromText('POLYGON((0 0, 0 1, 1 1, 1 0, 0 0))'),
  ST_GeomFromText('POLYGON((2 2, 2 3, 3 3, 3 2, 2 2))')
), 4326);
```

---

## 📦 **7. GeometryCollection**

* A mixed collection of any geometry types: Point, LineString, Polygon, etc.
* **Example**: A feature that includes a building (polygon), entrance (point), and road (line).

```sql
SELECT ST_AsText(ST_Collect(
  ST_MakePoint(1, 1),
  ST_GeomFromText('LINESTRING(0 0, 1 1)'),
  ST_GeomFromText('POLYGON((2 2, 2 3, 3 3, 3 2, 2 2))')
));
-- Output: GEOMETRYCOLLECTION(POINT(1 1), LINESTRING(0 0, 1 1), POLYGON((2 2, 2 3, 3 3, 3 2, 2 2)))

--above might not work : 
SELECT ST_AsText(ST_Collect(ARRAY[
  ST_MakePoint(1, 1),
  ST_GeomFromText('LINESTRING(0 0, 1 1)'),
  ST_GeomFromText('POLYGON((2 2, 2 3, 3 3, 3 2, 2 2))')
]));
-- or (below to view on map)
SELECT ST_SetSRID(ST_Collect(ARRAY[
  ST_MakePoint(1, 1),
  ST_GeomFromText('LINESTRING(0 0, 1 1)'),
  ST_GeomFromText('POLYGON((2 2, 2 3, 3 3, 3 2, 2 2))')
]), 4326);

```

---
## A. Get Polygon JSON data for : geojson.io
This will make a polygon shape for given coordinate to mark a area : on geojson.io
````sql
SELECT ST_AsGeoJSON(ST_MakePolygon(ST_GeomFromText(
  'LINESTRING(
    77.70140092792002 29.009596248113937,
    77.70406728952923 29.00942690767178,
    77.70423583203615 29.005336785157738,
    77.70414459373166 29.005322907817458,
    77.70363547214085 29.005579405090856,
    77.70309140451249 29.005827412683175,
    77.70253328417755 29.00602289372935,
    77.7006298830608  29.006381631490214,
    77.70138755774542 29.009598623003328,
    77.70140231286825 29.009597977806763,
    77.70140092792002 29.009596248113937
  )', 4326))) AS geojson;
````

## B. Get Polygon shape on postGIS map. (internal) to view it on QGIS use "A".
This will make a polygon shape for given coordinate to mark a area : on postGIS map
````sql
INSERT INTO regions (name, area)
VALUES (
  'Mainpuri-Fatehgarh-Etawah-Delhi Region',
  ST_MakePolygon(ST_GeomFromText(
    'LINESTRING(
      79.0280 27.2316,
      79.6315 27.3616,
      79.0219 26.7769,
      77.1025 28.7041,
      79.0280 27.2316
    )', 4326))
);
````
---
# ✅ Phase 2: Spatial Queries
---

✅ **ST\_Distance**
✅ **ST\_DWithin**
✅ **ST\_AsText**

We'll explore each function step-by-step with syntax, use cases, examples, and real-life analogies.

---

### ✅ 1. **ST\_AsText(geometry)** – *Convert Geometry to Readable Format*

#### 📌 Purpose:

Converts a PostGIS geometry object (which is usually stored in binary) into a human-readable WKT (Well-Known Text) format.

#### 🧠 Syntax:

```sql
SELECT ST_AsText(geometry_column) FROM table_name;
```

#### 🧪 Example:

```sql
SELECT ST_AsText(location) FROM cities;
```

#### 🧍 Real-life Analogy:

Imagine you stored a map location as a GPS chip. `ST_AsText` is like reading its data in plain English: “POINT(77.1025 28.7041)” instead of unreadable coordinates.

---

### ✅ 2. **ST\_Distance(geometryA, geometryB)** – *Exact Distance Between Two Points*

#### 📌 Purpose:

Calculates the Euclidean (straight-line) distance between two geometries.

#### 🧠 Syntax:

```sql
SELECT ST_Distance(geom1, geom2);
```

> 📏 Returns the distance in the unit of the SRID (if using SRID 4326, it's in degrees; use geography type or transform to get meters/kilometers).

#### 🧪 Example:

```sql
SELECT ST_Distance(
  ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326), -- Delhi
  ST_SetSRID(ST_MakePoint(80.9462, 26.8467), 4326)  -- Lucknow
);
```

#### 🌍 Real-life Use:

You're building a cab app. Use `ST_Distance` to calculate how far two users or a driver and a rider are from each other.

#### 🔧 Convert to Meters:

```sql
SELECT ST_Distance(
  geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)),
  geography(ST_SetSRID(ST_MakePoint(80.9462, 26.8467), 4326))
);
```

---

### ✅ 3. **ST\_DWithin(geometryA, geometryB, distance)** – *Check if Within Distance*

#### 📌 Purpose:

Returns `true` if two geometries are within a given distance of each other.

#### 🧠 Syntax:

```sql
SELECT ST_DWithin(geom1, geom2, distance);
```

#### 🧪 Example:

```sql
SELECT ST_DWithin(
  geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)),  -- Delhi
  geography(ST_SetSRID(ST_MakePoint(80.9462, 26.8467), 4326)),  -- Lucknow
  500000  -- meters (500 km)
);
```

#### ✅ Output:

Returns `true` or `false`.

#### 🧍 Real-life Use:

Food delivery app: Show only restaurants within 5 km radius.

---

### ✅ Combined Practical Example:

```sql
-- Let's say we have a cities table with (name, location)
SELECT 
  a.name AS city1,
  b.name AS city2,
  ST_Distance(geography(a.location), geography(b.location)) AS distance_in_meters,
  ST_DWithin(geography(a.location), geography(b.location), 300000) AS within_300_km
FROM 
  cities a,
  cities b
WHERE 
  a.name = 'Delhi' AND b.name = 'Lucknow';
```

---

### 📘 Tips for Advanced Use:

#### ✅ Find all nearby cities within 100km:

```sql
SELECT name
FROM cities
WHERE ST_DWithin(
  geography(location),
  geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)),  -- Delhi
  100000 -- 100 km
);
```

#### ✅ Order cities by distance from a point:

```sql
SELECT name,
  ST_Distance(
    geography(location),
    geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326))
  ) AS distance
FROM cities
ORDER BY distance ASC;
```

---

### 📌 Summary Table

| Function      | Purpose                           | Output Type            | Real-World Use                       |
| ------------- | --------------------------------- | ---------------------- | ------------------------------------ |
| `ST_AsText`   | Human-readable geometry text      | Text (WKT)             | Debug/Print geometry                 |
| `ST_Distance` | Exact distance between geometries | Float (unit-based)     | Show route distance                  |
| `ST_DWithin`  | Boolean if within given distance  | Boolean (`true/false`) | Check if a service is within a range |

### #SQL Examples of ABOVE ✅ Phase 2: Spatial Queries
````sql
-- ✅ 1. Read Geometry in Text Format (WKT)
SELECT id, name, ST_AsText(location) AS coordinates FROM cities;

-- ✅ 2. Distance Between Two Cities (in meters)
SELECT 
  ST_Distance(
    geography(a.location),
    geography(b.location)
  ) AS distance_meters
FROM 
  cities a,
  cities b
WHERE 
  a.name = 'Delhi' AND b.name = 'Amantyatech, Gurugram';

-- ✅ 3. Find Cities Within 150 km of Delhi
SELECT 
  c.name,
  ST_Distance(geography(c.location), geography(d.location)) AS distance_m
FROM 
  cities c,
  (SELECT location FROM cities WHERE name = 'Delhi') AS d
WHERE 
  ST_DWithin(
    geography(c.location),
    geography(d.location),
    150000  -- 150 km
  )
ORDER BY distance_m;

-- ✅ 4. Find Nearest City to a Given City
SELECT 
  c.name,
  ST_Distance(geography(c.location), geography(d.location)) AS distance_m
FROM 
  cities c,
  (SELECT location FROM cities WHERE name = 'Fatehgarh') AS d
WHERE 
  c.name != 'Fatehgarh'
ORDER BY distance_m
LIMIT 1;
````
---
# ✅ Phase 3: Geography vs Geometry (Basics to Advanced)
---

## ✅ 1. 🧠 Core Concept

| Aspect      | `geometry` (default)                     | `geography` (optional)            |
| ----------- | ---------------------------------------- | --------------------------------- |
| Earth shape | Flat 2D plane (Euclidean)                | Round Earth (spherical)           |
| Units       | Depends on SRID (e.g., degrees for 4326) | Always meters                     |
| Accuracy    | Less accurate over long distances        | Accurate for real-world distances |
| Speed       | Faster for small/local areas             | Slightly slower but more accurate |
| Use cases   | Local maps, drawings, games              | Distance/radius queries, GPS apps |

---

## ✅ 2. 🔧 How to Create Geometry vs Geography

### 🧱 Geometry (default)

```sql
-- Point stored as geometry
SELECT ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326);
```

### 🌐 Geography (spherical)

```sql
-- Point casted as geography
SELECT geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326));
```

---

## ✅ 3. 🔍 Real-Life Use Case Example

### ❌ Using geometry (may return wrong units)

```sql
SELECT ST_Distance(
  ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326), 
  ST_SetSRID(ST_MakePoint(80.9462, 26.8467), 4326)
);
```

📌 This returns **degrees** (not meters), which is hard to interpret.

---

### ✅ Using geography (returns meters)

```sql
SELECT ST_Distance(
  geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)), 
  geography(ST_SetSRID(ST_MakePoint(80.9462, 26.8467), 4326))
);
```

📌 This returns **distance in meters**, e.g., `435000` (i.e., 435 km between Delhi and Lucknow)

---

## ✅ 4. 🌐 Store a Column as Geography Instead of Geometry

### ❌ Old (geometry column):

```sql
CREATE TABLE cities (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  location GEOMETRY(Point, 4326)
);
```

### ✅ New (geography column):

```sql
CREATE TABLE cities_geo (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  location GEOGRAPHY(Point, 4326)
);
```

---

## ✅ 5. 📏 When to Use Geometry vs Geography

| Situation                                         | Use `geometry` | Use `geography`       |
| ------------------------------------------------- | -------------- | --------------------- |
| Small-scale maps (city blocks)                    | ✅              | ❌                     |
| Large-scale queries (100+ km)                     | ❌              | ✅                     |
| Accurate distance in km/miles needed              | ❌              | ✅                     |
| You want fastest performance                      | ✅              | ❌                     |
| You use geometry operations (e.g. ST\_Intersects) | ✅              | ✅ (limited functions) |

---

## ✅ 6. 🔄 Convert Geometry to Geography

You can convert geometry to geography in your queries:

```sql
SELECT ST_Distance(
  geography(location), 
  geography(ST_SetSRID(ST_MakePoint(78.0092, 27.1767), 4326))
)
FROM cities
WHERE name = 'Agra';
```

---

## ✅ 7. 💡 Advanced Tip: Indexing for Geography

If you use `GEOGRAPHY` type columns, **always add a spatial index**:

```sql
CREATE INDEX cities_location_geog_idx
ON cities
USING GIST (location);
```

This speeds up queries like:

```sql
SELECT name
FROM cities
WHERE ST_DWithin(
  geography(location),
  geography(ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326)),
  50000 -- 50 km
);
```

---

## ✅ 8. 🚫 Limitations of Geography

* Some geometry-only functions won’t work (e.g. `ST_Union`, `ST_ConvexHull`)
* You cannot mix geometry and geography directly — must cast explicitly
* Slower than geometry for large datasets

---

## 📌 Summary: When to Use What?

| Requirement                       | Recommended Type |
| --------------------------------- | ---------------- |
| Accurate distance in meters       | ✅ `GEOGRAPHY`    |
| Local mapping, visual tools       | ✅ `GEOMETRY`     |
| Small area (city layout) analysis | ✅ `GEOMETRY`     |
| Global app (delivery, flights)    | ✅ `GEOGRAPHY`    |
  
---






