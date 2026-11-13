-- Spring manages authentication; no Supabase Auth dependency.
CREATE TABLE public.users (
    id UUID PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    nickname VARCHAR(12) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_users_username UNIQUE (username)
);
-- Deny client API access. The backend uses its server-side DB connection.
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;
REVOKE ALL ON TABLE public.users FROM anon, authenticated;
