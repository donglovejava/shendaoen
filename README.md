# 智慧教育平台 (Smart Edu Platform)

基于 Spring Boot 3.5.13 + MyBatis + Spring Security + Thymeleaf 的智慧教育后端平台。

## 功能规划

- 用户（学生/教师/管理员）注册、登录、鉴权
- 课程 / 章节管理，视频 / PPT 上传与浏览
- 签到码签到，实时统计每日签到人数
- 得分系统：签到得分、学习得分、教师手动加分
- 教师仪表盘：实时监控、得分排行榜

## 技术栈

| 层 | 选型 |
|---|---|
| 框架 | Spring Boot 3.5.13 |
| 语言 | Java 17 |
| ORM | MyBatis 3.0.5 |
| 安全 | Spring Security (Session + Cookie) |
| 视图 | Thymeleaf |
| 数据库 | MySQL 8 |
| 构建 | Maven |

## 快速开始

### 1. 环境要求

- JDK 17+
- MySQL 8+
- Maven 3.8+（项目自带 `mvnw` 可直接用）

### 2. 数据库配置

无需手动建库，首次启动会根据 `application-dev.properties` 中的 `createDatabaseIfNotExist=true` 自动创建 `smart_edu` 数据库，并由 `schema.sql` / `data.sql` 初始化表和测试数据。

默认 MySQL 账号 `root` / `root`，如不同请修改 `src/main/resources/application-dev.properties`。

### 3. 本地启动

```bash
./mvnw spring-boot:run
```

访问 http://localhost:8080/ 即可进入系统。

### 4. Docker Compose 一键启动（推荐）

```bash
docker compose up -d --build
```

启动后访问：

- http://localhost:8081

更多部署说明见：[`docs/DEPLOY.md`](docs/DEPLOY.md)

### 5. 测试账号

| 用户名 | 密码 | 角色 |
|---|---|---|
| teacher | 123456 | 教师 |
| student | 123456 | 学生 |
| admin   | 123456 | 管理员 |

## 常见问题（FAQ）

### 1) Docker 拉取镜像超时（`registry-1.docker.io` 连接失败）

在 Docker Desktop -> `Settings` -> `Docker Engine` 中增加：

```json
"registry-mirrors": [
  "https://docker.1ms.run",
  "https://hub.rat.dev"
]
```

点 `Apply & Restart` 后重试：

```bash
docker compose pull
docker compose up -d --build
```

### 2) MySQL 3306 端口冲突

如果本机已安装 MySQL，`3306` 常被占用。当前项目已默认不暴露 MySQL 到宿主机，因此无需 `3306:3306` 映射。

### 3) 应用 8080 端口冲突

如果本机已有 Java 服务占用 8080，本项目已将宿主机映射改为：

```yaml
ports:
  - "8081:8080"
```

请访问 `http://localhost:8081`。

## GitHub 发布前检查清单

- [ ] `docker compose up -d --build` 可成功启动
- [ ] `docker compose ps` 显示 `smartedu-mysql` 与 `smartedu-app` 均为 `Up`
- [ ] `http://localhost:8081` 可访问并正常登录
- [ ] 教师发起签到、学生签到、仪表盘人数更新链路可用
- [ ] `docs/对话纪要.md` 与 `docs/DEPLOY.md` 已同步最新内容

## 项目结构

```
src/main/java/edu/neusoft/springbookdemo/
├── SpringbookDemoApplication.java
├── common/         通用返回体
├── config/         配置类（Security 等）
├── controller/     Web 层
├── service/        业务层
├── mapper/         MyBatis 接口
├── entity/         数据库实体
└── dto/            请求/响应对象

src/main/resources/
├── application.properties          主配置（profile=dev）
├── application-dev.properties      开发配置
├── application-prod.properties     生产配置（走环境变量）
├── db/
│   ├── schema.sql                  建表脚本
│   └── data.sql                    测试数据
├── mapper/                         MyBatis XML
├── templates/                      Thymeleaf 模板
└── static/                         静态资源
```

## 开发进度

- [x] 第 1 步：项目骨架 + 数据库表结构 + 测试数据
- [x] 第 2 步：用户注册登录 + Security Session 鉴权
- [x] 第 3 步：课程 / 章节 / 文件上传
- [x] 第 4 步：签到功能
- [x] 第 5 步：得分系统 + 实时仪表盘
- [x] 第 6 步：前端页面（Thymeleaf）
- [x] 第 7 步：Docker 化 + 部署文档

## 对话纪要

开发全过程记录于 [`docs/对话纪要.md`](docs/对话纪要.md)。

## License

MIT
