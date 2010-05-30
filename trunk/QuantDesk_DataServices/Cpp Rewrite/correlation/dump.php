<?
    mysql_connect("localhost", "winance", "");
    mysql_select_db("stockdata");

    $max_id = mysql_result(mysql_query("select MAX(id) from symbols"), 0, 0);

    $count_stocks = mysql_result(mysql_query("select count(*) from daily"), 0, 0);
    $count_quots = mysql_result(mysql_query("select count(distinct date) from history"), 0, 0);

    $date_res = mysql_query("select distinct(to_days(date)) from history order by date");

    while($date_row = mysql_fetch_array($date_res))
	$dates[] = $date_row[0];

    $fd=fopen('data.db', 'w');

    fwrite($fd, "$count_stocks $count_quots\n");

    echo "starting dump. count=$count_stocks $count_quots\n";

    for($i=1;$i<=$max_id;$i++)
    {
	$sql = "select to_days(date) as date_id, `change` from history,symbols where history.symbol = symbols.symbol AND symbols.id = $i AND symbols.deleted=0 order by date";
	$res = mysql_query($sql);

	if(mysql_num_rows($res) != 0)
	{
		fwrite($fd, "$i ");

		while($row = mysql_fetch_array($res))
		    $data[$row['date_id']] = $row['change'];

         	foreach($dates as $date_id) {
			if(isset($data[$date_id]))
			    fwrite($fd, "$data[$date_id] ");
			else
			    fwrite($fd, "666 ");
		}

		unset($data);
		fwrite($fd, "\n");
	}
	if($i%1000 == 0)
	    echo "$i dumped\n";
    }
?>
