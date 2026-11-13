CREATE TABLE public.auth_sessions (
 id UUID PRIMARY KEY,
 user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
 access_hash VARCHAR(64) NOT NULL UNIQUE,
 refresh_hash VARCHAR(64) NOT NULL UNIQUE,
 access_expires_at TIMESTAMPTZ NOT NULL,
 refresh_expires_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_auth_sessions_user_id ON public.auth_sessions(user_id);
ALTER TABLE public.auth_sessions ENABLE ROW LEVEL SECURITY;
REVOKE ALL ON public.auth_sessions FROM anon, authenticated;
