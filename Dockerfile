FROM java:8-jre
MAINTAINER luyuanx2

WORKDIR /root/
# 根据实际情况替换Agent的下载地址，将******替换成您ARMS的域名。
RUN wget "https://dtsre-prod.oss-cn-hangzhou.aliyuncs.com/Agent/ArmsAgent.zip" -O ArmsAgent.zip
RUN unzip ArmsAgent.zip -d /root/
# 修改arms-agent.config，替换profiler.collector.ip配置定义。
RUN sed -i "s#profiler.collector.ip=127.0.0.1#profiler.collector.ip=172.16.8.60#g" /root/ArmsAgent/arms-agent.config
ENV arms_licenseKey=10000@d1f202e070bddb8
ENV arms_appName=${project.build.finalName}
ENV JAVA_TOOL_OPTIONS ${JAVA_TOOL_OPTIONS} '-javaagent:/root/ArmsAgent/arms-bootstrap-1.7.0-SNAPSHOT.jar -Darms.licenseKey='${arms_licenseKey}' -Darms.appName='${arms_appName}
### for check the args
RUN env | grep JAVA_TOOL_OPTIONS
