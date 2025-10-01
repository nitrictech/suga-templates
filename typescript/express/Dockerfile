# Use an official Node.js runtime as base
FROM node:22-alpine

# Set working directory inside container
WORKDIR /app

# Install dependencies first (for better caching)
COPY package*.json ./

RUN npm ci --omit=dev

# Copy the rest of your app
COPY . .

RUN npm run build

# Start the app
CMD ["node", "dist/main.js"]
