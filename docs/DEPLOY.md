# 部署文档（Docker Compose）

本文档用于本地或服务器一键部署智慧教育平台。

## 1. 前置要求

- 已安装 Docker
- 已安装 Docker Compose（Docker Desktop 自带）

## 2. 一键启动

在项目根目录执行：

```bash
docker compose up -d --build
```

启动后访问：

- 应用地址：`http://localhost:8080`
- MySQL 端口：`localhost:3306`

默认数据库账号：

- 用户名：`root`
- 密码：`root`
- 数据库：`smart_edu`

## 3. 初始化说明

- `app` 服务以 `prod` profile 启动
- 通过 `SPRING_SQL_INIT_MODE=always` 自动执行 `schema.sql` 和 `data.sql`
- 首次启动会创建表结构与测试数据

测试账号（明文）：

- `teacher / 123456`
- `student / 123456`
- `admin / 123456`

## 4. 常用命令

查看日志：

```bash
docker compose logs -f app
```

停止服务：

```bash
docker compose down
```

停止并删除数据卷（谨慎，会清空数据库和上传文件）：

```bash
docker compose down -v
```

## 5. 服务器部署建议

1. 安装 Docker / Compose
2. 拉取项目代码
3. 执行 `docker compose up -d --build`
4. 用 Nginx 反向代理到 `127.0.0.1:8080`
5. 配置 HTTPS（Let's Encrypt）

## 6. 可配置项

`docker-compose.yml` 中可按需修改：

- `DB_PASSWORD`：数据库密码
- `APP_UPLOAD_DIR`：应用内上传目录（容器内路径）
- 端口映射：`8080:8080`、`3306:3306`
