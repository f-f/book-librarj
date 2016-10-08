FROM clojure:latest
ENV LC_ALL C
ENV DEBIAN_FRONTEND noninteractive
ENV DEBCONF_NONINTERACTIVE_SEEN true

MAINTAINER Fabrizio Ferrai <fabrizio@ferrai.io>

EXPOSE 3000
RUN apt-get update && apt-get upgrade -y
RUN mkdir /app
ADD src /app/src
ADD project.clj /app
ADD resources /app/resources
WORKDIR /app
RUN touch .lein-env
RUN mkdir target
RUN lein deps
RUN lein cljsbuild once min
CMD lein with-profile prod run
