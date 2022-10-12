# pull official base image
FROM node:17.0.1-alpine3.14

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package*.json ./
RUN npm install npm@8.1.0
RUN npm install --silent
RUN npm install react-scripts@4.0.3 -g --silent
RUN npm config set unsafe-perm true

# add app
COPY . ./

# start app
CMD ["npm", "start"]