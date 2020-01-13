# base image
#FROM node:12.2.0
#FROM node:latest

# install chrome for protractor tests
#RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
#RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
#RUN apt-get update && apt-get install -yq google-chrome-stable

# set working directory
#WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
#ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
#COPY package.json /app/package.json
#RUN npm install --save core-js@^3
#RUN npm install
#RUN npm install -g @angular/cli

# add app
#COPY . /app

# start app
#CMD ng serve --host 0.0.0.0

#################################

FROM node:latest

RUN mkdir /app
WORKDIR /app

COPY package.json /app/package.json
RUN npm install

RUN npm install -g @angular/cli

COPY . .

EXPOSE 4200

CMD ["ng", "serve", "--disableHostCheck", "--host", "0.0.0.0", "--port", "4200"]

##################################

#FROM node:latest as build-deps
#
#WORKDIR /home/ui/app
#
##ENV SERVER=bennettnotes.com
#
#COPY package.json ./
#
#RUN npm install
#
#COPY . ./
#
#RUN npm install -g @angular/cli
#
#CMD ["ng", "serve", "--host", "0.0.0.0"]

##########################################

## Stage 0, "build-stage", based on Node.js, to build and compile the frontend
#FROM node:10.8.0 as build-stage
#WORKDIR /app
#COPY package*.json /app/
#RUN npm install
#COPY ./ /app/
#ARG configuration=production
#RUN npm run build -- --output-path=./dist/out --configuration $configuration
#
## Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
#FROM nginx:1.15
##Copy ci-dashboard-dist
#COPY --from=build-stage /app/dist/out/ /usr/share/nginx/html
##Copy default nginx configuration
#COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf

