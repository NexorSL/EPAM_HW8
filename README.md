# EPAM_HW8
### it is a REST API that implements a CRUD JDBC console application that has next entities:
- Developer
- Skill
- Account

Used a MySQL database as storage.
<br/>
Liquibase is used to initialize tables in DB and fill them.
<br/>
The user has the ability to create, read, update and delete data.
<br/>

Layers:
---
- model - POJO classes
- repository - classes that provide access to database
- service - classes with business logic
- rest – servlets that handles user’s requests

This api is deployed to heroku.

Link to project: https://java-epam-restapi.herokuapp.com
---
Endpoints
- >/api/v1/developers
- >/api/v1/accounts
- >/api/v1/skills

Technology stack: Java, MySQL, JDBC, Servlets, Liquibase, JUnit.
