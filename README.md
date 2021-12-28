# 4cdl
Download images from 4chan

## Usage
java -jar 4cdl.jar --boards=board[,board2[,...]] [--search=query] [--save-dir=directory] [--save-db=user:pass@db_host:port] [--name-only] [--text-only]

### Search board /b for threads which contain "random" in their name and download to local directory /home/john/pics
java -jar 4cdl.jar --save-dir=/home/john/pics --boards=/b --search=random

### Search board /wg for threads without filter
java -jar 4cdl.jar --save-dir=/home/john/pics --boards=/wg

### Search multiple boards
java -jar 4cdl.jar --save-dir=/home/john/pics --boards=/b,/s,/wg --search=random

### Print all thread names matching the given filter (no download)
java -jar 4cdl.jar --boards=/wg --search='tropical birds' --name-only

### Print all comments of all threads matching the given filter (no download)
java -jar 4cdl.jar --boards=/pol --search=president --text-only

### Save to database (schema '4cdl' with table 'Images' will be created)
java -jar 4cdl.jar --boards=/pol --search=president --save-db=root:mariadb@localhost:3306 