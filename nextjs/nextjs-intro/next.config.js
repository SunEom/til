const API_KEY = process.env.API_KEY;
module.exports = {
  reactStrictMode: true,
  async redirects() {
    return [
      {
        source: "/old-blog/",
        destination: "https://nomadcoders.co",
        permanent: false,
      },
      {
        source: "/old-blog/:path",
        destination: "/new-sext-blog/:path",
        permanent: false,
      },
      {
        source: "/old-blog/:path*",
        destination: "/new-sext-blog/:path*",
        permanent: false,
      },
    ];
  },

  async rewrites() {
    return [
      {
        source: "/api/movies",
        destination: `https://api.themoviedb.org/3/movie/popular?api_key=${API_KEY}`,
      },
      {
        source: "/api/movies/:id",
        destination: `https://api.themoviedb.org/3/movie/:id?api_key=${API_KEY}`,
      },
    ];
  },
};
