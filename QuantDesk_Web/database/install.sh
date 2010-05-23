ROOT=/home/winweb

cd $ROOT

mysql <<EOF
create database if not exists db_winance2;
create user u_winance2;
grant all on db_winance2.* to u_winance2;
set password for u_winance2 = password('KyAdjxvh59xk');
EOF

(cd public_html && chmod 777 templates_c cache temp)

crontab -l > crontab.txt
if [ `grep -c process.php crontab.txt` = 0 ]
then
    echo "5 18 * * * cd $ROOT/public_html/lib/crons && php process.php" >> crontab.txt
    crontab crontab.txt
fi    
 
cat >>$ROOT/public_html/lib/config.php <<EOF

<? \$_CONFIG['MySQLUser'] = 'u_winance2'; ?>
<? \$_CONFIG['MySQLPassword'] = 'KyAdjxvh59xk'; ?>
<? \$_CONFIG['MySQLDatabase'] = 'db_winance2'; ?>

EOF
