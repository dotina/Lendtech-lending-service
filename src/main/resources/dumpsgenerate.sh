#! /usr/bin/bash

#System set up
DIRS="/home /etc"
NOW=$(date +"%d-%m-%Y")
BACKUP=/tmp/backup.$$

#Database set up
MUSER="root"
MPASS="root"
MHOST="localhost"
H2="$(which h2)"
MYSQLDUMP="$(which mysqldump)"
GZIP="$(which gzip)"

#Ftp server set up
FTPD="/home/annalis/incremental"
FTPU="annalis"
FTPP="password"
FTPS="255.255.255.0"
NCFTP="$(which ncftpput)"

#Start mysql backup
#get all db names
DBS="$($MYSQL -u $MUSER -h $MHOST -p$MPASS -Bse 'show databases')"
for db in $DBS
do
 FILE=$BACKUP/mysql-$db.$NOW-$(date +"%T").gz
 $MYSQLDUMP -u $MUSER -h $MHOST -p$MPASS $db | $GZIP -9 > $FILE
done

#back up using ftp(ncftp)
ncftp -u"$FTPU" -p"$FTPP" $FTPS << EOF
mkdir $FTPD
mkdir $FTPD/$NOW
cd $FTPD/$NOW
lcd $BACKUP
mput *
quit
EOF

