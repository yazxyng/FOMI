export async function checkServer(): Promise<void> {
  const baseUrl = process.env.EXPO_PUBLIC_API_URL;
  if (!baseUrl) throw new Error('EXPO_PUBLIC_API_URL is required');
  const controller = new AbortController();
  const timeout = setTimeout(() => controller.abort(), 10000);
  try {
    const response = await fetch(`${baseUrl.replace(/\/$/, '')}/actuator/health`, { signal: controller.signal });
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    const body: unknown = await response.json();
    if (typeof body !== 'object' || body === null || !('status' in body) || body.status !== 'UP') {
      throw new Error('Server is not healthy');
    }
  } finally { clearTimeout(timeout); }
}
