# Use an official Node.js runtime as base
FROM node:22-alpine

# Set working directory inside container
WORKDIR /app

# Install dependencies first (for better caching)
COPY package*.json ./

RUN npm ci --omit=dev

# Copy the rest of your app
COPY . .

# Expose the port your app runs on
EXPOSE 4001

# Start the app
CMD ["node", "main.js"]
