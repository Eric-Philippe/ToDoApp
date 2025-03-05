# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2024-10-20

### Added

#### Project

Cleaned up the project and added the following features:

- Added a `README.md` file with the project description and instructions
- Added a `CHANGELOG.md` file to document the project changes
- Added husky and lint-staged to lint the code before commit
- Added a bunch of scripts during deployment to make it easier to run the project

### Fixed

#### Frontend

- Fixed the `done` status of the todos
- The logged user was in fact not properly logged in the frontend

## [1.0.0-alpha.2] - 2024-10-19

### Added

#### Frontend

The whole frontend is now up and running with the following features:

- Login
- Register
- Create a new todo
- Consult all todos
- Put your todos as done

## [1.0.0-alpha.1] - 2024--10-17

### Added

#### API

The v1 API is now up and running with the following endpoints:

- `GET /v1/todos`
- `POST /v1/todos`
- `GET /v1/todos/:id`
- `PUT /v1/todos/:id`
- `DELETE /v1/todos/:id`

- `POST /v1/auth/login`
- `POST /v1/auth/register`
- `GET /v1/auth/me`

- Access to Swagger API documentation at `/api`

#### Project

- The whole project is now under a Docker environment
- - NestJS
- - PostgreSQL
- - PGAdmin4

> Check the [.env.sample](.env.sample) file to set your environment variables.
