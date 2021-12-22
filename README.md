# 4cdl
Download images from 4chan

## Usage
java -jar 4cdl.jar directory board[,board2[,board3]] query [--name-only]

### Search board /b for threads which contain "random" in their name and download to local directory /home/john/pics
java -jar 4cdl.jar /home/john/pics /b random

### Search board /wg for threads without filter
java -jar 4cdl.jar /home/john/pics /wg ''

### Search multiple boards
java -jar 4cdl.jar /home/john/pics /b,/s,/wg random

### Print all thread names matching the given filter (no download)
java -jar 4cdl.jar /dummy /wg 'tropical birds' --name-only

### Print all comments of all threads matching the given filter (no download)
java -jar 4cdl.jar /dummy /pol 'not nice' --text-only
