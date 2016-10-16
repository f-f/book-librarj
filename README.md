![Book Librarj Screenshot](resources/public/screenshot.png)

# Book Librarj

A *half-baked, over-engineered, well-though small web application*, made at the [7/10/16 Clojure Hackathon](https://www.eventbrite.com/e/clojure-hackathon-tickets-27564401868#) [@ Siili](https://www.siili.com/), by [Fabrizio](https://twitter.com/fabferrai) and [Jouni](https://twitter.com/sapfanboy), with the help of lots of coffeine.

Its main purpose is to allow the management of a (physical) book library, with a nice view and the ability to search through the titles. 
We started from the images in [this S3 bucket](http://siili-book-library.s3-website-eu-west-1.amazonaws.com/) and [some relational data about the books](books.csv).

No technology was harmed during the hackathon. However, we got to play with lots of nice shiny stuff: [re-frame 0.8](https://github.com/Day8/re-frame) for a Reactive and buzzword-compliant SPA, [re-com](https://github.com/Day8/re-com) for ready-made fresh Reagent components, [Postgresql full text search](http://rachbelaid.com/postgres-full-text-search-is-good-enough/), [jq](https://stedolan.github.io/jq/) to hack with json on the command line, [AWS Elastic Beanstalk + Docker](https://juxt.pro/blog/posts/beanstalk.html) for fast Cloud deployments, [AWS CloudFormation](https://aws.amazon.com/cloudformation/) and Infrastructure as code, [System](https://github.com/danielsz/system) for a fast REPL-driven-development...

## How to run the thing

##### 0. Get [`lein`](http://leiningen.org/), `psql`, and you may want `docker` too.

### Dev environment

1. Run database: `docker run --name some-postgres -e POSTGRES_USER=testuser -e POSTGRES_PASSWORD=testpass -p 127.0.0.1:5432:5432 -d postgres:9.3`
2. Start the migration if the data: TODO
3. In a terminal start figwheel: `lein figwheel`
4. In another terminal start a repl with `lein repl` and then type `(start)` into it
5. Navigate to `localhost:3000`, start hacking!

### Production environment

1. Run the database somewhere
2. Migrate the data into it
3. Put the Postgres connection URI in project.clj
4. Build the production clojurescript with `lein cljsbuild once min`
5. Start the service with `lein with-profile prod run`

### AWS Deploy

1. Run the cloudformation script
2. Get the connection URI from the AWS webinterface
3. Run `./deploy` to deploy on Elastic Beanstalk
