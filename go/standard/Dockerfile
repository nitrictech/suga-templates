FROM golang:1.24-alpine as builder

WORKDIR /app

COPY . .

RUN CGO_ENABLED=0 GOOS=linux go build -o main main.go

FROM alpine:latest

RUN apk --no-cache add ca-certificates

COPY --from=builder /app/main .

ENTRYPOINT ["/main"]