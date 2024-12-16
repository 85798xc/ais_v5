-- Create the 'roles' enum type
CREATE TYPE role AS ENUM ('ADMIN', 'LECTURER', 'STUDENT');

-- Create the 'users' table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(48) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(48) NOT NULL,
                       lastname VARCHAR(48) NOT NULL
);

-- Create the 'user_roles' join table for 'roles' (many-to-many relationship between users and roles)
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role role NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role)
);

-- Create the 'subjects' table
CREATE TABLE subjects (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE,
                          lecturer_id BIGINT NOT NULL,
                          FOREIGN KEY (lecturer_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create the 'groups' table
CREATE TABLE groups (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

-- Create the 'group_students' join table for 'students' in 'groups' (many-to-many relationship)
CREATE TABLE group_students (
                                group_id BIGINT NOT NULL,
                                student_id BIGINT NOT NULL,
                                FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
                                FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
                                PRIMARY KEY (group_id, student_id)
);

-- Create the 'group_subjects' join table for 'subjects' in 'groups' (many-to-many relationship)
CREATE TABLE group_subjects (
                                group_id BIGINT NOT NULL,
                                subject_id BIGINT NOT NULL,
                                FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
                                FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
                                PRIMARY KEY (group_id, subject_id)
);

-- Create the 'grades' table
CREATE TABLE grades (
                        id BIGSERIAL PRIMARY KEY,
                        student_id BIGINT NOT NULL,
                        subject_id BIGINT NOT NULL,
                        grade VARCHAR(10),
                        FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
                        FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);
