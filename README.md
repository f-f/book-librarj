![Book Librarj Screenshot](resources/public/screenshot.png)

# Book Librarj

AWS S3 bucket
http://siili-book-library.s3-website-eu-west-1.amazonaws.com/

Postgres DB endpoint 
book-librarj.cvucsauh3hzy.eu-west-1.rds.amazonaws.com:5432

## Run a local dev environment

1. Run database: `docker run --name some-postgres -e POSTGRES_USER=testuser -e POSTGRES_PASSWORD=testpass -p 127.0.0.1:5432:5432 -d postgres:9.3`
2. Start the migration if the data: TODO
3. In a terminal start figwheel: `lein figwheel`
4. In another terminal start a repl with `lein repl` and then type `(start)` into it
5. Navigate to `localhost:3000`

## Run a production environment

1. Run the database somewhere
2. Migrate the data into it
3. Put the Postgres connection URI in project.clj
4. Build the production clojurescript with `lein cljsbuild once min`
5. Start the service with `lein with-profile prod run`

## Run on AWS

1. Run the cloudformation script
2. Get the connection URI from the AWS webinterface
3. Run `./deploy` to deploy on Elastic Beanstalk
