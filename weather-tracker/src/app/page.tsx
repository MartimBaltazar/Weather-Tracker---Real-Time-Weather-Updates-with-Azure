// src/app/page.tsx (or src/pages/index.tsx)
"use client";

import { useState } from "react";

export default function Home() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const res = await fetch("http://localhost:9090/weather?city=" + city);
      if (!res.ok) throw new Error("Failed to fetch weather data");
      const data = await res.json();
      setWeather(JSON.stringify(data, null, 2));
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Weather Lookup</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={city}
          placeholder="Enter city"
          onChange={(e) => setCity(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? "Fetching..." : "Get Weather"}
        </button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
      {weather && (
        <pre style={{ background: "#f0f0f0", padding: "1rem" }}>{weather}</pre>
      )}
    </div>
  );
}
