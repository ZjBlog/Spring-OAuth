# oauth2
多项目打包在  先在父级打包install
mvn clean -U package -pl gateway --am
-U 检查jar更新
-pl 指定打包的子工程名
--am  把本地的依赖也进行打包

      routes:
      - id: nameRoot
        #自定义路径 需要hystrix
        uri: lb://search
        predicates:
        - Path=/test/**
        #熔断
        filters:
        - name: Hystrix
          args:
            name: fallbackcmd
            fallbackUri: forward:/fallback
      - id: hystrix_route
        uri: lb://search
        predicates:
        - Path=/hehe/**
        filters:
        - StripPrefix=1
        #重试
        - name: Retry
          args:
            retries: 3
            statuses: BAD_GATEWAY,INTERNAL_SERVER_ERROR
      - id: search
        uri: lb://search-service
        predicates:
        - Path=/search/**
        filters:
        - StripPrefix=1
        - name: Retry
          args:
            retries: 3
            series:
              - SERVER_ERROR
              - CLIENT_ERROR
            methods:
              - GET
              - POST
            status:
              - GATEWAY_TIMEOUT
            exceptions:
            - java.io.IOException
            - java.util.concurrent.TimeoutException
        - name: Hystrix
          args:
            name: fallbackcmd
            fallbackUri: forward:/fallback
https://www.fangzhipeng.com/springcloud/2019/02/05/sc-sleuth-g.html
sleuth server端使用docker

docker run -d -p 9411:9411 openzipkin/zipkin
docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management

#zipkin连接mq
docker run -d -p 9411:9411 -e RABBIT_ADDRESSES=192.168.1.253:5672 -e RABBIT_PASSWORD=guest -e RABBIT_USER=guest openzipkin/zipkin



将链路数据存在在Elasticsearch中
docker run -d -p 9411:9411 -e STORAGE_TYPE=elasticsearch -e RABBIT_ADDRESSES=192.168.1.253:5672 -e RABBIT_PASSWORD=guest -e RABBIT_USER=guest -e ES_HOSTS=192.168.1.250:9200 openzipkin/zipkin
