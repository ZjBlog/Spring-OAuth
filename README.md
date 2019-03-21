# oauth2
多项目打包在  父级打包
mvn clean -U package -pl gateway --am
-U 检查jar更新
-pl 指定打包的子工程名
--am  把本地的依赖也进行打包