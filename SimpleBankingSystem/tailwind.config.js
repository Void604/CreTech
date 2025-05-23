/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        dark: {
          900: '#0A0A0A',
          800: '#121212',
          700: '#1A1A1A',
          600: '#242424',
          500: '#2E2E2E',
          400: '#383838',
          300: '#424242',
          200: '#4C4C4C',
          100: '#565656',
        },
        accent: {
          500: '#FF6B6B',
          600: '#FF5252',
          700: '#FF3838',
        },
      },
    },
  },
  plugins: [],
};