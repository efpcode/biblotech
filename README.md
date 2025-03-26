# Welcome to Biblotech

## Introduction
This is a school project that explores restful api design with Jakarta E11 and WildFly.

## Limitations
Limitation of the API is that all responses or postings needs to be done with JSON. 


## Json Schema

```json
	{
        "author": "George Orwell",
        "description": "A dystopian novel about a totalitarian regime that uses surveillance and thought control.",
        "id": 2,
        "isbn": "9780451524935",
        "pages": 328,
        "publishedYear": "1949-06-08",
        "title": "1984"
}
```


-----
## Endpoints of the API
The API is meant to be accessed locally with localhost.
Please enter to see landing page: ``http://localhost:8080/`` and click on the API link.

### Post
To add a new record to database use the endpoint below:
  * ``http://localhost:8080/api/books``

### Get
To explore records in the database use the following endpoints:

|| General purposes exploration:
  * ``http://localhost:8080/api/books``
Query above fetches all records provided.

Available search params are:
  * author  - Author name (optional)
  * title  - Book title (optional)
  * pageNumber - Number of page(s) for a certain query (optional)
  * pageSize - Number of elements shown per page (optiionl)
  * sortBy - Column available to order data by see [Json Schema](#json-schema). Default set to ``title``.
  * sortOrder - The direction of the sorted data ascending ``asc`` or descending ``desc`` . Default set to ``asc``. 

|| Valid Search query for Authors:
  * ``http://localhost:8080/api/books?author=j&&sortOrder=desc&pageSize=20&sortBy=pages``

|| Valid Search query for Book Title:
  * ``http://localhost:8080/api/books?title=j&&sortOrder=desc&pageSize=20&sortBy=pages``



|| Filtering records
  * ``http://localhost:8080/api/books/search``

Available search params are:
* startDate - Cut of at 1440-01-01, historical reasons [Gutenberg's press](https://en.wikipedia.org/wiki/Printing_press#Gutenberg's_press)
* endDate - To present time
* All the previous mentioned params are also available.

|| Valid Search Query Verbose
  * ``http://localhost:8080/api/books/search?author=Jane%20Austen&title=Emma&sortOrder=desc&pageSize=10&sortBy=pages&pageNumber=1&startDate=1600-01-01&endDate=1900-01-01``

|| Valid Search Less Verbose
  * ``http://localhost:8080/api/books/search?author=Jane%20Austen&title=Emma&startDate=1600-01-01&endDate=1900-01-01``

|| Valid Search Author and Title
  * ``http://localhost:8080/api/books/search?author=Jane%20Austen&title=Emma``

|| Valid Search Between Date
  * ``http://localhost:8080/api/books/search?startDate=1600-01-01&endDate=1900-01-01``

The query for between date can also be combined with only search for title or author. 


#### Single Book Result

|| Fetch record with primary key
  * ``http://localhost:8080/api/books/{id}``

The ```{id}``` parameter is the primary key to database.

|| Fetch record with isbn number
  * ``http://localhost:8080/api/books/isbn/{isbn}``

The ```{isbn}``` parameter represents the ISBN digits of the book.

### Put Patch & Delete Record

|| To delete patch and put record database:
  * ``http://localhost:8080/api/books/{id}``

The ```{id}``` parameter is the primary key to database.  

-----
## Installation 

### Prerequisites 

* Maven needs to be installed
* Docker needs to be installed and running 

### Step 1
Start by git cloning main repo:
```
git clone https://github.com/efpcode/biblotech.git 
```

### Step 2
Ensure that Docker is running in the background.

### Step 3
Run maven command below
```
mvn clean package
```
### Step 4
Run the following docker command
```
docker-compose --profile production up
```
### Step 5

Start project by either click on the link provided in Docker dash bord or paste
this link to preferred url:

``http://localhost:8080/api/books``

-----

# THANK YOU
