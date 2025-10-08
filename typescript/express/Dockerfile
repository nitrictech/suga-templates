# Build stage - has ALL dependencies
FROM node:22-alpine AS builder
WORKDIR /app

# Install dependencies first (for better caching)
COPY package*.json ./
RUN npm ci

# Copy and build the application
COPY . .
RUN npm run build

# Production stage - only prod dependencies
FROM node:22-alpine
WORKDIR /app

# Install only production dependencies
COPY package*.json ./
RUN npm ci --omit=dev

# Copy built application from the builder stage
COPY --from=builder /app/dist ./dist

# Start the app
CMD ["node", "dist/main.js"]
