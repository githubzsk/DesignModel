# Docker 常用命令

##### CentOS7安装Docker

```shell
# 1.卸载旧版本
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
# 2.需要的安装包
sudo yum install -y yum-utils
# 3.设置镜像仓库 
sudo yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# 4.安装docker
sudo yum install docker-ce docker-ce-cli containerd.io

########################1-4 == yum install -y docker ##########################


# 5.启动（停止/查看状态）docker    
systemctl start/stop/status docker
# 6.查看版本 
docker version


# 7.配置阿里云私有镜像
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://ow3lach2.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

##### 一、基础命令

###### 1.1查看版本 

```
docker -v
```

###### 1.2查看信息 

```
docker info
```

###### 1.3启动 

```
systemctl start docker
```

###### 1.4停止 

```
systemctl stop docker
```

###### 1.5查看状态 

```
systemctl status docker
```

##### 二、镜像相关命令

###### 2.1 查看所有镜像

```
docker images
```

###### 2.2 搜索镜像

```
docker search imageName 直接搜索镜像名

docker serach mysql
```

###### 2.3 拉取镜像

```
docker pull imageName 默认拉取最后一个版本

docker pull imageName:version  拉取制定版本镜像
```

###### 2.4 删除镜像

```
docker rmi imageId 根据镜像id删除镜像

docker rmi `docker images -q` 删除所有镜像
```

##### 三、容器相关命令

###### 3.1.1 查看容器

查看正在运行的容器

```
docker ps
```

查看所有容器

```
docker ps -a
```

查看最后一次运行的容器

```
docker ps -1
```

查看停止运行的容器

```
docker ps  -f status=exited  
```

###### 3.1.2 创建与启动容器

创建容器常用的参数说明

创建容器命令

```
docker run
```

`-i`:表示运行容器

`-t`:表示容器启动后会进入其命令行，加入这两个参数后，容器创建就能登录进去，既分配一个伪终端

`--name`:为创建的容器命名

`-v`:表示目录映射关系（前者是宿主机目录，后者是映射到宿主机上的目录）

`-d`:表示创建一个守护式容器后台运行，不会自动登录容器，-i -t创建后会进入容器

`-p`:表示映射端口，前者是宿主机端口，后者是容器内的映射端口。可以使用-p做多个端口映射

 1. 交互式创建容器

    ```
    docker run -it --name=容器名称 镜像名称:标签 /bin/bash
    docker run -it --name=myCentos centos:7 /bin/bash
    ```

    这时我们通过ps命令查看，发现可以看到启动的容器，状态为启动状态

    退出当前容器

    `exit`  退出后容器停止运行
    
    进入正在运行的容器
    
    ```
    docker attach 容器id
    ```
    
 2. 守护方式创建容器

    使用 -d  则在后台运行 ，守护式在后台运行，所以不需要/bin/bash

    ```
    docker run -id --name=容器名称 镜像名称:标签
    docker run -id --name=myCentos2 centos:7   注意容器不能重名
    ```

    进入守护式的容器

    ```
    docker exec -it  id/name /bin/bash
    docker exec -it  myCentos2 /bin/bash
    docker exec -it  7f022ad69698 /bin/bash
    ```

    守护方式创建的容器，使用exit退出之后，容器依旧运行，不会停止

 3. 停止后台运行的容器

    ```
    docker stop  容器id/容器name
    docker stop myCentos2       docker stop 7f022ad69698
    ```

 4. 启动停止掉的容器

    ```
    docker start  容器id/容器name
    docker start myCentos2       docker start 7f022ad69698
    ```

##### 四、宿主机和容器拷贝文件

######  1.宿主机到容器

```
docker cp fileName   容器id/name:/path/path
docker cp file.txt  myCentos2:/usr/tmp
```

###### 2.容器到宿主机

```
docker cp 容器id/name:/path/path/fileName     宿主机/path/fileNewName
docker cp myCentos2:/usr/tmp/file.txt         mytmp/fileCopy.txt
```



##### 五、部署相关

###### 	1.RabbitMQ

```
	//拉取镜像
	docker pull rabbitmq:management
	
	//守护式启动并映射端口
	docker run -id --name=rabbitmq -p 5671:5671 -p 5672:5672 -p 4369:4369 -p 25672:25672 	 -p 15671:15671 	-p 15672:15672 rabbitmq
	
	② docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:management
	
	//进入rabbitmq容器
	docker exec -it rabbitmq /bin/bash 
	
	// 启动UI插件
	rabbitmq-plugins enable rabbitmq_management
	
```

######  2.MySQL