# Создание и тестирование высоконагруженного отказоустойчивого кластера PostgreSQL на базе Patroni

Вот как будет выглядеть ваша инструкция в стиле отчета:

## 1. Создание виртуальных машин в OrbStack

Для реализации отказоустойчивого кластера были созданы три виртуальные машины, на которых планировалась установка
компонентов кластера.

## 2. Установка и настройка etcd

### 2.1. Установка etcd

На каждой виртуальной машине была произведена установка etcd:

```bash
sudo apt update
sudo apt -y install etcd-server
sudo apt -y install etcd-client
sudo systemctl stop etcd
sudo systemctl disable etcd
sudo rm -rf /var/lib/etcd/default 
```

![img.png](img.png)

### 2.2. Конфигурирование etcd

Для корректной работы кластера etcd были выполнены следующие настройки на каждой ноде:

#### Первая виртуальная машина

Файл `/etc/default/etcd` был настроен следующим образом:

```
ETCD_NAME="etcd1"
ETCD_DATA_DIR="/var/lib/etcd"
ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"
ETCD_INITIAL_ADVERTISE_PEER_URLS="http://192.168.139.129:2380"
ETCD_ADVERTISE_CLIENT_URLS="http://192.168.139.129:2379"
ETCD_INITIAL_CLUSTER="etcd1=http://192.168.139.129:2380,etcd2=http://192.168.139.142:2380,etcd3=http://192.168.139.90:2380"
ETCD_INITIAL_CLUSTER_STATE="new"
ETCD_INITIAL_CLUSTER_TOKEN="etcd-cluster-patroni"
ETCD_ELECTION_TIMEOUT="10000"
ETCD_HEARTBEAT_INTERVAL="2000"
ETCD_INITIAL_ELECTION_TICK_ADVANCE="false"
ETCD_ENABLE_V2="true"
```

![img_1.png](img_1.png)

При попытке запуска etcd служба зависла в состоянии ожидания кворума.

![img_2.png](img_2.png)

#### Вторая виртуальная машина

Конфигурация для второй ноды:

```
ETCD_NAME="etcd2"
ETCD_DATA_DIR="/var/lib/etcd"
ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"
ETCD_INITIAL_ADVERTISE_PEER_URLS="http://192.168.139.142:2380"
ETCD_ADVERTISE_CLIENT_URLS="http://192.168.139.142:2379"
...
```

![img_3.png](img_3.png)

После запуска наблюдалось ожидание кворума.

![img_4.png](img_4.png)

#### Третья виртуальная машина

Конфигурация для третьей ноды:

```
ETCD_NAME="etcd3"
ETCD_DATA_DIR="/var/lib/etcd"
ETCD_LISTEN_PEER_URLS="http://0.0.0.0:2380"
ETCD_LISTEN_CLIENT_URLS="http://0.0.0.0:2379"
ETCD_INITIAL_ADVERTISE_PEER_URLS="http://192.168.139.90:2380"
ETCD_ADVERTISE_CLIENT_URLS="http://192.168.139.90:2379"
...
```

После настройки всех узлов кластер etcd успешно запустился и ожидает запросов.

![img_5.png](img_5.png)

### 2.3. Проверка состояния кластера etcd

Для мониторинга состояния кластера были выполнены команды:

```bash
systemctl status etcd
systemctl stop etcd
systemctl is-enabled etcd
systemctl restart etcd
etcdctl endpoint status --cluster -w table
```

![img_6.png](img_6.png)

### 2.4. Проверка отказоустойчивости

После отключения etcd на одной из нод кластер продолжил работу

![img_7.png](img_7.png)

## 3. Установка PostgreSQL

Установка PostgreSQL на все три ноды для дальнейшей настройки кластера Patroni.

## 4. Установка Patroni

