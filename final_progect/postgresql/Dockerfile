FROM postgres:15.2-alpine
MAINTAINER ITAcademy

ADD /service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /service/dml/*.sql /docker-entrypoint-initdb.d/
