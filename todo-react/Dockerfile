# pull official base image
FROM nginx:stable-alpine

# set working directory
WORKDIR /app

# add app
COPY build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]