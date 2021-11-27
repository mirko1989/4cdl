# 4cdl
Download images from 4chan

## Usage

### Search board /b for threads which contain "random" in their name and download to local directory /home/john/pics
java -jar 4cdl.jar /home/john/pics /b random

### Search board /wg for threads without filter
java -jar 4cdl.jar /home/john/pics /wg ''

### Search multiple boards
java -jar 4cdl.jar /home/john/pics /b,/s,/wg random
