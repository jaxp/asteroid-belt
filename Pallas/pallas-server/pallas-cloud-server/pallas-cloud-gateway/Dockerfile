FROM java:8
MAINTAINER Jax "jax94@qq.com"
RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak
RUN echo "deb http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "deb-src http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse" >> /etc/apt/sources.list
RUN echo "199.232.68.133 raw.githubusercontent.com" >> /etc/hosts

ENV REFRESHED_AT 2020-11-19

RUN apt-get -yqq update
RUN apt-get -yqq install zip unzip

EXPOSE 8070
ARG PROJECT_NAME
ENV HOME_PATH /home
WORKDIR $HOME_PATH
ADD target/$PROJECT_NAME.zip $HOME_PATH/$PROJECT_NAME.zip
RUN unzip $PROJECT_NAME.zip

WORKDIR $HOME_PATH/$PROJECT_NAME
VOLUME ["$HOME_PATH/$PROJECT_NAME"]
ENTRYPOINT ["./bin/start.sh"]