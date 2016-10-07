# Book Librarj

AWS S3 bucket
http://siili-book-library.s3-website-eu-west-1.amazonaws.com/

Postgres DB endpoint 
book-librarj.cvucsauh3hzy.eu-west-1.rds.amazonaws.com:5432

### Run locally 

1. Run database: `docker run --name some-postgres -e POSTGRES_USER=testuser -e POSTGRES_PASSWORD=testpass -p 127.0.0.1:5432:5432 -d postgres:9.3`
