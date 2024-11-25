CREATE SCHEMA IF NOT EXISTS "test-schema";

CREATE TABLE "test-schema".category (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    image_url varchar(255) NULL,
    "name" varchar(255) NULL,
    order_number int4 NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE "test-schema".category_permission (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    "role" varchar(255) NULL,
    subscriptions text[],
    "version" BIGINT DEFAULT 0,
    category_id varchar(255) NOT NULL,
    CONSTRAINT category_permission_pkey PRIMARY KEY (id),
    CONSTRAINT category_permission_role_check CHECK (((role)::text = ANY ((ARRAY['BLOCKED_USER'::character varying, 'SUSPENDED_USER'::character varying, 'GENERAL_AUDIENCE'::character varying, 'VERIFIED_USER'::character varying, 'FRIEND'::character varying, 'ADMIN'::character varying, 'PENDING_VERIFICATION'::character varying])::text[]))),
    CONSTRAINT category_permission_fkey FOREIGN KEY (category_id) REFERENCES "test-schema".category(id)
);

CREATE TABLE "test-schema".rubric (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    category_id varchar(255) NULL,
    image_url varchar(255) NULL,
    "name" varchar(255) NULL,
    order_number int4 NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT rubric_pkey PRIMARY KEY (id),
    CONSTRAINT rubric_fkey FOREIGN KEY (category_id) REFERENCES "test-schema".category (id)
);

CREATE TABLE "test-schema".parameter (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    about varchar(255) NULL,
    category_id varchar(255) NULL,
    rubric_id varchar(255) NULL,
    "name" varchar(255) NULL,
    order_number int4 NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT parameter_pkey PRIMARY KEY (id),
    CONSTRAINT parameter_category_fkey FOREIGN KEY (category_id) REFERENCES "test-schema".category(id),
    CONSTRAINT parameter_rubric_fkey FOREIGN KEY (rubric_id) REFERENCES "test-schema".rubric(id)
);

CREATE TABLE "test-schema".test (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    "name" varchar(255) NULL,
    order_number int4 NULL,
    rubric_id varchar(255) NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT test_pkey PRIMARY KEY (id),
    CONSTRAINT test_rubric_fkey FOREIGN KEY (rubric_id) REFERENCES "test-schema".rubric(id)
);

CREATE TABLE "test-schema".test_parameter (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    about varchar(1024) NULL,
    "name" varchar(255) NULL,
    test_id varchar(255) NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT test_param_pkey PRIMARY KEY (id),
    CONSTRAINT test_param_fkey FOREIGN KEY (test_id) REFERENCES "test-schema".test(id)
);

CREATE TABLE "test-schema".test_parameter_value (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    max_value int4 NULL,
    min_value int4 NULL,
    "text" varchar(255) NULL,
    test_parameter_id varchar(255) NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT test_param_value_pkey PRIMARY KEY (id),
    CONSTRAINT test_param_value_fkey FOREIGN KEY (test_parameter_id) REFERENCES "test-schema".test_parameter(id)
);

CREATE TABLE "test-schema".test_card (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    back varchar(4096) NULL,
    front varchar(4096) NULL,
    gimmy varchar(1024) NULL,
    image_url varchar(255) NULL,
    max_value int4 NULL,
    min_value int4 NULL,
    card_name varchar(255) NULL,
    opportunities varchar(4096) NULL,
    strengths varchar(4096) NULL,
    test_id varchar(255) NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT test_card_pkey PRIMARY KEY (id),
    CONSTRAINT test_card_fkey FOREIGN KEY (test_id) REFERENCES "test-schema".test(id)
);

CREATE TABLE "test-schema".test_question (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    "text" varchar(4096) NULL,
    test_id varchar(255) NULL,
    parameter_id varchar(255) NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT test_question_pkey PRIMARY KEY (id),
    CONSTRAINT test_question_param_fkey FOREIGN KEY (parameter_id) REFERENCES "test-schema".test_parameter(id),
    CONSTRAINT test_question_test_fkey FOREIGN KEY (test_id) REFERENCES "test-schema".test(id)
);

CREATE TABLE "test-schema".test_answer (
    id varchar(255) NOT NULL DEFAULT gen_random_uuid(),
    "text" varchar(4096) NULL,
    score int4 NULL,
    question_id varchar(255) NULL,
    "version" BIGINT DEFAULT 0,
    CONSTRAINT test_answer_pkey PRIMARY KEY (id),
    CONSTRAINT test_answer_fkey FOREIGN KEY (question_id) REFERENCES "test-schema".test_question(id)
);