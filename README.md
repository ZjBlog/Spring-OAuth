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
